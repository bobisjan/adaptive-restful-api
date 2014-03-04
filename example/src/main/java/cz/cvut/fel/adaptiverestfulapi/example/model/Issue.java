
package cz.cvut.fel.adaptiverestfulapi.example.model;

import java.util.Locale;


public class Issue {

    private String description;
    private Project project;

    public Issue() {
        this("New Issue", null);
    }

    public Issue(String description, Project project) {
        this.setDescription(description);
        this.setProject(project);
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
