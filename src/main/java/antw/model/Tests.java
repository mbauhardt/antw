package antw.model;

public class Tests extends Names<Test> {

    public Tests() {
        super(Test.class);
    }

    public Test getTest(String name) {
        return get(name);
    }

}
