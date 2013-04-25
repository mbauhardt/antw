package antw.junit.model;

import java.util.concurrent.TimeUnit;

import antw.common.model.Name;

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

	FLASH(_oneSecond), FAST(FLASH, _tenSeconds), ONE_MIN(FAST, _oneMinute), THREE_MIN(
		ONE_MIN, _threeMinutes), SEVEN_MIN(THREE_MIN, _sevenMinutes), UUH(
		SEVEN_MIN, _manyDays);

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
    private String _stackTrace = null;
    private TestSuite _testSuite;

    public TestCase setStatus(Status status) {
	_status = status;
	return this;
    }

    public Status getStatus() {
	return _status;
    }

    public TestCase setMessage(String message) {
	_message = message;
	return this;
    }

    public String getMessage() {
	return _message;
    }

    public void setStackTrace(String stackTrace) {
	_stackTrace = stackTrace;
    }

    public String getStackTrace() {
	return _stackTrace;
    }

    public Category getCategory() {
	long ms = getDuration();
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

    public boolean error() {
	return _status == Status.ERROR;
    }
}
