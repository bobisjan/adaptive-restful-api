
package cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Project extends AProject {

    private String name;
    private List<AIssue> issues;
    private Date startedAt;

    public Project() {
        this.setName("New Project");
        this.setIssues(new LinkedList<AIssue>());
    }

    public Project(String name, List<AIssue> issues) {
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

    public List<AIssue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<AIssue> issues) {
        this.issues = issues;
    }

}
