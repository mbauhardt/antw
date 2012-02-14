package antw.model;

public class Counter {

    private int _counter = 0;

    public int increment() {
        return ++_counter;
    }

    public int getCounter() {
        return _counter;
    }

}
