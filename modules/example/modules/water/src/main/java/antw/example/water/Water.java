package antw.example.water;

public class Water {

    private int _celius;

    public Water(int celius) {
        _celius = celius;
    }

    public boolean flow() {
        return _celius >= 0 && _celius <= 100;
    }

    public boolean freeze() {
        return _celius < 0;
    }

}
