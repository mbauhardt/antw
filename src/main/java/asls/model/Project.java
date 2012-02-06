package asls.model;

import java.util.Collection;

public class Project extends Name {

    private Targets _targets = new Targets();

    public Project() {
    }

    public Target getTarget(String name) {
        Target target = _targets.get(name);
        target.setProject(this);
        return target;
    }

    public Collection<Target> computeRelativeBuildTime(long duration) {
        return _targets.computeRelativeBuildTime(duration);
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        Project other = (Project) obj;
        return this.getName().equals(other.getName());
    }

}
