package antw.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import antw.model.TestCase;
import antw.model.TestCase.Status;
import antw.model.TestSuite;
import antw.model.TestSuites;
import antw.ui.JunitStandardTable;
import antw.ui.JunitTable;
import antw.util.TestUtil;

public class JUnitFormatter extends Printer implements JUnitResultFormatter {

    private TestSuites _testSuites = new TestSuites();
    private JunitTable _defaultTable = new JunitStandardTable();
    private JunitTable _plainTable = new JunitPlainTable();
    private Printer _plainPrinter;

    public JUnitFormatter() {
        File folder = new File("build/antw");
        folder.mkdirs();
        try {
            setOutputPrint(new PrintStream(new FileOutputStream(new File(folder, "junit.txt"), true)));
            _plainPrinter = getPlainPrinter(folder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addError(Test test, Throwable t) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        _testSuites.get(suiteName).getTest(testCase).setStatus(Status.ERROR).setMessage(t.getMessage());
    }

    @Override
    public void addFailure(Test test, AssertionFailedError t) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        _testSuites.get(suiteName).getTest(testCase).setStatus(Status.FAILURE).setMessage(t.getMessage());
    }

    @Override
    public void startTestSuite(JUnitTest unitTest) throws BuildException {
        TestSuite testSuite = _testSuites.get(unitTest.getName());
        _defaultTable.logTestSuiteStarted(this, testSuite);
        _plainTable.logTestSuiteStarted(_plainPrinter, testSuite);
    }

    @Override
    public void endTestSuite(JUnitTest unitTest) throws BuildException {
        TestSuite testSuite = _testSuites.getTestSuite(unitTest.getName());
        _defaultTable.logTestSuiteFinished(this, testSuite);
        _plainTable.logTestSuiteFinished(_plainPrinter, testSuite);
    }

    @Override
    public void startTest(Test test) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCaseName = TestUtil.getNameOfTestCase(test);
        TestCase testCase = _testSuites.getTestSuite(suiteName).getTest(testCaseName).setStartTime(new Date());
        _defaultTable.logTestCaseStarted(this, testCase);
        _plainTable.logTestCaseStarted(_plainPrinter, testCase);
    }

    @Override
    public void endTest(Test test) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCaseName = TestUtil.getNameOfTestCase(test);
        TestCase testCase = _testSuites.getTestSuite(suiteName).getTest(testCaseName).setFinishTime(new Date());
        _defaultTable.logTestCaseFinished(this, testCase);
        _plainTable.logTestCaseFinished(_plainPrinter, testCase);
    }

    @Override
    public void setOutput(OutputStream outputStream) {
    }

    @Override
    public void setSystemError(String err) {
    }

    @Override
    public void setSystemOutput(String out) {
    }

    private Printer getPlainPrinter(File reportDir) {
        try {
            PrintStream printStream = new PrintStream(
                    new FileOutputStream(new File(reportDir, "junit-plain.tsv"), true));
            return new Printer().setOutputPrint(printStream).setErrorPrint(printStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
