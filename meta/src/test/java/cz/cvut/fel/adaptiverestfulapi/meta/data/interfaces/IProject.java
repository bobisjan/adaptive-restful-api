
package cz.cvut.fel.adaptiverestfulapi.meta.data.interfaces;

import java.util.List;


public interface IProject {

    public String getName();
    public void setName(String name);

    public List<IIssue> getIssues();
    public void setIssues(List<IIssue> issues);

}
