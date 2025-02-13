package org.demo.jpql2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.demo.jpql2.dto.StudentGroupByName;
import org.demo.jpql2.entities.Student2;
import org.demo.util.PersistenceUtil;

/**
 * @author Auodumbar
 */
public class NamedQueryExample {
    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        entityManager.getTransaction().begin();

        TypedQuery<Student2> getAllStudents = entityManager.createNamedQuery("getAllStudents", Student2.class);
        getAllStudents.getResultList().forEach(System.out::println);

        System.out.println("Second Query!!");

        TypedQuery<StudentGroupByName> studentGroupByName = entityManager.createNamedQuery("studentGroupByName", StudentGroupByName.class);

        studentGroupByName.getResultList().forEach(student -> System.out.println(student));

        entityManager.getTransaction().commit();
        entityManager.close();



    }
}
