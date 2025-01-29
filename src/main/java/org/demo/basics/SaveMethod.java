package org.demo.basics;

import org.demo.entities.Student;
import org.demo.util.PersistenceUtil;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * @author Auodumbar
 * <p>
 * The effect of saving an already persisted instance is the same as with persist.
 * The difference comes when we try to save a detached instance:
 */
public class SaveMethod {
    public static void main(String[] args) {
        Session session = PersistenceUtil.getHibernateSession();
        // Begin the transaction
        session.getTransaction().begin();

        //save()   is deprecated since version 6.0
        Student student = new Student("Rushikesh", LocalDate.now(), "Nepal");
        session.save(student); // Executes INSERT immediately

        // Evict the entity from the session (make it detached)
        session.evict(student);
        System.out.println("Student evicted from session");

        // Modify the detached entity
        student.setCountry("Canada");
        System.out.println("Updated (detached) Student: " + student);

        // Now, if we use save() on a detached object, it will cause a problem.
        // Hibernate may treat this as a new entity and try to insert it.
        session.save(student);  // Hibernate may try to insert the student again

        session.getTransaction().commit();
        session.close();

        Session session2 = PersistenceUtil.getHibernateSession();

        session2.getTransaction().begin();

        Student student2 = session2.get(Student.class, 3202);

        student2.setCountry("USA");
        session2.save(student2); //Insert Query fires Update the row

        session2.getTransaction().commit();
        session2.close();


    }
}
