package antw.junit;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;
import org.junit.runner.Description;
import org.junit.runner.RunWith;

import antw.common.util.Constants;
import antw.common.util.StringUtil;
import antw.junit.model.TestCase;
import antw.junit.model.TestSuite;
import antw.junit.model.TestSuites;
import antw.junit.model.TestCase.Status;
import antw.junit.util.TestUtil;

public class JUnitSimpleFormatter extends antw.common.Printer implements
	JUnitResultFormatter {

    private TestSuites _testSuites = new TestSuites();

    @Override
    public void addError(Test test, Throwable t) {
	String suiteName = TestUtil.getSuiteName(test);
	String testCase = TestUtil.getNameOfTestCase(test);
	_testSuites.get(suiteName).getTest(testCase).setStatus(Status.ERROR)
		.setMessage(t.getMessage());
    }

    @Override
    public void addFailure(Test test, AssertionFailedError t) {
	String suiteName = TestUtil.getSuiteName(test);
	String testCase = TestUtil.getNameOfTestCase(test);
	TestCase status = _testSuites.get(suiteName).getTest(testCase)
		.setStatus(Status.FAILURE);
	if (t.getMessage() != null) {
	    status.setMessage(t.getMessage());
	}
    }

    @Override
    public void startTestSuite(JUnitTest unitTest) throws BuildException {
	TestSuite testSuite = _testSuites.get(unitTest.getName());
	String runWith = getRunWith(testSuite);
	newLine();
	out("%-10s %-80s %n",
		new Object[] {
			StringUtil.padding(Constants.TEST_SUITE_LABEL, 20),
			StringUtil.padding(
				testSuite.getName()
					+ " running"
					+ (runWith == null ? "\033[0;33m without @RunWith annotation \033[0;00m"
						: " with " + runWith), 80) });
    }

    private String getRunWith(TestSuite testSuite) {
	String name = testSuite.getName();
	if (name == null)
	    return null;
	try {
	    RunWith annotation = Class.forName(name).getAnnotation(
		    RunWith.class);
	    if (annotation == null) {
		return null;
	    }
	    return annotation.value().getSimpleName();
	} catch (ClassNotFoundException e) {
	    return null;
	}
    }

    @Override
    public void endTestSuite(JUnitTest unitTest) throws BuildException {
	_testSuites.getTestSuite(unitTest.getName());
    }

    @Override
    public void startTest(Test test) {
	String suiteName = TestUtil.getSuiteName(test);
	String testCaseName = TestUtil.getNameOfTestCase(test);
	_testSuites.getTestSuite(suiteName).getTest(testCaseName)
		.setStartTime(new Date());
    }

    @Override
    public void endTest(Test test) {
	String suiteName = TestUtil.getSuiteName(test);
	String testCaseName = TestUtil.getNameOfTestCase(test);
	TestCase testCase = _testSuites.getTestSuite(suiteName)
		.getTest(testCaseName).setFinishTime(new Date());
	space(2);
	out("%-10s %-50s %-15s %-15s %-10s %n",
		new Object[] {
			StringUtil.padding(Constants.TEST_CASE_LABEL, 20),
			StringUtil.padding(testCase.getName(), 50),
			StringUtil.padding(testCase.getDurationAsString(), 15),
			StringUtil.padding(status(testCase.getStatus()), 15)
				+ "\033[0;00m",

			StringUtil.padding(testCase.getMessage(), 10) });
    }

    private String status(Status status) {
	if (status == Status.PASSED) {
	    return "\033[0;32m" + status + "\033[0;00m";
	}
	return "\033[0;31m" + status + "\033[0;00m";
    }

    @Override
    public void setOutput(OutputStream outputStream) {
	setOutputPrint(new PrintStream(outputStream));
    }

    @Override
    public void setSystemError(String err) {
    }

    @Override
    public void setSystemOutput(String out) {
    }

}
