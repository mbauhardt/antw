package antw.profiler;

public class Profiler {

    public static void start(String className, String methodName) {
        System.out.println("start: " + Thread.currentThread().getId() + className + "." + methodName);
    }

    public static void end(String className, String methodName) {
        System.out.println("end: " + Thread.currentThread().getId() + className + "." + methodName);
    }
}
