package antw.profiler;

import java.util.Date;

public class MethodStack {

    private long _threadId;

    private MethodCall _methodCall;

    private ApplicationStack _applicationStack;

    private boolean _enabled = true;

    private MethodCall _rootMethod;

    public MethodStack(long threadId, ApplicationStack applicationStack, boolean enabled) {
        _threadId = threadId;
        _applicationStack = applicationStack;
        _enabled = enabled;
        _rootMethod = new MethodCall(null, new Method(Profiler.class.getName(), "<init>"));
        _rootMethod.setStartTime(new Date());
        registerMethodCall(_rootMethod);
        _applicationStack.registerMethodStack(_threadId, this);
    }

    public long getThreadId() {
        return _threadId;
    }

    public void registerMethodCall(MethodCall methodCall) {
        _methodCall = methodCall;
    }

    public void deregisterMethodCall(MethodCall methodCall) {
        _methodCall = methodCall.getParent();
    }

    public MethodCall reverseFindMethodCall(Method method) {
        MethodCall currentMethodCall = getCurrentMethodCall();
        if (currentMethodCall == null) {
            return null;
        }
        while (!currentMethodCall.getMethod().equals(method)) {
            currentMethodCall.setEndTime(new Date());
            currentMethodCall = currentMethodCall.getParent();
            if (currentMethodCall == null) {
                break;
            }

        }
        if (!method.equals(currentMethodCall.getMethod())) {
            throw new IllegalArgumentException("method [" + method + "] not found in stack");
        }
        return currentMethodCall;
    }

    public MethodCall getCurrentMethodCall() {
        return _methodCall;
    }

    public void enable() {
        _enabled = true;
    }

    public void disable() {
        _enabled = false;
    }

    public boolean isEnabled() {
        return _enabled;
    }

    public void clear() {
        _rootMethod.clear();
    }

    public MethodCall getRootMethod() {
        return _rootMethod;
    }
}
