
package cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts;

import java.util.LinkedList;
import java.util.List;


public class Project extends AProject {

    private String name;
    private List<AIssue> issues;

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

    public List<AIssue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<AIssue> issues) {
        this.issues = issues;
    }

}
