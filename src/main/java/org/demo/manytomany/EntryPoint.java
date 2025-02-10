package org.demo.manytomany;

import jakarta.persistence.EntityManager;
import org.demo.util.PersistenceUtil;

import java.util.List;

/**
 * @author Auodumbar
 */
public class EntryPoint {

    public static void main(String[] args) {
        EntityManager manager = PersistenceUtil.getEntityManager();

        manager.getTransaction().begin();

        User user1 = new User();
        user1.setName("Bob");

        User user2 = new User();
        user2.setName("Alice");

        Group g1 = new Group();
        g1.setName("Admin");

        Group g2 = new Group();
        g2.setName("Developer");

        user1.setGroups(List.of(g1,g2)); //user1 will be in both group
        user2.setGroups(List.of(g2)); //user2 in only developer group

        manager.persist(user1);
        manager.persist(user2);

        manager.getTransaction().commit();
    }
}
