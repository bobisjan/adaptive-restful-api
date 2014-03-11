
package cz.cvut.fel.adaptiverestfulapi.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PersistenceContext {

    private static PersistenceContext singleton;

    private EntityManagerFactory factory;
    private EntityManager manager;

    public static PersistenceContext getInstance() {
        if (singleton == null) {
            singleton = new PersistenceContext();
        }
        return singleton;
    }

    public EntityManager getManager() {
        return this.manager;
    }

    public void init() throws Exception {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        DriverManager.getConnection("jdbc:derby:memory:example-jpa;create=true").close();

        this.factory = javax.persistence.Persistence.createEntityManagerFactory("example-manager-factory");
        this.manager = this.factory.createEntityManager();
    }

    public void destroy() throws Exception {
        if (this.manager != null) {
            this.manager.close();
        }

        if (this.factory != null) {
            this.factory.close();
        }

        DriverManager.getConnection("jdbc:derby:memory:example-jpa;shutdown=true").close();
    }

}
