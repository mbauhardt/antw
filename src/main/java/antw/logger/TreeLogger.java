package antw.logger;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

import antw.AntwContext;
import antw.model.target.Project;
import antw.model.target.Target;
import antw.util.TimeUtil;

public class TreeLogger extends LoggerAdapter {

    private Project _lastProject = new Project("");
    private int _spaceCount = 2;
    private Date _start;
    private final AntwContext _context;

    public TreeLogger(AntwContext context) {
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
            err(event.getException());
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

}
