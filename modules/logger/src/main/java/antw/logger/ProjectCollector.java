package antw.logger;

import java.util.Date;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.SubBuildListener;


public class ProjectCollector implements BuildListener, SubBuildListener {

    private final LoggerContext _context;

    public ProjectCollector(LoggerContext context) {
        _context = context;
    }

    @Override
    public void buildStarted(BuildEvent event) {
        _context.getProjects().setStart(new Date());
    }

    @Override
    public void buildFinished(BuildEvent event) {
        _context.getProjects().setEnd(new Date());
    }

    @Override
    public void targetStarted(BuildEvent event) {
        _context.getTarget(event).addStartTime(new Date()).increment();
    }

    @Override
    public void targetFinished(BuildEvent event) {
        _context.getTarget(event).addFinishTime(new Date());
    }

    @Override
    public void taskStarted(BuildEvent event) {

    }

    @Override
    public void taskFinished(BuildEvent event) {

    }

    @Override
    public void messageLogged(BuildEvent event) {

    }

    @Override
    public void subBuildStarted(BuildEvent event) {
        _context.getProject(event).setSubProject(true);

    }

    @Override
    public void subBuildFinished(BuildEvent event) {

    }

}
