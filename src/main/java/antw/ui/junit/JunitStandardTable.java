package antw.ui.junit;

import antw.logger.Printer;
import antw.model.junit.TestCase;
import antw.model.junit.TestSuite;

public class JunitStandardTable extends JunitTable {

    @Override
    public void logTestSuiteStarted(Printer printer, TestSuite testSuite) {
        printer.newLine();
        printer.out(testSuite.getName());
    }

    @Override
    public void logTestSuiteFinished(Printer printer, TestSuite testSuite) {

    }

    @Override
    public void logTestCaseStarted(Printer printer, TestCase testCase) {

    }

    @Override
    public void logTestCaseFinished(Printer printer, TestCase testCase) {
        printer.space(4);
        printer.out(
                "%-65s %-15s %-15s %-15s %-10s %n",
                new Object[] { testCase.getName(), testCase.getDurationAsString(), testCase.getCategory(),
                        testCase.getStatus(), testCase.getMessage() });
    }

}
