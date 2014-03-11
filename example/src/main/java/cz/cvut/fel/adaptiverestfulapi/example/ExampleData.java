
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
        project.setName("Octopus");
        entityManager.persist(project);

        Project project2 = new Project();
        project2.setName("Octopus 2");
        entityManager.persist(project);

        Issue issue = new Issue();
        issue.setDescription("Issue 1 description");
        issue.setProject(project);
        entityManager.persist(issue);

        Issue issue2 = new Issue();
        issue2.setDescription("Issue 2 description");
        issue2.setProject(project);
        entityManager.persist(issue2);

        Issue issue3 = new Issue();
        issue3.setDescription("Issue 3 description");
        issue3.setProject(project2);
        entityManager.persist(issue3);

        entityManager.merge(project);
        entityManager.merge(project2);

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

}
