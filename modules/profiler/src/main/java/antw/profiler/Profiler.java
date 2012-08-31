package antw.profiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
        if (!isEnabled()) {
            return;
        }
        MethodStack methodStack = _currentStack.get();
        MethodCall parent = methodStack.getMethodCall();
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
        if (!isEnabled()) {
            return;
        }
        MethodStack methodStack = _currentStack.get();
        if (methodStack.hasItems()) {
            MethodCall methodCall = methodStack.getMethodCall();
            methodCall.setEndTime(new Date());
            methodStack.deregisterMethodCall(methodCall);
        }
    }

    public static Map<Long, MethodStack> getMethodStacks() {
        return _applicationStack.getMethodStacks();
    }

    public static Map<Long, List<MethodCall>> getMethodCalls() {
        Map<Long, List<MethodCall>> methodCalls = new LinkedHashMap<Long, List<MethodCall>>();
        Set<Long> threadIds = getMethodStacks().keySet();
        for (Long threadId : threadIds) {
            MethodStack methodStack = getMethodStacks().get(threadId);
            Collection<MethodCall> children = methodStack.getMethodCall().getChildren();
            methodCalls.put(threadId, new ArrayList<MethodCall>(children));
        }
        return methodCalls;
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
}
