
package cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts;

import java.util.Locale;


public class Issue extends AIssue {

    private String description;
    private AProject project;

    public Issue() {
        this("New Issue", null);
    }

    public Issue(String description, AProject project) {
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

    public AProject getProject() {
        return this.project;
    }

    public void setProject(AProject project) {
        this.project = project;
    }

}
