package antw.logger.model;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import antw.common.model.Names;
import antw.common.util.TimeUtil;

public class Projects extends Names<Project> {

    private Date _start;
    private Date _end;

    public Projects() {
        super(Project.class);
    }

    public Collection<Target> computeRelativeBuildTime(long duration) {
        Set<Target> sortedTargets = new TreeSet<Target>(compareByDuration());

        Collection<Project> projects = values();
        for (Project project : projects) {
            sortedTargets.addAll(project.computeRelativeBuildTime(duration));
        }
        return sortedTargets;
    }

    private Comparator<Target> compareByDuration() {
        return new Comparator<Target>() {
            @Override
            public int compare(Target thisTarget, Target otherTarget) {
                int compareTo = otherTarget.getDurationInPercent().compareTo(thisTarget.getDurationInPercent());
                return compareTo != 0 ? compareTo : -1;
            }
        };
    }

    public void setEnd(Date date) {
        _end = date;
    }

    public Date getEnd() {
        return _end;
    }

    public void setStart(Date date) {
        _start = date;
    }

    public Date getStart() {
        return _start;
    }

    public Long getDuration() {
        return _end.getTime() - _start.getTime();
    }

    public String getDurationAsString() {
        return TimeUtil.formatTimeDuration(getDuration());
    }

}
