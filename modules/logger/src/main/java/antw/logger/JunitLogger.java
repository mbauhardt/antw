package antw.logger;

import org.apache.tools.ant.BuildEvent;

import antw.common.util.Constants;
import antw.common.util.StringUtil;
import antw.common.util.TimeUtil;

public class JunitLogger extends LoggerAdapter {

    private String _lastTestSuite;

    public JunitLogger() {
	super("junit");
    }

    @Override
    public void messageLogged(BuildEvent event) {
	if (event.getTask() != null) {
	    if ("junit".equals(event.getTask().getTaskType())) {
		if (event.getPriority() <= org.apache.tools.ant.Project.MSG_INFO) {
		    String message = event.getMessage();
		    if (message.contains(Constants.TEST_SUITE_LABEL)) {
			printTestSuite(message);
		    } else if (message.contains(Constants.TEST_CASE_LABEL)) {
			printTestCase(message);
		    }
		}
	    }
	}

    }

    void printTestCase(String message) {
	String testCase = StringUtil.remove(Constants.TEST_CASE_LABEL, message);
	testCase = testCase.replaceAll("\\s{2,}", ";");
	String s = _lastTestSuite;
	try {
	    String[] splits = testCase.split(";");
	    for (int i = 0; i < splits.length; i++) {
		String split = splits[i];
		if (i == 1) {
		    split = "" + TimeUtil.convertTimeDuration(split);
		}
		s = s + "," + split;
	    }
	} catch (Throwable t) {

	}
	out(s);
    }

    private void printTestSuite(String message) {
	_lastTestSuite = StringUtil.remove(Constants.TEST_SUITE_LABEL, message);
    }

}
