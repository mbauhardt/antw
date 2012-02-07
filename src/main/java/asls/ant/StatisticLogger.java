package asls.ant;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;

import asls.model.Projects;
import asls.model.Target;

public class StatisticLogger extends LoggerAdapter {

    private Projects _projects = new Projects();
    private Table _table = new DurationTable();

    public void buildFinished(BuildEvent event) {
        _projects.setEnd(new Date());
        _table.logBuildFinished(this, _projects);
        newLine();
        if (event.getException() != null) {
            err(event.getException());
            out("BUILD FAILED");
        } else {
            out("BUILD SUCCESSFUL");
        }
    }

    @Override
    public void buildStarted(BuildEvent event) {
        _projects.setStart(new Date());
    }

    @Override
    public void messageLogged(BuildEvent event) {
    }

    @Override
    public void targetFinished(BuildEvent event) {
        String projectName = event.getProject().getName();
        String targetName = event.getTarget().getName();
        Target target = _projects.get(projectName).getTarget(targetName).addFinishTime(new Date());
        _table.logTargetFinished(this, target);
    }

    @Override
    public void targetStarted(BuildEvent event) {
        String projectName = event.getProject().getName();
        String targetName = event.getTarget().getName();
        _projects.get(projectName).getTarget(targetName).addStartTime(new Date()).increment();
        _table.logTargetStartet(this, _projects.get(projectName).getTarget(targetName));
    }

    @Override
    public void subBuildStarted(BuildEvent event) {
        _projects.get(event.getProject().getName()).setSubProject(true);
    }

    @Override
    public void subBuildFinished(BuildEvent event) {
        _projects.get(event.getProject().getName()).setSubProject(false);
    }

}
