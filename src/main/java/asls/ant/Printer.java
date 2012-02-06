package asls.ant;

import java.io.PrintStream;

public class Printer {

    private PrintStream _out;
    private PrintStream _err;

    public void setOutputPrint(PrintStream output) {
        _out = output;
    }

    public void setErrorPrint(PrintStream err) {
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

    public synchronized void newLine() {
        _out.append("\n");
    }

    public synchronized void newLine(int lineCount) {
        for (int i = 0; i < lineCount; i++) {
            newLine();
        }
    }
}
