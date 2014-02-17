
package cz.cvut.fel.adaptiverestfulapi.meta.data.abstracts;

import java.util.List;


public abstract class AProject {

    public abstract String getName();
    public abstract void setName(String name);

    public abstract List<AIssue> getIssues();
    public abstract void setIssues(List<AIssue> issues);

}
