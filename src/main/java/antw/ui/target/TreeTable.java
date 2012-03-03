package antw.ui.target;

import java.util.Date;

import antw.logger.Printer;
import antw.model.target.Project;
import antw.model.target.Projects;
import antw.model.target.Target;
import antw.util.TimeUtil;

public class TreeTable extends Table {

    private Project _lastProject = new Project("root");
    private int _spaceCount = 2;
    private Date _start;

    @Override
    public void logTargetStartet(Printer printer, Target target) {
        if (!target.getProject().equals(_lastProject)) {
            switchToSubProject(printer, _lastProject, target.getProject());
            switchFromSubProject(printer, _lastProject, target.getProject());
            printProject(printer, target.getProject());
        }
        _lastProject = target.getProject();
        printer.space(_spaceCount + 2);
        printer.out(
                "%-40s %-40s%n",
                new Object[] {
                        "|--- " + target.getName(),
                        "[" + target.getCounter() + " times; "
                                + TimeUtil.formatTimeDuration(System.currentTimeMillis() - _start.getTime()) + "]" });
    }

    private void printProject(Printer printer, Project project) {
        printer.space(_spaceCount + 2);
        printer.out("|");
        printer.space(_spaceCount + 1);
        printer.out(project.getName());
    }

    private void switchFromSubProject(Printer printer, Project lastProject, Project project) {
        if (lastProject.isSubProject()) {
            if (!project.isSubProject()) {
                printer.space(_spaceCount + 1);
                _spaceCount -= 2;
                printer.out("/");
            }
        }
    }

    private void switchToSubProject(Printer printer, Project lastProject, Project currentProject) {
        if (!lastProject.isSubProject()) {
            if (currentProject.isSubProject()) {
                _spaceCount += 2;
                printer.space(_spaceCount + 1);
                printer.out("\\");
            }
        }

    }

    @Override
    public void logTargetFinished(Printer printer, Target target) {

    }

    @Override
    public void logBuildFinished(Printer printer, Projects projects) {

    }

    @Override
    public void logBuildStarted(Printer printer, Projects projects) {
        _start = new Date();
    }

}
