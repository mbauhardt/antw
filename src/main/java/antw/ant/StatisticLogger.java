package antw.ant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.util.FileUtils;

import antw.model.Projects;
import antw.model.Target;
import antw.ui.DurationTable;
import antw.ui.PlainDurationTable;
import antw.ui.SummaryTable;
import antw.ui.Table;
import antw.ui.TreeTable;
import antw.util.FileUtil;

public class StatisticLogger extends LoggerAdapter {

    private Projects _projects = new Projects();
    private Table _defaultTable = new TreeTable();
    private Table[] _tables = new Table[] { new DurationTable(), new SummaryTable(), new PlainDurationTable() };
    private Map<String, Printer> _printers = new HashMap<String, Printer>();

    @Override
    public void buildStarted(BuildEvent event) {
        _projects.setStart(new Date());
        newLine();
        File reportDir = createReportDir(event);
        _printers.put(TreeTable.class.getName(), this);
        _printers.put(DurationTable.class.getName(), getDurationPrinter(reportDir));
        _printers.put(SummaryTable.class.getName(), getSummaryPrinter(reportDir));
        _printers.put(PlainDurationTable.class.getName(), getPlainDurationPrinter(reportDir));

        _defaultTable.logBuildStarted(this, _projects);
        for (int i = 0; i < _tables.length; i++) {
            _tables[i].logBuildStarted(_printers.get(_tables[i].getClass().getName()), _projects);
        }

    }

    private Printer getPlainDurationPrinter(File reportDir) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(new File(reportDir, "target-duration.tsv"),
                    true));
            return new Printer().setOutputPrint(printStream).setErrorPrint(printStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Printer getDurationPrinter(File reportDir) {
        try {
            PrintStream printStream = new PrintStream(new FileOutputStream(new File(reportDir, "target-duration.txt"),
                    true));
            return new Printer().setOutputPrint(printStream).setErrorPrint(printStream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Printer getSummaryPrinter(File reportDir) {
        try {
            return new Printer().setOutputPrint(new PrintStream(new File(reportDir, "target-summary.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private File createReportDir(BuildEvent event) {
        File file = new File(event.getProject().getBaseDir(), ".antw/reports");
        file.mkdirs();
        return file;
    }

    public void buildFinished(BuildEvent event) {
        _projects.setEnd(new Date());

        _defaultTable.logBuildFinished(this, _projects);
        for (int i = 0; i < _tables.length; i++) {
            _tables[i].logBuildFinished(_printers.get(_tables[i].getClass().getName()), _projects);
        }

        if (event.getException() != null) {
            newLine(3);
            err(event.getException());
            newLine(3);
            out("BUILD FAILED :(");
        } else {
            newLine(3);
            out("BUILD SUCCESSFUL :)");
        }
        newLine(2);

        File antwFolder = new File(event.getProject().getBaseDir(), "build/antw");
        antwFolder.mkdirs();
        File reportDir = createReportDir(event);
        File[] files = reportDir.listFiles();
        FileUtils fileUtils = FileUtils.getFileUtils();
        for (File file : files) {
            try {
                fileUtils.rename(file, new File(antwFolder, file.getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        List<File> junitFiles = FileUtil.findFiles(event.getProject().getBaseDir(), "junit.txt");
        FileUtil.merge(junitFiles, new File("build/antw/junit.txt"));
        junitFiles = FileUtil.findFiles(event.getProject().getBaseDir(), "junit-plain.tsv");
        FileUtil.merge(junitFiles, new File("build/antw/junit-plain.tsv"));

        FileUtils.delete(reportDir);
    }

    @Override
    public void targetStarted(BuildEvent event) {
        String projectName = event.getProject().getName();
        String targetName = event.getTarget().getName();
        _projects.get(projectName).getTarget(targetName).addStartTime(new Date()).increment();

        _defaultTable.logTargetStartet(this, _projects.get(projectName).getTarget(targetName));
        for (int i = 0; i < _tables.length; i++) {
            _tables[i].logTargetStartet(_printers.get(_tables[i].getClass().getName()), _projects.get(projectName)
                    .getTarget(targetName));
        }
    }

    @Override
    public void targetFinished(BuildEvent event) {
        String projectName = event.getProject().getName();
        String targetName = event.getTarget().getName();
        Target target = _projects.get(projectName).getTarget(targetName).addFinishTime(new Date());

        _defaultTable.logTargetFinished(this, target);
        for (int i = 0; i < _tables.length; i++) {
            _tables[i].logTargetFinished(_printers.get(_tables[i].getClass().getName()), target);
        }
    }

    @Override
    public void subBuildStarted(BuildEvent event) {
        _projects.get(event.getProject().getName()).setSubProject(true);
    }

    @Override
    public void subBuildFinished(BuildEvent event) {
    }

}
