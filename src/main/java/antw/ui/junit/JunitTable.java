package antw.ui.junit;

import antw.logger.Printer;
import antw.model.TestCase;
import antw.model.TestSuite;

public abstract class JunitTable {

    public abstract void logTestSuiteStarted(Printer printer, TestSuite testSuite);

    public abstract void logTestSuiteFinished(Printer printer, TestSuite testSuite);

    public abstract void logTestCaseStarted(Printer printer, TestCase testCase);

    public abstract void logTestCaseFinished(Printer printer, TestCase testCase);

}
