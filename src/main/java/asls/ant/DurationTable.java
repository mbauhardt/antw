package asls.ant;

import asls.model.Target;
import asls.util.TimeUtil;

public class DurationTable extends Table {

    private static final String HEADER_FORMAT = "%-25s %-40s %-15s %-15s %-15s %-15s%n";
    private static final String TARGET_FORMAT = "%-25s %-40s %-15d %-15tT %-15tT %-15s%n";

    @Override
    public void header(Printer printer) {
        printer.out(HEADER_FORMAT, new Object[] { "PROJECT", "TARGET", "CALL COUNT", "START", "FINISH", "DURATION" });
        String line = "";
        for (int i = 0; i < 125; i++) {
            line += "-";
        }
        printer.out(line);
    }

    @Override
    public void record(Printer printer, Target target) {
        printer.out(
                TARGET_FORMAT,
                new Object[] { target.getProject().getName(), target.getName(), target.getCounter(), target.getStart(),
                        target.getFinish(),
                        TimeUtil.formatTimeDuration(target.getFinish().getTime() - target.getStart().getTime()) });

    }

}
