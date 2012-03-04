package antw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;
import org.apache.tools.ant.util.FileUtils;

import antw.model.Project;
import antw.model.Projects;
import antw.model.Target;
import antw.util.ExceptionUtil;

public class AntwContext {

    private Projects _projects = new Projects();

    public AntwContext() {
        new File(getReportDir()).mkdirs();
    }

    public Projects getProjects() {
        return _projects;
    }

    public Project getProject(BuildEvent event) {
        String projectName = getProjectName(event);
        return _projects.get(projectName);
    }

    public Target getTarget(BuildEvent event) {
        Project project = getProject(event);
        String targetName = getTargetName(event);
        return project.getTarget(targetName);
    }

    public String getReportDir() {
        return ".antw/reports";
    }

    public PrintStream openReportingStream(String fileName) {
        try {
            return new PrintStream(new FileOutputStream(new File(getReportDir(), fileName), false));
        } catch (FileNotFoundException e) {
            throw ExceptionUtil.convertToRuntimeException("can not open stream", e);
        }
    }

    private String getProjectName(BuildEvent event) {
        return event.getProject().getName();
    }

    private String getTargetName(BuildEvent event) {
        return event.getTarget().getName();
    }

    public void finishReports(BuildEvent event) {
        File antwFolder = new File(event.getProject().getBaseDir(), "build/antw");
        antwFolder.mkdirs();
        File reportDir = new File(getReportDir());
        File[] files = reportDir.listFiles();
        FileUtils fileUtils = FileUtils.getFileUtils();
        for (File file : files) {
            try {
                fileUtils.rename(file, new File(antwFolder, file.getName()));
            } catch (IOException e) {
                throw ExceptionUtil.convertToRuntimeException("can not rename file", e);
            }
        }
        FileUtils.delete(reportDir);

    }
}
