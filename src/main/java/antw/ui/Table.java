package antw.ui;

import antw.ant.Printer;
import antw.model.Projects;
import antw.model.Target;

public abstract class Table {

    public abstract void logBuildStarted(Printer printer, Projects projects);

    public abstract void logBuildFinished(Printer printer, Projects projects);

    public abstract void logTargetStartet(Printer printer, Target target);

    public abstract void logTargetFinished(Printer printer, Target target);
}
