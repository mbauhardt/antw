package asls.ant;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

import asls.model.Projects;
import asls.model.Target;
import asls.util.TimeUtil;

public class StatisticLogger extends LoggerAdapter {

    boolean _subProjectBuilding = false;
    private Projects _projects = new Projects();
    private String _lastStartedProject;
    private Date _start;
    private Date _finish;

    public void buildFinished(BuildEvent event) {
        _finish = new Date();
        if (event.getException() != null) {
            err(event.getException());
            out("BUILD FAILED");
        } else {
            out("BUILD SUCCESSFUL");
        }
        out("Total time: " + TimeUtil.formatTimeDuration(_finish.getTime() - _start.getTime()));
    }

    @Override
    public void buildStarted(BuildEvent event) {
        _start = new Date();
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
        if (!projectName.equals(_lastStartedProject)) {
            out("");
        }
        _lastStartedProject = projectName;
        String targetName = event.getTarget().getName();
        _projects.get(projectName).getTarget(targetName).addStartTime(new Date()).increment();
        if (!_subProjectBuilding) {
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
