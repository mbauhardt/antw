package antw.ui;

import antw.ant.Printer;
import antw.model.Project;
import antw.model.Projects;
import antw.model.Target;

public class TreeTable extends Table {

    private Project _lastProject;

    @Override
    public void logTargetStartet(Printer printer, Target target) {
        if (!target.getProject().equals(_lastProject)) {
            printer.newLine();
            printer.out(target.getProject().getName());
        }
        _lastProject = target.getProject();
        printer.space(2);
        printer.out("|--- " + target.getName());
    }

    @Override
    public void logTargetFinished(Printer printer, Target target) {

    }

    @Override
    public void logBuildFinished(Printer printer, Projects projects) {

    }

}
