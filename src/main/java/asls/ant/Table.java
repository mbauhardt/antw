package asls.ant;

import asls.model.Target;

public abstract class Table {

    public abstract void header(Printer printer);

    public abstract void record(Printer printer, Target target);

}
