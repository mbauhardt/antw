package antw.model.junit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import antw.model.Name;

public class TestSuite extends Name {

    private Tests _tests = new Tests();

    public TestCase getTest(String name) {
        return _tests.getTest(name).setTestSuite(this);
    }

    public boolean hasFailingTests() {
        Collection<TestCase> values = _tests.values();
        for (TestCase testCase : values) {
            if (testCase.failed()) {
                return true;
            }
        }
        return false;
    }

    public Collection<TestCase> getFailingTests() {
        List<TestCase> testCases = new ArrayList<TestCase>();
        for (TestCase testCase : _tests.values()) {
            if (testCase.failed()) {
                testCases.add(testCase);
            }
        }
        return testCases;
    }
}
