package antw.logger;

import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.BuildLogger;

public class AntwLogger extends LoggerAdapter {

    private LoggerContext _context = new LoggerContext();
    private int _level;

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

        PrintStream junitStream = _context.openReportingStream("junit-plain.csv");
        BuildListener junitView = (BuildListener) new JunitLogger().setOutputPrint(junitStream).setErrorPrint(
                junitStream);
        junitView.buildStarted(event);

        PrintStream messageStream = _context.openReportingStream("messages.txt");
        BuildLogger messageView = (BuildLogger) new MessageLogger().setOutputPrint(messageStream).setErrorPrint(
                messageStream);
        messageView.setMessageOutputLevel(_level);
        messageView.buildStarted(event);

        event.getProject().addBuildListener(collector);
        event.getProject().addBuildListener(treeView);
        event.getProject().addBuildListener(durationView);
        event.getProject().addBuildListener(plainDurationView);
        event.getProject().addBuildListener(summaryView);
        event.getProject().addBuildListener(junitView);
        event.getProject().addBuildListener(messageView);
    }

    @Override
    public void setMessageOutputLevel(int level) {
        _level = level;
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
