
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;


@Entity
public class Task extends Issue {

    @Column
    private Date completedAt;

    @ManyToOne
    private Project project;

    public Task() {
        this(null, "New Task", null);
    }

    public Task(Date completedAt, String description, Project project) {
        super(description, project);
        this.setCompletedAt(completedAt);
    }

    public Date getCompletedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
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
            old.removeTask(this);
        }
        if (this.project != null) {
            this.project.addTask(this);
        }
    }

    private boolean same(Project project) {
        return (this.project == null) ? project == null : this.project.equals(project);
    }

}
