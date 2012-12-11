package antw.logger;

import org.apache.tools.ant.BuildEvent;

import antw.common.util.Constants;
import antw.common.util.StringUtil;

public class JunitProfilerLogger extends LoggerAdapter {

    private int _spacesBefore = Integer.MAX_VALUE;

    public JunitProfilerLogger() {
	super("junitprofiler");
    }

    @Override
    public void messageLogged(BuildEvent event) {
	if (event.getTask() != null) {
	    if ("junit".equals(event.getTask().getTaskType())) {
		if (event.getPriority() <= org.apache.tools.ant.Project.MSG_INFO) {
		    String message = event.getMessage();
		    if (message.contains(Constants.PROFILER_LABEL)) {
			printProfilerMessage(event, message);
		    }
		}
	    }
	}

    }

    private void printProfilerMessage(BuildEvent event, String message) {
	message = StringUtil.remove(Constants.PROFILER_LABEL, message, false);
	int spaces = StringUtil.countFirstWhiteSpaces(message);
	if (spaces < _spacesBefore) {
	    _spacesBefore = spaces;
	}
	message = message.substring(_spacesBefore);
	out(message);
    }
}
