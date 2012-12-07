package antw.junit;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import antw.common.util.Constants;
import antw.junit.model.TestCase;
import antw.junit.model.TestSuite;
import antw.junit.model.TestSuites;
import antw.junit.model.TestCase.Status;
import antw.junit.util.TestUtil;

public class JUnitStandardFormatter extends antw.common.Printer implements
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
	_testSuites.get(suiteName).getTest(testCase).setStatus(Status.FAILURE)
		.setMessage(t.getMessage());
    }

    @Override
    public void startTestSuite(JUnitTest unitTest) throws BuildException {
	TestSuite testSuite = _testSuites.get(unitTest.getName());
	newLine();
	out("%-20s %-65s %n", new Object[] { Constants.TEST_SUITE_LABEL,
		testSuite.getName() });
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
	space(4);
	out("%-20s %-65s %-15s %-15s %-15s %-10s %n", new Object[] {
		Constants.TEST_CASE_LABEL, "  " + testCase.getName() + "  ",
		testCase.getDurationAsString(), testCase.getCategory(),
		testCase.getStatus(), testCase.getMessage() });
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
