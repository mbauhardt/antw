package asls.model;

import java.util.Collection;

public class Targets extends Names<Target> {

    public Targets() {
        super(Target.class);
    }

    public Collection<Target> computeRelativeBuildTime(long duration) {
        Collection<Target> targets = values();
        for (Target target : targets) {
            target.computeRelativeBuildTime(duration);
        }
        return targets;
    }

}
