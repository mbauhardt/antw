package antw.logger.model;

import java.util.ArrayList;
import java.util.Collection;

import antw.common.model.Names;

public class Projects extends Names<Project> {

    public Projects() {
	super(Project.class);
    }

    public Collection<Target> computeDurationOverallTargets(long duration) {
	Collection<Target> targets = new ArrayList<Target>();
	Collection<Targets> targetList = getTargets();
	for (Targets targetFromList : targetList) {
	    targets.addAll(targetFromList
		    .computeRelativeDurationOverTargets(duration));
	}
	return targets;
    }

    public Collection<Targets> getTargets() {
	Collection<Targets> targets = new ArrayList<Targets>();
	Collection<Project> values = values();
	for (Project project : values) {
	    targets.add(project.getTargets());
	}
	return targets;
    }

}
