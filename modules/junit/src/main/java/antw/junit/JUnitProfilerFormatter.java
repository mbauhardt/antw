package antw.junit;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import antw.common.util.Constants;
import antw.common.util.StringUtil;
import antw.junit.util.TestUtil;
import antw.profiler.MethodCall;
import antw.profiler.Profiler;

public class JUnitProfilerFormatter extends JUnitStandardFormatter {

	@Override
	public void startTest(Test test) {
		super.startTest(test);
	}

	@Override
	public void endTest(Test test) {
		super.endTest(test);
		flushProfiling(test);
		Profiler.reset();
	}

	@Override
	public void startTestSuite(JUnitTest suite) throws BuildException {
		super.startTestSuite(suite);
	}

	@Override
	public void endTestSuite(JUnitTest suite) throws BuildException {
		super.endTestSuite(suite);
	}

	private void flushProfiling(Test test) {
		space(8);
		out("%-20s %-65s %n", new Object[] { Constants.PROFILER_LABEL, "" });
		space(8);
		out("%-20s %-65s %n",
				new Object[] { Constants.PROFILER_LABEL,
						TestUtil.getFullQualifiedName(test) });
		Map<Long, MethodCall> rootMethods = Profiler.getRootMethods();
		Set<Long> threadIds = rootMethods.keySet();
		for (Long threadId : threadIds) {
			MethodCall methodCall = rootMethods.get(threadId);
			out(1, methodCall);
		}
	}

	private void out(int spaces, MethodCall methodCall) {
		space(8);
		String space = buildSpaces(spaces);
		out("%-20s %-65s %-11s %-11s %n",
				new Object[] { Constants.PROFILER_LABEL,
						space + methodCall.getMethod(),
						methodCall.getCount() + " times",
						methodCall.getTime() + " ms" });
		Collection<MethodCall> children = methodCall.getChildren();
		for (MethodCall child : children) {
			out(spaces + 1, child);
		}
	}

	@Override
	public void setOutput(OutputStream outputStream) {
		setOutputPrint(new PrintStream(outputStream));
		setErrorPrint(new PrintStream(outputStream));
	}

	@Override
	public void setSystemError(String error) {
		err(error);
	}

	@Override
	public void setSystemOutput(String output) {
		out(output);
	}

}
