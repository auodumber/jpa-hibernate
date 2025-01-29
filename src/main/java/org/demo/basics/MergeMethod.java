package org.demo.basics;

import jakarta.persistence.EntityManager;
import org.demo.entities.Student;
import org.demo.util.PersistenceUtil;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */
public class MergeMethod {

    public static void main(String[] args) {

        try (EntityManager entityManager = PersistenceUtil.getEntityManager()) {
            // Begin the transaction
            entityManager.getTransaction().begin();

            Student student = new Student("Rahul", LocalDate.now(), "Karnataka");
            entityManager.persist(student);
            entityManager.flush(); //Force insert query
            entityManager.detach(student);//detach object from context;

            student.setCountry("Maharashtra");

            //finds an entity instance by id taken from the passed object (either an existing entity instance from the persistence context is retrieved, or a new instance loaded from the database)
            //copies fields from the passed object to this instance
            //returns a newly updated instance
            //Since the entity doesn’t exist in the database, Hibernate will insert the entity as a new row in the database.
            Student managedStudent = entityManager.merge(student);

            //Note that the merge method returns an object. It’s the managedStudent object we loaded into the persistence context and updated,
            //not the student object that we passed as an argument. They’re two different objects, and we usually need to discard the student object.

            Student student2 = entityManager.find(Student.class,2702);
            entityManager.detach(student2);

            student2.setCountry("India");

            entityManager.merge(student2);


            entityManager.getTransaction().commit();
        }
    }

}
