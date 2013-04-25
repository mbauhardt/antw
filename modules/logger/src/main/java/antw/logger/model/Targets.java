package antw.logger.model;

import java.util.Collection;

import antw.common.model.Names;

public class Targets extends Names<Target> {

    public Targets() {
	super(Target.class);
    }

    public Collection<Target> computeRelativeDurationOverTargets(long duration) {
	Collection<Target> values = values();
	for (Target target : values) {
	    target.computeRelativeDuration(duration);
	}
	return values;
    }

}
