package antw.common;

import java.io.PrintStream;

public class Printer {

    private PrintStream _out;
    private PrintStream _err;

    public Printer setOutputPrint(PrintStream output) {
        _out = output;
        return this;
    }

    public Printer setErrorPrint(PrintStream err) {
        _err = err;
        return this;
    }

    public PrintStream getOutputPrint() {
        return _out;
    }

    public PrintStream getErrorPrint() {
        return _err;
    }

    public synchronized void out(String message) {
        _out.println(message);
    }

    public synchronized void append(String message) {
        _out.append(message);
    }

    public synchronized void out(String logFormat, Object... messages) {
        _out.printf(logFormat, messages);
    }

    public synchronized void err(String message) {
        _err.println(message);
    }

    public synchronized void err(Throwable exception, boolean withStack) {
        if (withStack) {
            exception.printStackTrace(_err);
        } else {
            _err.print(exception.getMessage());
        }
    }

    public synchronized void newLine() {
        _out.append("\n");
    }

    public synchronized void newLine(int lineCount) {
        for (int i = 0; i < lineCount; i++) {
            newLine();
        }
    }

    public void tab() {
        _out.append("\t");
    }

    public void space(int i) {
        for (int j = 0; j < i; j++) {
            _out.append(' ');
        }
    }

    public String buildSpaces(int spaces) {
        String space = "";
        for (int i = 0; i < spaces; i++) {
            space += " ";
        }
        return space;
    }

}
