package antw.model;

public class Tests extends Names<TestCase> {

    public Tests() {
        super(TestCase.class);
    }

    public TestCase getTest(String name) {
        return get(name);
    }

}
