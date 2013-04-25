package antw.logger.model;

import antw.common.model.Name;

public class Project extends Name {

    private boolean _subProject;
    private Targets _targets = new Targets();

    public Project() {
    }

    public Project(String name) {
	super(name);
    }

    public Targets getTargets() {
	return _targets;
    }

    public Target getTarget(String name) {
	Target target = _targets.get(name);
	target.setProject(this);
	return target;
    }

    public Project setSubProject(boolean subProject) {
	_subProject = subProject;
	return this;
    }

    public boolean isSubProject() {
	return _subProject;
    }

    @Override
    public int hashCode() {
	return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null) {
	    return false;
	}
	Project other = (Project) obj;
	return this.getName().equals(other.getName());
    }

}
