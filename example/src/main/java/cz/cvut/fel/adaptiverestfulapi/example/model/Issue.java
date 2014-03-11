
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.Locale;


@Entity
public class Issue {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String description;

    @ManyToOne
    private Project project;

    public Issue() {
        this("New Issue", null);
    }

    public Issue(String description, Project project) {
        this.setDescription(description);
        this.setProject(project);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocalizedDescription() {
        return Locale.getDefault().toString() + ": " + this.getDescription();
    }

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        if (this.same(project)) {
            return;
        }

        Project old = this.project;
        this.project = project;

        if (old != null) {
            old.removeIssue(this);
        }
        if (this.project != null) {
            this.project.addIssue(this);
        }
    }

    private boolean same(Project project) {
        return (this.project == null) ? project == null : this.project.equals(project);
    }

}
