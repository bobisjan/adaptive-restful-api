
package cz.cvut.fel.adaptiverestfulapi.meta.data.base;


public class Issue extends Item {

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

    public Project getProject() {
        return this.project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
