package antw.logger;

import org.apache.tools.ant.BuildEvent;

public class MessageLogger extends LoggerAdapter {

    private int _level;

    public MessageLogger() {
	super("message");
    }

    @Override
    public void messageLogged(BuildEvent event) {
	int priority = event.getPriority();
	if (priority <= _level) {
	    out(event.getMessage());
	}
    }

    @Override
    public void setMessageOutputLevel(int level) {
	_level = level;
    }

    @Override
    public void buildFinished(BuildEvent event) {
	if (event.getException() != null) {
	    err(event.getException(), false);
	    out("BUILD FAILED :(");
	} else {
	    out("BUILD SUCCESSFUL :)");
	}
    }
}
