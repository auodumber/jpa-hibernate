package org.demo.basics;

import org.demo.entities.Student;
import org.demo.util.PersistenceUtil;
import org.hibernate.Session;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */
public class UpdateMethod {
    public static void main(String[] args) {
        // Obtain a Hibernate Session
        Session session = PersistenceUtil.getHibernateSession();

        // Begin the transaction
        session.getTransaction().begin();

        //As with persist and save, the update method is an “original” Hibernate method. Its semantics differ in several key points:
        //it acts upon a passed object (its return type is void). The update method transitions the passed object from a detached to persistent state.
        //save() and update()  is deprecated since version 6.0
        Student student1 = new Student("Hari", LocalDate.now(), "China");
        session.save(student1); // Executes INSERT immediately
        session.evict(student1); // detached from persistence context

        student1.setCountry("Jorden");
        session.update(student1); //Re-attached to context transient -> persistence

        session.getTransaction().commit();

        session.close();

        Session session2 = PersistenceUtil.getHibernateSession();
        session2.getTransaction().begin();

        Student student2 = new Student("Akshay", LocalDate.now(), "Mumbai");

        //Trying to call update on a transient instance will result in an exception. The following won’t work:
        session2.update(student2); //throws exception

        session2.getTransaction().commit();

        session2.close();
    }
}

