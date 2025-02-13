package org.demo.criteriaquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.demo.criteriaquery.entities.Author;
import org.demo.criteriaquery.entities.Book;
import org.demo.criteriaquery.entities.BookShop;
import org.demo.criteriaquery.entities.Customer;
import org.demo.util.PersistenceUtil;

/**
 * @author Auodumbar
 */
public class CriteriaQueryExample {
    public static void main(String[] args) {

        EntityManager entityManager = PersistenceUtil.getEntityManager();
        entityManager.getTransaction().begin();

        //simpleExample(entityManager);
        //columnSelection(entityManager);
        //joinMultipleTables(entityManager);
        //subQueries(entityManager);

        entityManager.getTransaction().commit();
        entityManager.close();
    }


    public static void simpleExample(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = criteriaBuilder.createQuery(Customer.class);

        Root<Customer> customerRoot = cq.from(Customer.class);

        cq.select(customerRoot); // SELECT c FROM Customer c;

        TypedQuery<Customer> query = entityManager.createQuery(cq);
        query.getResultList().forEach(System.out::println);

    }

    public static void columnSelection(EntityManager entityManager) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> cq = criteriaBuilder.createQuery(Object[].class);

        Root<Customer> customerRoot = cq.from(Customer.class);

        cq.multiselect(customerRoot.get("name"), criteriaBuilder.sum(customerRoot.get("id"))); //SELECT c.name, sum(c.id)
        cq.where(criteriaBuilder.ge(customerRoot.get("id"), 1)); // WHERE c.id >= 1
        cq.groupBy(customerRoot.get("name")); //GROUP BY c.name
        //cq.orderBy(criteriaBuilder.desc(customerRoot.get("id")));//ORDER BY c.id DESC

        TypedQuery<Object[]> query = entityManager.createQuery(cq);
        query.getResultList().forEach(object -> System.out.println(object[0] + " " + object[1]));
    }


    public static void joinMultipleTables(EntityManager entityManager) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> tuple = builder.createTupleQuery();

        // FROM Book b
        Root<Book> bookRoot = tuple.from(Book.class);

        // LEFT JOIN book.authorList a
        Join<Book, Author> joinAuthor = bookRoot.join("authorsList", JoinType.LEFT);

        // INNER JOIN book.bookShopList bs
        Join<Book, BookShop> joinBookShop = bookRoot.join("bookShopList");

        // SELECT b, a, bs FROM Book b LEFT JOIN b.authorList a INNER JOIN b.bookShopList bs
        tuple.multiselect(bookRoot, joinAuthor, joinBookShop).where(builder.like(joinAuthor.get("name"), "A%"));

        TypedQuery<Tuple> query = entityManager.createQuery(tuple);
        query.getResultStream().forEach(t ->
                System.out.println(t.get(0) + " " +
                                   t.get(1, Author.class) + " " +
                                   t.get(2)
                ));


    }

    private static void subQueries(EntityManager entityManager) {

    }


}

