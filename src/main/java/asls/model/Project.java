package asls.model;

public class Project extends Name {

    private Targets _targets = new Targets();

    public Project() {
    }

    public Target getTarget(String name) {
        Target target = _targets.get(name);
        target.setProject(this);
        return target;
    }

}
