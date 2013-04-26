package antw.logger;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;

public class AntwLogger extends LoggerAdapter {

    private LoggerContext _context = new LoggerContext();
    private int _level;

    public AntwLogger() {
	super("antw");
    }

    @Override
    public void buildStarted(BuildEvent event) {
	List<BuildListener> listeners = getAvailableListeners(event);
	replaceFileSreamWithStdoutStream(listeners);
	addBuildListers(event, listeners);
    }

    private void replaceFileSreamWithStdoutStream(List<BuildListener> listeners) {
	String loggerId = System.getProperty("antw.stdout.view", "tree");
	for (BuildListener buildListener : listeners) {
	    if (buildListener instanceof LoggerAdapter) {
		LoggerAdapter adapter = (LoggerAdapter) buildListener;
		if (adapter.getId().equals(loggerId)) {
		    adapter.getErrorPrint().close();
		    adapter.getOutputPrint().close();
		    adapter.setOutputPrint(getOutputPrint()).setErrorPrint(
			    getOutputPrint());
		}
	    }
	}

    }

    private List<BuildListener> getAvailableListeners(BuildEvent event) {
	BuildListener collector = createProjectCollector(event);
	BuildListener treeView = createTreeView(event);
	BuildListener durationView = createTargetDurationTextView(event);
	BuildListener plainDurationView = createTargetDurationTsvView(event);
	BuildListener summaryView = createTargetSummaryView(event);
	BuildListener junitView = createJunitView(event);
	BuildListener messageView = createMessageView(event);
	BuildListener antDefaultView = createAntDefaultView(event);
	BuildListener profilerView = createJunitProfilerView();
	BuildListener powerLineView = createPowerLineView(event);
	BuildListener treeSummaryView = createTreeSummaryView(event);
	return Arrays.asList(collector, treeView, durationView,
		plainDurationView, summaryView, junitView, messageView,
		antDefaultView, profilerView, powerLineView, treeSummaryView);

    }

    

    private void addBuildListers(BuildEvent event, List<BuildListener> listeners) {
	for (BuildListener buildListener : listeners) {
	    event.getProject().addBuildListener(buildListener);
	}
    }

    private BuildListener createProjectCollector(BuildEvent event) {
	BuildListener collector = (BuildListener) new ProjectCollector(_context);
	collector.buildStarted(event);
	return collector;
    }

    private BuildListener createJunitProfilerView() {
	PrintStream profilerStream = _context
		.openReportingStream("junit-profiler.txt");
	BuildLogger profilerView = (BuildLogger) new JunitProfilerLogger();
	profilerView.setMessageOutputLevel(_level);
	profilerView.setOutputPrintStream(profilerStream);
	return profilerView;
    }

    private BuildListener createAntDefaultView(BuildEvent event) {
	PrintStream antDefaultStream = _context
		.openReportingStream("ant-default.txt");
	BuildLogger antDefaultView = (BuildLogger) new DefaultLogger();
	antDefaultView.setMessageOutputLevel(_level);
	antDefaultView.setOutputPrintStream(antDefaultStream);
	antDefaultView.setErrorPrintStream(antDefaultStream);
	antDefaultView.buildStarted(event);
	return antDefaultView;
    }

    private BuildListener createMessageView(BuildEvent event) {
	PrintStream messageStream = _context
		.openReportingStream("messages.txt");
	BuildLogger messageView = (BuildLogger) new MessageLogger()
		.setOutputPrint(messageStream).setErrorPrint(messageStream);
	messageView.setMessageOutputLevel(_level);
	messageView.buildStarted(event);
	return messageView;
    }

    private BuildListener createJunitView(BuildEvent event) {
	PrintStream junitStream = _context
		.openReportingStream("junit-plain.csv");
	BuildListener junitView = (BuildListener) new JunitLogger()
		.setOutputPrint(junitStream).setErrorPrint(junitStream);
	junitView.buildStarted(event);
	return junitView;
    }

    private BuildListener createTargetSummaryView(BuildEvent event) {
	PrintStream summaryStream = _context
		.openReportingStream("target-summary.txt");
	BuildListener summaryView = (BuildListener) new SummaryLogger(_context)
		.setOutputPrint(summaryStream).setErrorPrint(summaryStream);
	summaryView.buildStarted(event);
	return summaryView;
    }

    private BuildListener createTargetDurationTsvView(BuildEvent event) {
	PrintStream plainDurationStream = _context
		.openReportingStream("target-duration.tsv");
	BuildListener plainDurationView = (BuildListener) new PlainDurationLogger(
		_context).setOutputPrint(plainDurationStream).setErrorPrint(
		plainDurationStream);
	plainDurationView.buildStarted(event);
	return plainDurationView;
    }

    private BuildListener createTargetDurationTextView(BuildEvent event) {
	PrintStream durationStream = _context
		.openReportingStream("target-duration.txt");
	BuildListener durationView = (BuildListener) new DurationLogger(
		_context).setOutputPrint(durationStream).setErrorPrint(
		durationStream);
	durationView.buildStarted(event);
	return durationView;
    }

    private BuildListener createTreeView(BuildEvent event) {
	PrintStream treeStream = _context.openReportingStream("tree.txt");
	BuildListener treeView = (BuildListener) new TreeLogger(_context)
		.setOutputPrint(treeStream).setErrorPrint(treeStream);
	treeView.buildStarted(event);
	return treeView;
    }

    private BuildListener createPowerLineView(BuildEvent event) {
	PrintStream powerlineStream = _context.openReportingStream("powerline.txt");
	BuildListener powerlineView = (BuildListener) new PowerLineLogger(_context)
		.setOutputPrint(powerlineStream)
		.setErrorPrint(powerlineStream);
	((BuildLogger) powerlineView).setMessageOutputLevel(_level);
	powerlineView.buildStarted(event);
	return powerlineView;
    }
    
    private BuildListener createTreeSummaryView(BuildEvent event) {
	PrintStream treeSummaryStream = _context.openReportingStream("tree-summary.txt");
	BuildListener treeSummaryView = (BuildListener) new TreeSummaryLogger(_context)
		.setOutputPrint(treeSummaryStream)
		.setErrorPrint(treeSummaryStream);
	((BuildLogger) treeSummaryView).setMessageOutputLevel(_level);
	treeSummaryView.buildStarted(event);
	return treeSummaryView;
    }

    @Override
    public void setMessageOutputLevel(int level) {
	_level = level;
    }

    public void buildFinished(BuildEvent event) {
	_context.finishReports(event);
    }

}
