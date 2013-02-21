package antw.logger;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.Task;

import antw.common.Printer;
import antw.common.util.StringUtil;
import antw.common.util.TimeUtil;
import antw.logger.model.Project;
import antw.logger.model.Target;

public class PowerLineLogger extends LoggerAdapter {

    private class Flush extends TimerTask {

	private Printer _printer;

	private String[] _progress = new String[] { "|", "/", "-", "\\", "|",
		"/", "-", "\\" };
	private int _counter = 0;

	public Flush(Printer printer) {
	    _printer = printer;
	}

	@Override
	public void run() {

	    if (_project == null || _target == null || _task == null
		    || _message == null) {
		return;
	    }
	    append("\r");
	    for (int i = 0; i < 140; i++) {
		append(" ");
	    }
	    append("\r");
	    _printer.out(
		    "%.4s %.8s %.7s %.20s %.7s %.20s %.7s %.20s %.7s %.50s",
		    new Object[] {
			    _progress[_counter++ % _progress.length] + "   ",
			    TimeUtil.formatDate(new Date()),
			    "   >   ",
			    StringUtil.padding(
				    "\033[0;34m" + _project.getName(), 20),
			    "   >   ",
			    StringUtil.padding(
				    "\033[0;33m" + _target.getName(), 20),
			    "   >   ",
			    StringUtil.padding(
				    "\033[0;35m" + _task.getTaskName(), 20),
			    "   >   ",
			    "\033[0;36m" + StringUtil.padding(_message, 20) });
	    _printer.append("\033[0;00m");
	    // _printer.append("\033[0;00m ");
	}

    }

    private LoggerContext _context;
    private int _level;

    private Target _target;
    private Project _project;
    private Task _task;
    private Timer _timer;
    private String _message;

    public PowerLineLogger(LoggerContext context) {
	super("powerline");
	_context = context;
	_timer = new Timer();
    }

    @Override
    public void targetStarted(BuildEvent event) {
	_target = _context.getTarget(event);
	if (!_context.getProject(event).equals(_project)) {
	    newLine();
	}
	_project = _target.getProject();
    }

    @Override
    public void taskStarted(BuildEvent event) {
	_task = event.getTask();
    }

    @Override
    public void buildStarted(BuildEvent event) {
	_timer.schedule(new Flush(this), new Date(), 200);
	newLine();
	newLine();
    }

    @Override
    public void buildFinished(BuildEvent event) {
	_timer.cancel();
	newLine();
	newLine();
	newLine();
	if (event.getException() != null) {
	    err(event.getException(), false);
	    out("BUILD FAILED :(");
	} else {
	    out("BUILD SUCCESSFUL :)");
	}
    }

    @Override
    public void messageLogged(BuildEvent event) {
	int priority = event.getPriority();
	if (priority <= _level) {
	    _message = event.getMessage();
	}
    }

    @Override
    public void setMessageOutputLevel(int level) {
	_level = level;
    }

}
