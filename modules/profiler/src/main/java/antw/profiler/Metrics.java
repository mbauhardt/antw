package antw.profiler;

public class Metrics {

    private long _count = 0;
    private long _time = 0;

    public void inc(long time) {
        _count++;
        _time += time;
    }

    public long getCount() {
        return _count;
    }

    public long getTime() {
        return _time;
    }

    @Override
    public String toString() {
        return _time + " ms." + " " + _count + " times";
    }
}
