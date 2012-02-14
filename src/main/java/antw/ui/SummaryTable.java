package antw.ui;

import java.util.Collection;

import antw.ant.Printer;
import antw.model.Projects;
import antw.model.Target;
import antw.util.TimeUtil;

public class SummaryTable extends Table {

    private static final String HEADER_FORMAT = "%-25s %-40s %-15s %-15s%n";
    private static final String TARGET_FORMAT = "%-25s %-40s %-15s %-15s%n";

    @Override
    public void logTargetStartet(Printer printer, Target target) {
    }

    @Override
    public void logTargetFinished(Printer printer, Target target) {

    }

    @Override
    public void logBuildFinished(Printer printer, Projects projects) {
        logHeader(printer);
        Collection<Target> targets = projects.computeRelativeBuildTime(projects.getEnd().getTime()
                - projects.getStart().getTime());
        for (Target target : targets) {
            if (target.getDurationInPercent() <= 3) {
                continue;
            }
            printer.out(
                    TARGET_FORMAT,
                    new Object[] { target.getProject().getName(), target.getName(),
                            TimeUtil.formatTimeDuration(target.getDuration()), target.getDurationInPercent() + "%" });
        }
        printer.newLine();
        printer.out(
                TARGET_FORMAT,
                new Object[] { "Total", "",
                        TimeUtil.formatTimeDuration(projects.getEnd().getTime() - projects.getStart().getTime()),
                        "100%" });
    }

    private void logHeader(Printer printer) {
        printer.out(HEADER_FORMAT, new Object[] { "PROJECT", "TARGET", "DURATION (s)", "DURATION (%)" });
        String line = "";
        for (int i = 0; i < 96; i++) {
            line += "-";
        }
        printer.out(line);
    }

}
