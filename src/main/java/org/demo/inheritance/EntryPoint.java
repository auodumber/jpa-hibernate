package org.demo.inheritance;

import jakarta.persistence.EntityManager;
import org.demo.util.PersistenceUtil;

/**
 * @author Auodumbar
 */
public class EntryPoint {
    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        entityManager.getTransaction().begin();

        Manager manager = new Manager();
        manager.setId(1);
        manager.setName("Bob");
        manager.setDepartment("Engineering");

        Developer developer = new Developer();
        developer.setId(2);
        developer.setName("Alice");
        developer.setModule("ERP-HRD");

        entityManager.persist(manager);
        entityManager.persist(developer);


        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
