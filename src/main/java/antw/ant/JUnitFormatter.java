package antw.ant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Date;

import junit.framework.AssertionFailedError;
import junit.framework.Test;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitResultFormatter;
import org.apache.tools.ant.taskdefs.optional.junit.JUnitTest;

import antw.model.Test.Status;
import antw.model.TestSuite;
import antw.model.TestSuites;
import antw.util.TestUtil;

public class JUnitFormatter extends Printer implements JUnitResultFormatter {

    private TestSuites _testSuites = new TestSuites();

    public JUnitFormatter() {
        File folder = new File(".antw");
        folder.mkdirs();
        try {
            setOutputPrint(new PrintStream(new FileOutputStream(new File(folder, "junit.txt"), true)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addError(Test test, Throwable t) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        _testSuites.get(suiteName).getTest(testCase).setStatus(Status.ERROR);
    }

    @Override
    public void addFailure(Test test, AssertionFailedError t) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        _testSuites.get(suiteName).getTest(testCase).setStatus(Status.FAILURE);
    }

    @Override
    public void endTest(Test test) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        antw.model.Test antwTest = _testSuites.getTestSuite(suiteName).getTest(testCase).setFinishTime(new Date());
        space(4);
        out("%-65s %-15s %-15s %-15s %n",
                new Object[] { antwTest.getName(), antwTest.getDuration(), antwTest.getCategory(), antwTest.getStatus() });
    }

    @Override
    public void startTest(Test test) {
        String suiteName = TestUtil.getSuiteName(test);
        String testCase = TestUtil.getNameOfTestCase(test);
        _testSuites.getTestSuite(suiteName).getTest(testCase).setStartTime(new Date());
    }

    @Override
    public void startTestSuite(JUnitTest unitTest) throws BuildException {
        TestSuite testSuite = _testSuites.get(unitTest.getName());
        newLine();
        out(testSuite.getName());
    }

    @Override
    public void endTestSuite(JUnitTest unitTest) throws BuildException {
        _testSuites.getTestSuite(unitTest.getName());
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

}
