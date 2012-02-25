package antw.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import antw.util.TimeUtil;

public class TestCase extends Name {

    private static long _oneSecond = TimeUnit.SECONDS.toMillis(1);
    private static long _tenSeconds = TimeUnit.SECONDS.toMillis(10);
    private static long _oneMinute = TimeUnit.MINUTES.toMillis(1);
    private static long _threeMinutes = TimeUnit.MINUTES.toMillis(3);
    private static long _sevenMinutes = TimeUnit.MINUTES.toMillis(7);
    private static long _manyDays = TimeUnit.DAYS.toMillis(Integer.MAX_VALUE);

    public static enum Status {
        ERROR, FAILURE, PASSED
    }

    public static enum Category {

        FLASH(_oneSecond), VERY_SHORT(FLASH, _tenSeconds), SHORT(VERY_SHORT, _oneMinute), MEDIUM(SHORT, _threeMinutes), LONG(
                MEDIUM, _sevenMinutes), UUH(LONG, _manyDays);

        private long _ms;
        private Category _prev;

        Category(long ms) {
            _ms = ms;
        }

        Category(Category prev, long ms) {
            _prev = prev;
            _ms = ms;
        }

        public long maxDuration() {
            return _ms;
        }

        public static Category get(long ms) {
            Category[] values = values();
            for (Category category : values) {
                if (ms <= category._ms) {
                    if (category._prev == null) {
                        return category;
                    }
                    if (ms > category._prev._ms) {
                        return category;
                    }
                }
            }
            throw new RuntimeException("no category found");
        }
    }

    private Status _status = Status.PASSED;
    private String _message = "";
    private Date _start;
    private Date _end;
    private TestSuite _testSuite;

    public TestCase setStatus(Status status) {
        _status = status;
        return this;
    }

    public Status getStatus() {
        return _status;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public String getMessage() {
        return _message;
    }

    public TestCase setStartTime(Date date) {
        _start = date;
        return this;
    }

    public Date getStartTime() {
        return _start;
    }

    public TestCase setFinishTime(Date date) {
        _end = date;
        return this;
    }

    public Date getFinishTime() {
        return _end;
    }

    public String getDuration() {
        return TimeUtil.formatTimeDuration(_end.getTime() - _start.getTime());
    }

    public Category getCategory() {
        long ms = _end.getTime() - _start.getTime();
        return Category.get(ms);
    }

    public TestCase setTestSuite(TestSuite testSuite) {
        _testSuite = testSuite;
        return this;
    }

    public TestSuite getTestSuite() {
        return _testSuite;
    }

    public boolean failed() {
        return _status == Status.ERROR || _status == Status.FAILURE;
    }
}
