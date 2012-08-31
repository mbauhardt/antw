package antw.junit;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import antw.profiler.MethodCall;
import antw.profiler.Profiler;

public class JUnitProfilerFormatter extends antw.common.Printer implements JUnitResultFormatter {

    @Override
    public void addError(Test test, Throwable t) {

    }

    @Override
    public void addFailure(Test test, AssertionFailedError t) {

    }

    @Override
    public void startTest(Test test) {
    }

    @Override
    public void endTest(Test test) {
        dump();
    }

    @Override
    public void startTestSuite(JUnitTest suite) throws BuildException {
    }

    @Override
    public void endTestSuite(JUnitTest suite) throws BuildException {
        dump();
    }

    private void dump() {
        Map<Long, List<MethodCall>> methodCallMaping = Profiler.getMethodCalls();
        Set<Long> threadIds = methodCallMaping.keySet();
        for (Long threadId : threadIds) {
            List<MethodCall> methodCalls = methodCallMaping.get(threadId);
            for (MethodCall methodCall : methodCalls) {
                out("", methodCall);
            }
        }
    }

    private void out(String prefix, MethodCall methodCall) {
        out(prefix + methodCall.getMethod().toString() + "\t" + methodCall.getCount() + "\t" + methodCall.getTime());
        Collection<MethodCall> children = methodCall.getChildren();
        for (MethodCall child : children) {
            out(prefix + prefix, child);
        }
    }

    @Override
    public void setOutput(OutputStream outputStream) {
        setOutputPrint(new PrintStream(outputStream));
    }

    @Override
    public void setSystemError(String error) {

    }

    @Override
    public void setSystemOutput(String outpit) {

    }

}
