
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.example.model.Issue;
import cz.cvut.fel.adaptiverestfulapi.example.model.Project;

import javax.persistence.EntityManager;


public class ExampleData {

    private ExampleData() {

    }

    public static void generate(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Project project = new Project();
        project.setId(new Long(1));
        project.setName("Octopus");

        Project project2 = new Project();
        project2.setId(new Long(2));
        project2.setName("Octopus 2");

        Issue issue = new Issue();
        issue.setId(new Long(1));
        issue.setDescription("Issue 1 description");

        Issue issue2 = new Issue();
        issue2.setId(new Long(2));
        issue2.setDescription("Issue 2 description");

        Issue issue3 = new Issue();
        issue3.setId(new Long(3));
        issue3.setDescription("Issue 3 description");

        entityManager.merge(project);
        entityManager.merge(project2);

        issue.setProject(project);
        issue2.setProject(project);
        issue3.setProject(project2);

        entityManager.merge(issue);
        entityManager.merge(issue2);
        entityManager.merge(issue3);

        entityManager.merge(project);
        entityManager.merge(project2);

        entityManager.getTransaction().commit();
    }

}
