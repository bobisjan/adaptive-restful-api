
package cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts;


public abstract class AIssue {

    public abstract String getDescription();
    public abstract void setDescription(String Description);

    public abstract AProject getProject();
    public abstract void setProject(AProject project);

}
