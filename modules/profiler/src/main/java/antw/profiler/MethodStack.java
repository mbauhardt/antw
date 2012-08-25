package antw.profiler;

public class MethodStack {

    private long _threadId;

    private MethodCall _methodCall;

    private ApplicationStack _applicationStack;

    private boolean _enabled;

    public MethodStack(long threadId, ApplicationStack applicationStack, boolean enabled) {
        _threadId = threadId;
        _applicationStack = applicationStack;
        _enabled = enabled;
        registerMethodCall(new MethodCall(null, new Method(Profiler.class.getName(), "init()")));
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

    public MethodCall getMethodCall() {
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

    public boolean hasItems() {
        return _methodCall != null;
    }

}
