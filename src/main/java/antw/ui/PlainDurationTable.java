package antw.ui;

import antw.ant.Printer;
import antw.model.Projects;
import antw.model.Target;

public class PlainDurationTable extends Table {

    private static final String TAB = "\t";

    @Override
    public void logTargetStartet(Printer printer, Target target) {
    }

    @Override
    public void logTargetFinished(Printer printer, Target target) {
        printer.out(target.getProject().getName() + TAB + target.getName() + TAB + target.getCounter() + TAB
                + target.getStart() + TAB + target.getFinish() + TAB + target.getDuration());
    }

    @Override
    public void logBuildFinished(Printer printer, Projects projects) {
    }

    @Override
    public void logBuildStarted(Printer printer, Projects projects) {
        printer.out("PROJECT" + TAB + "TARGET" + TAB + "CALL COUNT" + TAB + "START" + TAB + "FINISH" + TAB
                + "DURATION (ms)");
    }

}
