package asls.ant;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

import asls.model.Projects;
import asls.model.Target;

public class StatisticLogger extends LoggerAdapter {

    boolean _subProjectBuilding = false;
    private Projects _projects = new Projects();

    public void buildFinished(BuildEvent event) {
        if (event.getException() != null) {
            err(event.getException());
            out("BUILD FAILED");
        } else {
            out("BUILD SUCCESSFUL");
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
        Target target = _projects.get(projectName).getTarget(targetName).addFinishTime(new Date());
        out(target.logFormat(), target.logParameter());
        if (!_subProjectBuilding) {
            out("");
        }
    }

    @Override
    public void targetStarted(BuildEvent event) {
        String projectName = event.getProject().getName();
        String targetName = event.getTarget().getName();
        _projects.get(projectName).getTarget(targetName).addStartTime(new Date()).increment();
        if (!_subProjectBuilding) {
            out(projectName.toUpperCase() + "." + targetName.toUpperCase());
            out(_projects.get(projectName).getTarget(targetName).logDescriptionFormat(), _projects.get(projectName)
                    .getTarget(targetName).logDescription());
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
