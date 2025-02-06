package org.demo.onetomany;

import jakarta.persistence.EntityManager;
import org.demo.util.PersistenceUtil;

import java.util.List;

/**
 * @author Auodumbar
 */
public class EntryPoint {

    public static void main(String[] args) {

        EntityManager entityManager = PersistenceUtil.getEntityManager();

        try{
            entityManager.getTransaction().begin();

            Post post = new Post();
            post.setContent("First Post");

            Comment comment1 = new Comment();
            comment1.setComment("First Post 1 Comment");
            comment1.setPost(post); //we set this also

            Comment comment2 = new Comment();
            comment2.setComment("First Post 2 Comment");
            comment2.setPost(post);

            post.setComments(List.of(comment1,comment2));

            entityManager.persist(post);

            entityManager.getTransaction().commit();
        }finally {
            entityManager.close();
        }
    }
}
