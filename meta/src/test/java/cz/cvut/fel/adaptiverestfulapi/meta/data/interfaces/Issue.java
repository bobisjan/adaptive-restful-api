
package cz.cvut.fel.adaptiverestfulapi.meta.data.interfaces;


public class Issue implements IIssue {

    private String description;
    private IProject project;

    public Issue() {
        this("New Issue", null);
    }

    public Issue(String description, IProject project) {
        this.setDescription(description);
        this.setProject(project);
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IProject getProject() {
        return this.project;
    }

    public void setProject(IProject project) {
        this.project = project;
    }

}
