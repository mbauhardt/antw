package antw.ui.target;

import antw.logger.Printer;
import antw.model.target.Projects;
import antw.model.target.Target;

public abstract class Table {

    public abstract void logBuildStarted(Printer printer, Projects projects);

    public abstract void logBuildFinished(Printer printer, Projects projects);

    public abstract void logTargetStartet(Printer printer, Target target);

    public abstract void logTargetFinished(Printer printer, Target target);
}
