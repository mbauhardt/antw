package antw.logger.model;

import java.util.Collection;

import antw.common.model.Names;

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
