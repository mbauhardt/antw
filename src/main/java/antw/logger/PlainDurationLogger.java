package antw.logger;

import org.apache.tools.ant.BuildEvent;

import antw.AntwContext;
import antw.model.target.Target;

public class PlainDurationLogger extends LoggerAdapter {

    private static final String TAB = "\t";
    private final AntwContext _context;

    public PlainDurationLogger(AntwContext context) {
        _context = context;
    }

    @Override
    public void targetFinished(BuildEvent event) {
        Target target = _context.getTarget(event);
        out(target.getProject().getName() + TAB + target.getName() + TAB + target.getCounter() + TAB
                + target.getStart() + TAB + target.getFinish() + TAB + target.getDuration());
    }

    @Override
    public void buildStarted(BuildEvent event) {
        out("PROJECT" + TAB + "TARGET" + TAB + "CALL COUNT" + TAB + "START" + TAB + "FINISH" + TAB + "DURATION (ms)");
    }

}
