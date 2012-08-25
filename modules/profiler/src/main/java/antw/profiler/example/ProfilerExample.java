package antw.profiler.example;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import antw.profiler.MethodCall;
import antw.profiler.MethodStack;
import antw.profiler.Profiler;

public class ProfilerExample {

    public static void main(String[] args) {

        Developer developer = new Developer("bob", "java");
        developer.getLanguage().getName();

        Map<Long, MethodStack> methodCallMapping = Profiler.getMethodStacks();
        Set<Long> keySet = methodCallMapping.keySet();
        for (Long threadId : keySet) {
            MethodStack methodStack = methodCallMapping.get(threadId);
            print("", methodStack.getMethodCall());
        }
    }

    private static void print(String prefix, MethodCall methodCall) {
        System.out.println(prefix + methodCall.getMethod().toString());
        Collection<MethodCall> children = methodCall.getChildren();
        for (MethodCall methodCall2 : children) {
            print(prefix + " ", methodCall2);
        }
    }

}
