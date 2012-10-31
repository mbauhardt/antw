package antw.example.sun;

public class Sun {

    private int _clock;

    public Sun(int clock) {
        _clock = clock;
    }

    public boolean sunrise() {
        return _clock >= 5 && _clock <= 7;
    }

    public boolean sunset() {
        return _clock >= 18 && _clock <= 20;
    }

    public boolean sunshine() {
        return !sunrise() && !sunset();
    }
}
