package antw.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;


public class ExceptionUtil {

    public static RuntimeException convertToRuntimeException(String message,
	    Throwable throwable) {
	return new RuntimeException(message, throwable);
    }

    public static String getStackTraceAsString(Throwable throwable) {
	StringWriter stringWriter = new StringWriter();
	throwable.printStackTrace(new PrintWriter(stringWriter));
	return stringWriter.toString();
    }

}
