package antw.logger.model;

import antw.common.model.Name;

public class Target extends Name {

    private Project _project;

    public Target() {
    }

    public void setProject(Project project) {
	_project = project;
    }

    public Project getProject() {
	return _project;
    }

    @Override
    public boolean equals(Object arg0) {
	if (arg0 == null) {
	    return false;
	}
	Target other = (Target) arg0;
	if (!this.getProject().equals(other.getProject())) {
	    return false;
	}
	if (!this.getName().equals(other.getName())) {
	    return false;
	}
	return true;
    }

    @Override
    public int hashCode() {
	return getProject().hashCode() + getName().hashCode();
    }

}
