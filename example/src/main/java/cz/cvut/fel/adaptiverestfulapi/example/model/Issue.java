
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.Locale;


@MappedSuperclass
public abstract class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String description;

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

    public abstract Project getProject();

    public abstract void setProject(Project project);

}
