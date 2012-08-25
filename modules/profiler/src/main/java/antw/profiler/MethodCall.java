package antw.profiler;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class MethodCall {

    private long _lastStartTime = 0;
    private Metrics _metrics = new Metrics();
    private MethodCall _parent;
    private Method _method;
    private Map<Method, MethodCall> _children = new LinkedHashMap<Method, MethodCall>();

    public MethodCall(MethodCall parent, Method method) {
        _parent = parent;
        _method = method;
        if (parent != null) {
            parent.addChild(this);
        }
        method.setMethodCall(this);
    }

    public Collection<MethodCall> getChildren() {
        return _children.values();
    }

    public MethodCall getParent() {
        return _parent;
    }

    public Method getMethod() {
        return _method;
    }

    public MethodCall setStartTime(Date startTime) {
        _lastStartTime = startTime.getTime();
        return this;
    }

    public void setEndTime(Date endTime) {
        if ((endTime.getTime() - _lastStartTime) > 0) {
            _metrics.inc(endTime.getTime() - _lastStartTime);
        } else {
            _metrics.inc(0);
        }
        _lastStartTime = 0;
    }

    private void addChild(MethodCall methodCall) {
        _children.put(methodCall.getMethod(), methodCall);
    }

    public MethodCall findMethodCall(String className, String methodName) {
        return findMethodCall(new Method(className, methodName));
    }

    public MethodCall findMethodCall(Method method) {
        return _method != null && _method.equals(method) ? _method.getMethodCall() : _children.get(method);
    }

    public long getTime() {
        return _metrics.getTime();
    }

    public long getCount() {
        return _metrics.getCount();
    }

    @Override
    public String toString() {
        String ret = _method.toString() + " " + _metrics.toString();
        for (MethodCall methodCall : _children.values()) {
            ret += ("\r\n\t" + methodCall.toString());
        }
        return ret;
    }

    public boolean alreadyCalled(String className, String methodName) {
        return (_method != null && _method.equals(new Method(className, methodName)))
                || _children.containsKey(new Method(className, methodName));
    }
}
