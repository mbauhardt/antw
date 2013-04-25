package antw.common.model;

import java.util.Date;

import antw.common.util.TimeUtil;

public class Duration extends Counter {

    private Date _start;
    private Date _end;
    private long _duration;

    public Duration setStart(Date start) {
	_start = start;
	return this;
    }

    public Date getStart() {
	return _start;
    }

    public Duration setEnd(Date end) {
	_end = end;
	return this;
    }

    public Date getEnd() {
	return _end;
    }

    public Duration computeRelativeDuration(long duration) {
	_duration = (long) ((getDuration()) * 100 / duration);
	return this;
    }

    public long getRelativeDuration() {
	return _duration;
    }

    public Long getDuration() {
	return _end.getTime() - _start.getTime();
    }

    public String getDurationAsString() {
	return TimeUtil.formatTimeDuration(getDuration());
    }

}
