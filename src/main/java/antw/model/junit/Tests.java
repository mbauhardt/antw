package antw.model.junit;

import antw.model.Names;

public class Tests extends Names<TestCase> {

    public Tests() {
        super(TestCase.class);
    }

    public TestCase getTest(String name) {
        return get(name);
    }

}
