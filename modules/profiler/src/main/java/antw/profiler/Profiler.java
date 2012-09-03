package antw.profiler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Profiler {

    private static boolean _enabled = true;
    private static ApplicationStack _applicationStack = new ApplicationStack();
    private static ThreadLocal<MethodStack> _currentStack = new ThreadLocal<MethodStack>() {

        protected MethodStack initialValue() {
            return new MethodStack(Thread.currentThread().getId(), _applicationStack, _enabled);
        };
    };

    public static void start(String className, String methodName) {
        System.out.println("-> " + className + "." + methodName);
        if (!isEnabled()) {
            return;
        }
        MethodStack methodStack = _currentStack.get();
        MethodCall parent = methodStack.getCurrentMethodCall();
        MethodCall currentMethodCall = null;
        if (parent.findMethodCall(className, methodName) != null) {
            currentMethodCall = parent.findMethodCall(className, methodName);
        } else {
            currentMethodCall = new MethodCall(parent, new Method(className, methodName));
        }
        currentMethodCall.setStartTime(new Date());
        methodStack.registerMethodCall(currentMethodCall);
    }

    private static boolean isEnabled() {
        return _currentStack.get().isEnabled();
    }

    public static void end(String className, String methodName) {
        System.out.println("<- " + className + "." + methodName);
        System.out.println();
        if (!isEnabled()) {
            return;
        }
        MethodStack methodStack = _currentStack.get();
        if (methodStack.hasItems()) {
            MethodCall methodCall = methodStack.getCurrentMethodCall();
            assertMethodCall(methodCall, className, methodName);
            methodCall.setEndTime(new Date());
            methodStack.deregisterMethodCall(methodCall);
        }
    }

    private static void assertMethodCall(MethodCall methodCall, String className, String methodName) {
        if (!methodCall.getMethod().getClassName().equals(className)
                || !methodCall.getMethod().getMethodName().equals(methodName)) {
            // throw new IllegalArgumentException("invalid end of method [" +
            // methodCall.getMethod().toString()
            // + "] on argument [" + className + "." + methodName + "]");
            System.err.println("invalid end of method [" + methodCall.getMethod().toString() + "] on argument ["
                    + className + "." + methodName + "]");
        }

    }

    public static void enable() {
        _currentStack.get().enable();
    }

    public static void disable() {
        _currentStack.get().disable();
    }

    public static void clear() {
        MethodStack methodStack = _currentStack.get();
        methodStack.clear();
    }

    public static MethodCall getRootMethodFromCurrentThread() {
        return getRootMethod(Thread.currentThread().getId());
    }

    public static MethodCall getRootMethod(long threadId) {
        Map<Long, MethodStack> methodStacks = _applicationStack.getMethodStacks();
        return methodStacks.get(threadId).getRootMethod();
    }

    public static Map<Long, MethodCall> getRootMethods() {
        Map<Long, MethodCall> rootMethods = new HashMap<Long, MethodCall>();
        Map<Long, MethodStack> methodStacks = _applicationStack.getMethodStacks();
        Set<Long> threadIds = methodStacks.keySet();
        for (Long threadId : threadIds) {
            MethodStack methodStack = methodStacks.get(threadId);
            MethodCall rootMethod = methodStack.getRootMethod();
            rootMethods.put(threadId, rootMethod);
        }
        return rootMethods;
    }
}
