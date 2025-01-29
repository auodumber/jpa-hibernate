package org.demo.basics;

import jakarta.persistence.EntityManager;
import org.demo.entities.Student;
import org.demo.util.PersistenceUtil;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */


public class PersistMethod {
    public static void main(String[] args) {
        Session session = PersistenceUtil.getHibernateSession();

        session.getTransaction().begin();

        // Step 1: Persist a new Student object
        Student student = new Student("Jane", LocalDate.now(), "USA");
        session.persist(student); // Object is now in the persistence context, A call to sequence happens here to get identifier (as we are using postgres)
        // for "identity" generator  it doesn't guarantee that the identifier value will be assigned to the persistent instance immediately

        session.flush(); //persist method does not insert immediately, it fires insert query after transaction commit so we're forcefully inserting
        System.out.println("Persisted Student: " + student);

        // Step 2: detach the object
        session.detach(student); // Removes the object from the persistence context
        System.out.println("Student evicted from session");

        // Step 3: Update the detached object
        student.setCountry("Canada"); // Changes made to the detached object
        System.out.println("Updated (detached) Student: " + student);

        session.persist(student); //PersistentObjectException: detached entity passed to persist: org.demo.entities.Student

        session.getTransaction().commit(); //Throws Exception

        PersistenceUtil.closeEntityManager(session);

        //Example 2
        //Open a new session to verify the database state
        EntityManager entityManager2 = PersistenceUtil.getEntityManager();

        entityManager2.getTransaction().begin();

        Student retrievedStudent = entityManager2.find(Student.class, 1);
        System.out.println("Retrieved Student from DB: " + retrievedStudent);

        entityManager2.detach(retrievedStudent);  //If we Comment this Update query will fire and no exception will be raised

        retrievedStudent.setCountry("Russia"); //PersistentException: detached entity passed to persist: org.demo.entities.Student

        entityManager2.persist(retrievedStudent);

        entityManager2.getTransaction().commit();

        PersistenceUtil.closeEntityManager(entityManager2);


    }
}

