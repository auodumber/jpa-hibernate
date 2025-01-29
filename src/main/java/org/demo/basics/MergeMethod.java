package org.demo.basics;

import jakarta.persistence.EntityManager;
import org.demo.entities.Student;
import org.demo.util.PersistenceUtil;

import java.time.LocalDate;

/**
 * @author Auodumbar
 *
 *
 *
 *
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
            Student managedStudent = entityManager.merge(student); //If entity is in transient state i.e. if we comment line 21 to 23 then insert will happen

            //Note that the merge method returns an object. It’s the managedStudent object we loaded into the persistence context and updated,
            //not the student object that we passed as an argument. They’re two different objects, and we usually need to discard the student object.

            //What Happens When flush() is Commented Out?
            //persist(student) → The entity is added to the persistence context but not yet inserted into the database.
            //detach(student) → Removes the entity from the persistence context.
            //student.setCountry("Maharashtra") → Modifies the detached entity.
            //merge(student):
            //Since student is now transient (never inserted), merge() tries to load it from the database using its id (which does not exist yet).
            //This results in StaleObjectStateException because Hibernate expects an existing record but finds none


            Student student2 = entityManager.find(Student.class,2702);
            entityManager.detach(student2);

            student2.setCountry("India");
            entityManager.merge(student2);

            entityManager.getTransaction().commit();
        }
    }


    //StaleObjectStateException occurs in optimistic locking scenarios when Hibernate expects an entity version
    //but finds no corresponding row in the database. Since flush() was not called
}
