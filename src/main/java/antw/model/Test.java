package antw.model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import antw.util.TimeUtil;

public class Test extends Name {

    public static enum Status {
        ERROR, FAILURE, PASSED
    }

    public static enum Category {

        FLASH(TimeUnit.MILLISECONDS.toMillis(500)), SHORT(FLASH, TimeUnit.MINUTES.toMillis(1)), MEDIUM(SHORT,
                TimeUnit.MINUTES.toMillis(3)), LONG(MEDIUM, TimeUnit.MINUTES.toMillis(7)), UUH(LONG, TimeUnit.DAYS
                .toMillis(Integer.MAX_VALUE));

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
    private String _message;
    private Date _start;
    private Date _end;

    public Test setStatus(Status status) {
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

    public Test setStartTime(Date date) {
        _start = date;
        return this;
    }

    public Date getStartTime() {
        return _start;
    }

    public Test setFinishTime(Date date) {
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
}
