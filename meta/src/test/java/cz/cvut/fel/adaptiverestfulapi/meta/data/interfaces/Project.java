
package cz.cvut.fel.adaptiverestfulapi.meta.data.interfaces;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Project implements IProject {

    private String name;
    private List<IIssue> issues;
    private Date startedAt;

    public Project() {
        this.setName("New Project");
        this.setIssues(new LinkedList<IIssue>());
    }

    public Project(String name, List<IIssue> issues) {
        this.setName(name);
        this.setIssues(issues);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLowerCasedName(String name) {
        this.setName(name != null ? name.toUpperCase() : null);
    }

    public Date getStartedAt() {
        return this.startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public boolean isStarted() {
        return this.getStartedAt() != null;
    }

    public void setStarted(boolean started) {
        this.setStartedAt(started ? new Date() : null);
    }

    public List<IIssue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<IIssue> issues) {
        this.issues = issues;
    }

}
