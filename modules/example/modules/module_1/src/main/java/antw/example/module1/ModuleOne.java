package antw.example.module1;

public class ModuleOne {

    @Override
    public String toString() {
        return "module1";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
