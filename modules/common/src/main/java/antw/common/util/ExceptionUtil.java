package antw.common.util;

public class ExceptionUtil {

    public static RuntimeException convertToRuntimeException(String message, Throwable throwable) {
        return new RuntimeException(message, throwable);
    }
}
