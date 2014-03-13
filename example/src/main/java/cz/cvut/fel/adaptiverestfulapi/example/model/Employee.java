
package cz.cvut.fel.adaptiverestfulapi.example.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @OneToMany(mappedBy = "manager")
    private List<Project> managedProjects;

    public Employee() {
        this("First Name", "Last Name");
    }

    public Employee(String firstName, String lastName) {
        this(firstName, lastName, new LinkedList<Project>());
    }

    public Employee(String firstName, String lastName, List<Project> managedProjects) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setManagedProjects(managedProjects);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.getFirstName() + " " + this.getLastName();
    }

    public List<Project> getManagedProjects() {
        return this.managedProjects;
    }

    public void setManagedProjects(List<Project> managedProjects) {
        this.managedProjects = managedProjects;
    }

    public void addManagedProject(Project managedProject) {
        if (this.managedProjects.contains(managedProject)) {
            return;
        }
        this.managedProjects.add(managedProject);
        managedProject.setManager(this);
    }

    public void removeManagedProject(Project managedProject) {
        if (!this.managedProjects.contains(managedProject)) {
            return;
        }
        this.managedProjects.remove(managedProject);
        managedProject.setManager(null);
    }

    public Boolean isManager() {
        return this.managedProjects.size() > 0;
    }

}


