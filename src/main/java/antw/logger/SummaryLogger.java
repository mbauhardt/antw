package antw.logger;

import java.util.Collection;

import org.apache.tools.ant.BuildEvent;

import antw.AntwContext;
import antw.model.target.Projects;
import antw.model.target.Target;
import antw.util.TimeUtil;

public class SummaryLogger extends LoggerAdapter {

    private static final String HEADER_FORMAT = "%-25s %-40s %-15s %-15s%n";
    private static final String TARGET_FORMAT = "%-25s %-40s %-15s %-15s%n";

    private final AntwContext _context;

    public SummaryLogger(AntwContext context) {
        _context = context;
    }

    @Override
    public void buildFinished(BuildEvent event) {
        logHeader();
        Projects projects = _context.getProjects();
        Collection<Target> targets = projects.computeRelativeBuildTime(projects.getEnd().getTime()
                - projects.getStart().getTime());
        for (Target target : targets) {
            if (target.getDurationInPercent() <= 3) {
                continue;
            }
            out(TARGET_FORMAT,
                    new Object[] { target.getProject().getName(), target.getName(),
                            TimeUtil.formatTimeDuration(target.getDuration()), target.getDurationInPercent() + "%" });
        }
        newLine();
        out(TARGET_FORMAT,
                new Object[] { "Total", "",
                        TimeUtil.formatTimeDuration(projects.getEnd().getTime() - projects.getStart().getTime()),
                        "100%" });
    }

    private void logHeader() {
        out(HEADER_FORMAT, new Object[] { "PROJECT", "TARGET", "DURATION (s)", "DURATION (%)" });
        String line = "";
        for (int i = 0; i < 96; i++) {
            line += "-";
        }
        out(line);
    }

}
