package antw.util;

public class StringUtil {

    public static String remove(String toRemove, String string) {
        return string.replace(toRemove, "").trim();
    }
}
