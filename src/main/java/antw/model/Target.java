package antw.model;

import java.util.Date;

public class Target extends Name {

    private Date _start;
    private Date _finish;
    private Project _project;
    private int _durationInPercent;

    public Target() {
    }

    public Target addStartTime(Date start) {
        _start = start;
        return this;
    }

    public Date getStart() {
        return _start;
    }

    public Target addFinishTime(Date finish) {
        _finish = finish;
        return this;
    }

    public Date getFinish() {
        return _finish;
    }

    public void setProject(Project project) {
        _project = project;
    }

    public Project getProject() {
        return _project;
    }

    public Target computeRelativeBuildTime(long duration) {
        _durationInPercent = (int) ((_finish.getTime() - _start.getTime()) * 100 / duration);
        return this;
    }

    public Integer getDurationInPercent() {
        return _durationInPercent;
    }

    public Long getDuration() {
        return _finish.getTime() - _start.getTime();
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 == null) {
            return false;
        }
        Target other = (Target) arg0;
        if (!this.getProject().equals(other.getProject())) {
            return false;
        }
        if (!this.getName().equals(other.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return getProject().hashCode() + getName().hashCode();
    }

}
