package antw.model;

public class TestSuites extends Names<TestSuite> {

    public TestSuites() {
        super(TestSuite.class);
    }

    public TestSuite getTestSuite(String name) {
        return get(name);
    }

}
