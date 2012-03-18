package antw.logger;

import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.SubBuildListener;

import antw.common.Printer;

public class LoggerAdapter extends Printer implements BuildLogger, SubBuildListener {

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
        setOutputPrint(output);
    }

    public void setEmacsMode(boolean emacsMode) {

    }

    public void setErrorPrintStream(PrintStream err) {
        setErrorPrint(err);
    }

}
