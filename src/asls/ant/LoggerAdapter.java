package asls.ant;

import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.SubBuildListener;

public class LoggerAdapter implements BuildLogger, SubBuildListener {

	private PrintStream _err;
	private PrintStream _out;

	public void buildStarted(BuildEvent event) {

	}

	public void buildFinished(BuildEvent event) {

	}

	public void targetStarted(BuildEvent event) {

	}

	public void targetFinished(BuildEvent event) {

	}

	public void taskStarted(BuildEvent event) {

	}

	public void taskFinished(BuildEvent event) {

	}

	public void messageLogged(BuildEvent event) {

	}

	public void subBuildStarted(BuildEvent event) {
	}

	public void subBuildFinished(BuildEvent event) {

	}

	public void setMessageOutputLevel(int level) {

	}

	public void setOutputPrintStream(PrintStream output) {
		_out = output;
	}

	public void setEmacsMode(boolean emacsMode) {

	}

	public void setErrorPrintStream(PrintStream err) {
		_err = err;
	}

	public synchronized void out(String message) {
		_out.println(message);
	}

	public synchronized void out(String logFormat, Object... messages) {
		_out.printf(logFormat, messages);
	}

	public synchronized void err(String message) {
		_err.println(message);
	}

	public synchronized void err(Throwable exception) {
		exception.printStackTrace(_err);
	}
}
