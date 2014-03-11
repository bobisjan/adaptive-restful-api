
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Project {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private Date startedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Issue> issues;

    public Project() {
        this.setName("New Project");
        this.setIssues(new LinkedList<Issue>());
    }

    public Project(String name, List<Issue> issues) {
        this.setName(name);
        this.setIssues(issues);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Issue> getIssues() {
        return this.issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        if (this.issues.contains(issue)) {
            return;
        }
        this.issues.add(issue);
        issue.setProject(this);
    }

    public void removeIssue(Issue issue) {
        if (!this.issues.contains(issue)) {
            return;
        }
        this.issues.remove(issue);
        issue.setProject(null);
    }

}
