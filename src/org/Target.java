package org;

import java.util.Date;

public class Target extends Name {

	private Date _start;
	private Date _finish;
	private Project _project;

	public Target() {
	}

	public Target addStartTime(Date start) {
		_start = start;
		return this;
	}

	public Date getStart() {
		return _start;
	}

	public Target addFinishTime(Date finish) {
		_finish = finish;
		return this;
	}

	public Date getFinish() {
		return _finish;
	}

	public String logFormat() {
		return "%-25s %-40s %-15d %-15tT %-15tT %-15s%n";
	}

	public Object[] logParameter() {
		Object[] strings = new Object[] {
				getProject().getName(),
				getName(),
				getCounter(),
				getStart(),
				getFinish(),
				TimeUtil.formatTimeDuration(getFinish().getTime()
						- getStart().getTime()) };
		return strings;
	}

	public void setProject(Project project) {
		_project = project;
	}

	public Project getProject() {
		return _project;
	}

}
