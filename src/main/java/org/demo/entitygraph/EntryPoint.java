package org.demo.entitygraph;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Subgraph;
import org.demo.criteriaquery.entities.Author;
import org.demo.entitygraph.entities.Author2;
import org.demo.util.PersistenceUtil;

import java.util.stream.Collectors;

/**
 * @author Auodumbar
 * <p>
 * In simple terms, Entity Graph in JPA is a way to control how related entities are fetched from the database.
 * It helps avoid unnecessary joins and improves performance by specifying only the necessary associations to be fetched.
 * <p>
 * Basically it is eager fetching in programatic way
 */
public class EntryPoint {
    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();
        try {

            entityManager.getTransaction().begin();

            //printBooksByAuthors(entityManager);
            subGraphPrintBookShopByAuthors(entityManager);

            entityManager.getTransaction().commit();


        } finally {
            entityManager.close();
        }
    }

    public static void printBooksByAuthors(EntityManager entityManager) {
        // Author -> Book
        EntityGraph<?> graph = entityManager.createEntityGraph(Author2.class);
        graph.addAttributeNodes("booksList");

        entityManager.createQuery("SELECT a FROM Author2 a", Author.class)
                .setHint("jakarta.persistence.loadgraph", graph) //If we comment this line for graph then for each book a separate query will be fired please see entity graph dump in resource
                .getResultList().forEach(author -> System.out.println(
                        author.getBooksList()
                ));
    }

    public static void subGraphPrintBookShopByAuthors(EntityManager entityManager) {
        // Author -> Book -> BookShop
        EntityGraph<?> entityGraph = entityManager.createEntityGraph(Author2.class);
        Subgraph<?> bookSubGraph = entityGraph.addSubgraph("booksList");
        bookSubGraph.addAttributeNodes("bookShopList");

        entityManager.createQuery("SELECT a FROM Author2 a", Author2.class)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList()
                .forEach(a ->
                        System.out.println(
                                a.getBooksList().stream().map(book -> book.getBookShopList()).collect(Collectors.toList())
                        )
                );


    }
}
