package antw.logger;

import org.apache.tools.ant.BuildEvent;

import antw.common.util.Constants;
import antw.common.util.StringUtil;

public class JunitLogger extends LoggerAdapter {

    private String _lastTestSuite;

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

    private void printTestCase(String message) {
        String testCase = StringUtil.remove(Constants.TEST_CASE_LABEL, message);
        testCase = testCase.replaceAll("\\s{2,}", ",");
        out(_lastTestSuite + "," + testCase);
    }

    private void printTestSuite(String message) {
        _lastTestSuite = StringUtil.remove(Constants.TEST_SUITE_LABEL, message);
    }

}
