package antw.ui.junit;

import antw.logger.Printer;
import antw.model.TestCase;
import antw.model.TestSuite;

public class JunitPlainTable extends JunitTable {

    private static final String TAB = "\t";

    @Override
    public void logTestSuiteStarted(Printer printer, TestSuite testSuite) {

    }

    @Override
    public void logTestSuiteFinished(Printer printer, TestSuite testSuite) {

    }

    @Override
    public void logTestCaseStarted(Printer printer, TestCase testCase) {

    }

    @Override
    public void logTestCaseFinished(Printer printer, TestCase testCase) {
        printer.out(testCase.getTestSuite().getName() + TAB + testCase.getName() + TAB + testCase.getDuration() + TAB
                + testCase.getCategory() + TAB + testCase.getStatus() + TAB + testCase.getMessage());

    }

}
