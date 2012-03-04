package antw.logger;

import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;

import antw.AntwContext;

public class StatisticLogger extends LoggerAdapter {

    private AntwContext _context = new AntwContext();

    @Override
    public void buildStarted(BuildEvent event) {

        BuildListener collector = (BuildListener) new ProjectCollector(_context);
        collector.buildStarted(event);

        BuildListener treeView = (BuildListener) new TreeLogger(_context).setOutputPrint(getOutputPrint())
                .setErrorPrint(getErrorPrint());
        treeView.buildStarted(event);

        PrintStream durationStream = _context.openReportingStream("target-duration.txt");
        BuildListener durationView = (BuildListener) new DurationLogger(_context).setOutputPrint(durationStream)
                .setErrorPrint(durationStream);
        durationView.buildStarted(event);

        PrintStream plainDurationStream = _context.openReportingStream("target-duration.tsv");
        BuildListener plainDurationView = (BuildListener) new PlainDurationLogger(_context).setOutputPrint(
                plainDurationStream).setErrorPrint(plainDurationStream);
        plainDurationView.buildStarted(event);

        PrintStream summaryStream = _context.openReportingStream("target-summary.txt");
        BuildListener summaryView = (BuildListener) new SummaryLogger(_context).setOutputPrint(summaryStream)
                .setErrorPrint(summaryStream);
        summaryView.buildStarted(event);

        event.getProject().addBuildListener(collector);
        event.getProject().addBuildListener(treeView);
        event.getProject().addBuildListener(durationView);
        event.getProject().addBuildListener(plainDurationView);
        event.getProject().addBuildListener(summaryView);

    }

    public void buildFinished(BuildEvent event) {
        _context.finishReports(event);

        // List<File> junitFiles =
        // FileUtil.findFiles(event.getProject().getBaseDir(), "junit.txt");
        // FileUtil.merge(junitFiles, new File("build/antw/junit.txt"));
        // junitFiles = FileUtil.findFiles(event.getProject().getBaseDir(),
        // "junit-plain.tsv");
        // FileUtil.merge(junitFiles, new File("build/antw/junit-plain.tsv"));

    }

}
