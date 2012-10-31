package antw.profiler.example;

import java.util.Collection;

import antw.profiler.MethodCall;
import antw.profiler.Profiler;

public class ProfilerExample {

    public static void main(String[] args) {

        Developer developer = new Developer("bob", "java");
        developer.getLanguage().getName();

        MethodCall rootCall = Profiler.getRootMethodFromCurrentThread();
        print(" ", rootCall);
    }

    private static void print(String prefix, MethodCall methodCall) {
        System.out.println(prefix + methodCall.getMethod().toString());
        Collection<MethodCall> children = methodCall.getChildren();
        for (MethodCall methodCall2 : children) {
            print(prefix + " ", methodCall2);
        }
    }

}
