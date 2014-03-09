
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.Locale;


@Entity
public class Issue {

    @Id
    @GeneratedValue
    private long id;

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

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
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
        this.project = project;
    }

}
