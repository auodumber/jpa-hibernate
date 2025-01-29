package org.demo.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

/**
 * @author Auodumbar
 */
public class PersistenceUtil {


    private static final String PERSISTENCE_UNIT_NAME = "my-persistence-unit";
    private static EntityManagerFactory entityManagerFactory;

    static {
        try {
            // Initialize JPA EntityManagerFactory using persistence.xml
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialization failed: " + e.getMessage());
        }
    }

    /**
     * Get JPA EntityManager.
     *
     * @return EntityManager instance
     */
    public static EntityManager getEntityManager() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException("EntityManagerFactory is not initialized.");
        }
        return entityManagerFactory.createEntityManager();
    }

    /**
     * Get Hibernate Session from JPA EntityManager.
     *
     * @return Hibernate Session instance
     */
    public static Session getHibernateSession() {
        EntityManager entityManager = getEntityManager();
        return entityManager.unwrap(Session.class); // Unwrap Hibernate Session from EntityManager
    }

    /**
     * Close EntityManager.
     *
     * @param entityManager the EntityManager to close
     */
    public static void closeEntityManager(EntityManager entityManager) {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    /**
     * Shutdown the EntityManagerFactory.
     */
    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}


