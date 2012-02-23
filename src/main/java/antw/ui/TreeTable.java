package antw.ui;

import antw.ant.Printer;
import antw.model.Project;
import antw.model.Projects;
import antw.model.Target;

public class TreeTable extends Table {

    private Project _lastProject = new Project("root");
    private int _spaceCount = 2;

    @Override
    public void logTargetStartet(Printer printer, Target target) {
        if (!target.getProject().equals(_lastProject)) {
            switchToSubProject(printer, _lastProject, target.getProject());
            switchFromSubProject(printer, _lastProject, target.getProject());
            printProject(printer, target.getProject());
        }
        _lastProject = target.getProject();
        printer.space(_spaceCount + 2);
        printer.out("|--- " + target.getName());
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

    }

}
