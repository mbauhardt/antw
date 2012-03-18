package antw.logger;

import org.apache.tools.ant.BuildEvent;

import antw.logger.model.Project;
import antw.logger.model.Target;

public class DurationLogger extends LoggerAdapter {

    private static final String HEADER_FORMAT = "%-25s %-40s %-15s %-15s %-15s %-15s%n";
    private static final String TARGET_FORMAT = "%-25s %-40s %-15d %-15tT %-15tT %-15s%n";
    private Project _lastProject;
    private final LoggerContext _context;

    public DurationLogger(LoggerContext context) {
        _context = context;
    }

    @Override
    public void targetStarted(BuildEvent event) {
        Target target = _context.getTarget(event);
        if (!target.getProject().equals(_lastProject)) {
            newLine();
        }
        _lastProject = target.getProject();
        if (!target.getProject().isSubProject()) {
            logHeader();
        }

    }

    @Override
    public void targetFinished(BuildEvent event) {
        Target target = _context.getTarget(event);
        if (!target.getProject().equals(_lastProject)) {
            newLine();
        }
        out(TARGET_FORMAT,
                new Object[] { target.getProject().getName(), target.getName(), target.getCounter(), target.getStart(),
                        target.getFinish(), target.getDurationAsString() });
        if (!target.getProject().isSubProject()) {
            newLine(2);
        }
    }

    private void logHeader() {
        out(HEADER_FORMAT, new Object[] { "PROJECT", "TARGET", "CALL COUNT", "START", "FINISH", "DURATION" });
        String line = "";
        for (int i = 0; i < 125; i++) {
            line += "-";
        }
        out(line);
    }

}
