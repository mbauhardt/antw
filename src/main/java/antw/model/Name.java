package antw.model;

public class Name extends Counter {

    private String _name;

    public Name() {
    }

    public Name(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

}
