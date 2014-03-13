
package cz.cvut.fel.adaptiverestfulapi.example;

import cz.cvut.fel.adaptiverestfulapi.example.model.*;

import javax.persistence.EntityManager;
import java.util.Date;


public class ExampleData {

    private ExampleData() {

    }

    public static void generate(EntityManager entityManager) {
        entityManager.getTransaction().begin();

        Employee employee = new Employee("Dominic", "Strother");
        entityManager.persist(employee);

        Employee employee2 = new Employee("Nataly", "Knowlton");
        entityManager.persist(employee2);

        Employee employee3 = new Employee("Margarita", "Trumper");
        entityManager.persist(employee3);

        Employee employee4 = new Employee("Jadyn", "Colby");
        entityManager.persist(employee4);

        Project project = new Project();
        project.setName("Project A");
        project.setManager(employee2);
        entityManager.persist(project);

        Project project2 = new Project();
        project2.setName("Project B");
        project2.setManager(employee4);
        entityManager.persist(project2);

        Task task = new Task(null, "Task 1", project);
        entityManager.persist(task);

        Task task2 = new Task(new Date(), "Task 2", project);
        entityManager.persist(task2);

        Bug bug = new Bug(null, "Bug #1", project);
        entityManager.persist(bug);

        Task task3 = new Task(null, "Task 3", project2);
        entityManager.persist(task3);

        Bug bug2 = new Bug("java.lang.NullPointerException: line 32", "Bug #2", project2);
        entityManager.persist(bug2);

        Bug bug3 = new Bug("java.lang.IndexOutOfBoundsException: line 123", "Bug #3", project2);
        entityManager.persist(bug3);

        entityManager.merge(project);
        entityManager.merge(project2);

        entityManager.flush();
        entityManager.getTransaction().commit();
    }

}
