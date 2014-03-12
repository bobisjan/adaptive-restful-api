
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Bug extends Issue {

    @Column
    private String log;

    @ManyToOne
    private Project project;

    public Bug() {
        this(null, "New Bug", null);
    }

    public Bug(String log, String description, Project project) {
        super(description, project);
        this.setLog(log);
    }

    public String getLog() {
        return this.log;
    }

    public void setLog(String log) {
        this.log = log;
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
            old.removeBug(this);
        }
        if (this.project != null) {
            this.project.addBug(this);
        }
    }

    private boolean same(Project project) {
        return (this.project == null) ? project == null : this.project.equals(project);
    }

}
