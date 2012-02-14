package antw.util;

public class TimeUtil {

    public static String formatTimeDuration(long timeDuration) {
        StringBuilder builder = new StringBuilder();
        long hours = timeDuration / (60 * 60 * 1000);
        long mod = (timeDuration % (60 * 60 * 1000));
        long minutes = mod / (60 * 1000);
        mod = mod % (60 * 1000);
        long seconds = mod / 1000;

        if (hours != 0) {
            builder.append(hours);
            builder.append(" hrs, ");
        }
        if (minutes != 0) {
            builder.append(minutes);
            builder.append(" mins, ");
        }
        builder.append(seconds);
        builder.append(" sec");
        return builder.toString();
    }

}
