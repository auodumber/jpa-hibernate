package org.demo.onetone;

import jakarta.persistence.EntityManager;
import org.demo.util.PersistenceUtil;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */
public class EntryPoint {

    public static void main(String[] args) {

        EntityManager entityManager = PersistenceUtil.getEntityManager();

        entityManager.getTransaction().begin();

        Person person = new Person();
        person.setName("Auodumber");

        Passport passport = new Passport();
        passport.setExpDate(LocalDate.of(2027, 12, 4));

        person.setPassport(passport);

        entityManager.persist(person);
        //we have to persist passport also, if we want to persist passport whenever we are persisting person we have to use cascade.type.persist
        entityManager.persist(passport); //If we comment this, without adding cascading then : TransientObjectException: persistent instance references an unsaved transient instance of 'org.demo.onetone.Passport' (save the transient instance before flushing)

        entityManager.getTransaction().commit();
    }
}
