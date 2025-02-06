package org.demo.secondarytable;

import jakarta.persistence.EntityManager;
import org.demo.util.PersistenceUtil;

/**
 * @author Auodumbar
 */
public class EntryPoint {
    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        entityManager.getTransaction().begin();

        Employee employee = new Employee();
        employee.setEmpName("Auodumbar");
        employee.setAddress("Mumbai");

        entityManager.persist(employee);


        entityManager.getTransaction().commit();
    }
}
