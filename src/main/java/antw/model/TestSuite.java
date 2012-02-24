package antw.model;

public class TestSuite extends Name {

    private Tests _tests = new Tests();

    public Test getTest(String name) {
        return _tests.getTest(name);
    }
}
