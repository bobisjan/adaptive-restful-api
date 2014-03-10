
package cz.cvut.fel.adaptiverestfulapi.meta;

import cz.cvut.fel.adaptiverestfulapi.meta.data.simple.Issue;
import cz.cvut.fel.adaptiverestfulapi.meta.data.simple.Project;
import cz.cvut.fel.adaptiverestfulapi.meta.model.*;
import org.testng.annotations.Test;

import java.util.List;


public class ModelBuilderTest {

    @Test
    public void testBuildWithNoEntities() throws Exception {
        ModelBuilder builder = new ModelBuilder("model", Object.class);
        Model model = builder.build(null);

        assert (model == null) : " Model should be null.";
    }

    @Test
    public void testBuildWithValidEntities() throws Exception {
        ModelBuilder builder = new ModelBuilder("model", Object.class);

        Entity issue = new Entity("Issue", Issue.class);
        Entity project = new Entity("Project", Project.class);

        builder.addEntity(issue);
        builder.addEntity(project);

        builder.addProperty(new Attribute("description", Issue.class.getMethod("getDescription"), Issue.class.getMethod("setDescription", String.class), String.class), issue);
        builder.addProperty(new Relationship("project", Issue.class.getMethod("getProject"), Issue.class.getMethod("setProject", Project.class), "Project", RelationshipType.ToOne), issue);

        builder.addProperty(new Attribute("name", Project.class.getMethod("getName"), Project.class.getMethod("setName", String.class), String.class), project);
        builder.addProperty(new Relationship("issues", Project.class.getMethod("getIssues"), Project.class.getMethod("setIssues", List.class), "Issue", RelationshipType.ToMany), project);

        Model model = builder.build(null);

        Entity expected = null;

        expected = model.entityForName("Issue");
        assert (expected != null) : "Model should have entity with name `Issue`.";
        assert (expected.attributeForName("description") != null) : "Entity `Issue` should have attribute `description`.";
        assert (expected.relationshipForName("project") != null) : "Entity `Issue` should have relationship `project`.";

        expected = model.entityForName("Project");
        assert (expected != null) : "Model should have entity with name `Project`.";
        assert (expected.attributeForName("name") != null) : "Entity `Project` should have attribute `name`.";
        assert (expected.relationshipForName("issues") != null) : "Entity `Project` should have relationship `issues`.";
    }

    @Test
    public void testBuildWithInvalidEntities() throws Exception {
        ModelBuilder builder = new ModelBuilder("model", Object.class);

        Entity issue = new Entity(null, Issue.class);
        Entity project = new Entity("Project", null);

        builder.addEntity(issue);
        builder.addEntity(project);

        builder.addProperty(new Attribute("description", Issue.class.getMethod("getDescription"), Issue.class.getMethod("setDescription", String.class), String.class), issue);
        builder.addProperty(new Relationship("project", Issue.class.getMethod("getProject"), Issue.class.getMethod("setProject", Project.class), "abc", RelationshipType.ToOne), issue);

        builder.addProperty(new Attribute(null, Project.class.getMethod("getName"), Project.class.getMethod("setName", String.class), String.class), project);
        builder.addProperty(new Relationship("", Project.class.getMethod("getIssues"), Project.class.getMethod("setIssues", List.class), "xyz", RelationshipType.ToMany), project);

        Model model = builder.build(null);

        assert (builder.hasErrors() == true) : "Model builder should have errors.";
        assert (model == null) : "Model should be null.";
    }

}
