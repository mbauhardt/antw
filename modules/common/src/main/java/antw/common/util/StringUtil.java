package antw.common.util;

public class StringUtil {

    public static String remove(String toRemove, String string) {
	return remove(toRemove, string, true);
    }

    public static String remove(String toRemove, String string, boolean trim) {
	string = string.replace(toRemove, "");
	if (trim) {
	    string = string.trim();
	}
	return string;
    }

    public static int countFirstWhiteSpaces(String message) {
	int count = 0;
	for (int i = 0; i < message.length(); i++) {
	    char charAt = message.charAt(i);
	    if (charAt == ' ') {
		count++;
	    } else {
		break;
	    }
	}
	return count;
    }

    public static String padding(String name, int padding) {
	if (name == null) {
	    return "";
	}
	if (name.length() < padding) {
	    for (int i = name.length(); i < padding; i++) {
		name += " ";
	    }
	}
	return name;
    }
}
