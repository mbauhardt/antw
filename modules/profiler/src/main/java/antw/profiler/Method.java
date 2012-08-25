package antw.profiler;

public class Method {

    private String _className;
    private String _methodName;
    private MethodCall _methodCall;

    public Method(String className, String methodName) {
        _className = className;
        _methodName = methodName;
    }

    public String getClassName() {
        return _className;
    }

    public String getMethodName() {
        return _methodName;
    }

    public boolean same(String className, String methodName) {
        return _className.equals(className) && _methodName.equals(methodName);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((_className == null) ? 0 : _className.hashCode());
        result = prime * result + ((_methodName == null) ? 0 : _methodName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Method other = (Method) obj;
        if (_className == null) {
            if (other._className != null)
                return false;
        } else if (!_className.equals(other._className))
            return false;
        if (_methodName == null) {
            if (other._methodName != null)
                return false;
        } else if (!_methodName.equals(other._methodName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return _className + "." + _methodName;
    }

    public void setMethodCall(MethodCall methodCall) {
        _methodCall = methodCall;
    }

    public MethodCall getMethodCall() {
        return _methodCall;
    }

}
