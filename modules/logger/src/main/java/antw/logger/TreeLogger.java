package antw.logger;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

import antw.common.util.Constants;
import antw.common.util.StringUtil;
import antw.common.util.TimeUtil;
import antw.logger.model.Project;
import antw.logger.model.Target;

public class TreeLogger extends LoggerAdapter {

    protected Project _lastProject = new Project("");
    protected int _spaceCount = 2;
    protected Date _start;
    protected final LoggerContext _context;
    private boolean _junitTaskWasRunning;

    public TreeLogger(LoggerContext context) {
        _context = context;
    }

    @Override
    public void targetStarted(BuildEvent event) {
        Target target = _context.getTarget(event);
        if (!target.getProject().equals(_lastProject)) {
            switchToSubProject(_lastProject, target.getProject());
            switchFromSubProject(_lastProject, target.getProject());
            printProject(target.getProject());
        }
        _lastProject = target.getProject();
        space(_spaceCount + 2);
        out("%-40s %-40s%n",
                new Object[] {
                        "|--- " + target.getName(),
                        "[" + target.getCounter() + " times; "
                                + TimeUtil.formatTimeDuration(System.currentTimeMillis() - _start.getTime()) + "]" });
    }

    private void printProject(Project project) {
        space(_spaceCount + 2);
        out("|");
        space(_spaceCount + 1);
        out(project.getName());
    }

    private void switchFromSubProject(Project lastProject, Project project) {
        if (lastProject.isSubProject()) {
            if (!project.isSubProject()) {
                space(_spaceCount + 1);
                _spaceCount -= 2;
                out("/");
            }
        }
    }

    private void switchToSubProject(Project lastProject, Project currentProject) {
        if (!lastProject.isSubProject()) {
            if (currentProject.isSubProject()) {
                _spaceCount += 2;
                space(_spaceCount + 1);
                out("\\");
            }
        }

    }

    @Override
    public void buildStarted(BuildEvent event) {
        newLine();
        _start = new Date();
    }

    @Override
    public void buildFinished(BuildEvent event) {
        if (event.getException() != null) {
            newLine(3);
            err(event.getException(), false);
            newLine(3);
            out("BUILD FAILED :(");
            out("Total Time: " + _context.getProjects().getDurationAsString());
        } else {
            newLine(3);
            out("BUILD SUCCESSFUL :)");
            out("Total Time: " + _context.getProjects().getDurationAsString());
        }
        newLine(2);
    }

    @Override
    public void messageLogged(BuildEvent event) {
        if (event.getTask() != null) {
            if ("junit".equals(event.getTask().getTaskType())) {
                if (!_junitTaskWasRunning) {
                    switchToTestSuite();
                }
                if (event.getPriority() <= org.apache.tools.ant.Project.MSG_INFO) {
                    String message = event.getMessage();
                    if (message.contains(Constants.TEST_SUITE_LABEL)) {
                        printTestSuite(message);
                    } else if (message.contains(Constants.TEST_CASE_LABEL)) {
                        printTestCase(message);
                    }
                }
                _junitTaskWasRunning = true;
            } else {
                if (_junitTaskWasRunning) {
                    switchFromTestSuite();
                }
                _junitTaskWasRunning = false;
            }
        }
    }

    private void switchFromTestSuite() {
        space(_spaceCount + 1);
        _spaceCount -= 2;
        out("/");
    }

    private void printTestCase(String message) {
        space(_spaceCount + 2);
        out("|--- " + StringUtil.remove(Constants.TEST_CASE_LABEL, message));
    }

    private void switchToTestSuite() {
        _spaceCount += 2;
        space(_spaceCount + 1);
        out("\\");
    }

    private void printTestSuite(String testStuite) {
        space(_spaceCount + 2);
        out("|");
        space(_spaceCount + 1);
        out(StringUtil.remove(Constants.TEST_SUITE_LABEL, testStuite));
    }

}
