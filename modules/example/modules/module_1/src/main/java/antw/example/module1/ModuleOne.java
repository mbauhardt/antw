package antw.example.module1;

public class ModuleOne {

    private String _name;

    @Override
    public String toString() {
        return "module1";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    public void setName(String name) {
        _name = name;
    }

    public String getName() {
        return _name;
    }
}
