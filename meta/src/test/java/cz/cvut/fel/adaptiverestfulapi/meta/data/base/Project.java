
package cz.cvut.fel.adaptiverestfulapi.meta.data.base;

import java.util.LinkedList;
import java.util.List;


public class Project extends Item {

    private String name;
    private List<Issue> issues;

    public Project() {
        this.setName("New Project");
        this.setIssues(new LinkedList<Issue>());
    }

    public Project(String name, List<Issue> issues) {
        this.setName(name);
        this.setIssues(issues);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Issue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

}
