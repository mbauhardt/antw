package org;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

public class StatisticLogger extends LoggerAdapter {

	boolean _subProjectBuilding = false;
	private Projects _projects = new Projects();

	public void buildFinished(BuildEvent event) {
		if (event.getException() != null) {
			err(event.getException());
		}
	}

	@Override
	public void buildStarted(BuildEvent event) {
	}

	@Override
	public void messageLogged(BuildEvent event) {
	}

	@Override
	public void targetFinished(BuildEvent event) {
		String projectName = event.getProject().getName();
		String targetName = event.getTarget().getName();
		Target target = _projects.get(projectName).getTarget(targetName)
				.addFinishTime(new Date());
		out(target.logFormat(), target.logParameter());
		if (!_subProjectBuilding) {
			out("");
		}
	}

	@Override
	public void targetStarted(BuildEvent event) {
		String projectName = event.getProject().getName();
		String targetName = event.getTarget().getName();
		_projects.get(projectName).getTarget(targetName)
				.addStartTime(new Date()).increment();
		if (!_subProjectBuilding) {
			out(projectName.toUpperCase() + "." + targetName.toUpperCase());
		}
	}

	@Override
	public void subBuildStarted(BuildEvent event) {
		_subProjectBuilding = true;
	}

	@Override
	public void subBuildFinished(BuildEvent event) {
		_subProjectBuilding = false;
	}

}
