package antw.profiler;

import java.util.HashMap;
import java.util.Map;

public class ApplicationStack {

    private Map<Long, MethodStack> _methodStacks = new HashMap<Long, MethodStack>();

    public void registerMethodStack(Long threadId, MethodStack methodStack) {
        if (_methodStacks.containsKey(threadId)) {
            throw new IllegalArgumentException("method stack already registered: " + methodStack.getThreadId() + "@"
                    + methodStack.getCurrentMethodCall().getMethod().toString());
        }
        _methodStacks.put(threadId, methodStack);
    }

    public Map<Long, MethodStack> getMethodStacks() {
        return _methodStacks;
    }

    public void cleanup(Long threadId) {
        _methodStacks.remove(threadId);
    }
}
