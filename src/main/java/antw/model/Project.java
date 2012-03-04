package antw.model;

import java.util.Collection;


public class Project extends Name {

    private boolean _subProject;
    private Targets _targets = new Targets();

    public Project() {
    }

    public Project(String name) {
        super(name);
    }

    public Target getTarget(String name) {
        Target target = _targets.get(name);
        target.setProject(this);
        return target;
    }

    public Collection<Target> computeRelativeBuildTime(long duration) {
        return _targets.computeRelativeBuildTime(duration);
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
