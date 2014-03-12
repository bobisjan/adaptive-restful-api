
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Date startedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Bug> bugs;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public Project() {
        this.setName("New Project");
        this.setBugs(new LinkedList<Bug>());
        this.setTasks(new LinkedList<Task>());
    }

    public Project(String name, List<Bug> bugs, List<Task> tasks) {
        this.setName(name);
        this.setBugs(bugs);
        this.setTasks(tasks);
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

    public List<Bug> getBugs() {
        return this.bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    public void addBug(Bug bug) {
        if (this.bugs.contains(bug)) {
            return;
        }
        this.bugs.add(bug);
        bug.setProject(this);
    }

    public void removeBug(Bug bug) {
        if (!this.bugs.contains(bug)) {
            return;
        }
        this.bugs.remove(bug);
        bug.setProject(null);
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        if (this.tasks.contains(task)) {
            return;
        }
        this.tasks.add(task);
        task.setProject(this);
    }

    public void removeTask(Task task) {
        if (!this.tasks.contains(task)) {
            return;
        }
        this.tasks.remove(task);
        task.setProject(null);
    }

}
