package antw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.apache.tools.ant.BuildEvent;

import antw.model.target.Project;
import antw.model.target.Projects;
import antw.model.target.Target;
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
}
