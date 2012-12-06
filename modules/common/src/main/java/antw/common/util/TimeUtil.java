package antw.common.util;

public class TimeUtil {

	public static String formatTimeDuration(long timeDuration) {
		StringBuilder builder = new StringBuilder();
		long hours = timeDuration / (60 * 60 * 1000);
		long mod = (timeDuration % (60 * 60 * 1000));
		long minutes = mod / (60 * 1000);
		mod = mod % (60 * 1000);
		long seconds = mod / 1000;
		mod = mod % 1000;
		long ms = mod;

		if (hours != 0) {
			builder.append(hours);
			builder.append(" hrs, ");
		}
		if (minutes != 0) {
			builder.append(minutes);
			builder.append(" mins, ");
		}
		if (seconds != 0) {
			builder.append(seconds);
			builder.append(" sec, ");
		}
		builder.append(ms);
		builder.append(" ms");
		return builder.toString();
	}

	public static long convertTimeDuration(String duration) {
		long millis = 0;
		String[] splits = duration.split(",");
		for (String split : splits) {
			split = split.trim();
			String time = split.split(" ")[0];
			String unit = split.split(" ")[1];
			if (unit.equals("hrs")) {
				millis = millis + Long.parseLong(time) * 60 * 60 * 1000;
			} else if (unit.equals("mins")) {
				millis = millis + Long.parseLong(time) * 60 * 1000;
			} else if (unit.equals("sec")) {
				millis = millis + Long.parseLong(time) * 1000;
			} else if (unit.equals("ms")) {
				millis = millis + Long.parseLong(time);
			}
		}
		return millis;
	}
}
