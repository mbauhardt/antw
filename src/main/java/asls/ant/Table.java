package asls.ant;

import asls.model.Projects;
import asls.model.Target;

public abstract class Table {

    public abstract void logHeader(Printer printer);

    public abstract void logTargetFinished(Printer printer, Target target);

    public abstract void logBuildFinished(Printer printer, Projects projects);

    public abstract void logTargetStartet(Printer printer, Target target);

}
