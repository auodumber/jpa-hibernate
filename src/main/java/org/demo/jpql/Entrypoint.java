package org.demo.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.demo.util.PersistenceUtil;

import java.util.stream.Stream;

/**
 * @author Auodumbar
 */
public class Entrypoint {

    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();

        entityManager.getTransaction().begin();

/*
        String jpql = "SELECT p FROM Product p";
        String jpql = "SELECT p FROM Product p WHERE p.price > 5";
        String jpql = "SELECT p FROM Product p WHERE p.price > :price AND p.name LIKE :name";

        //SELECT p FROM Product p  ===> Fetch all the attributes of the Product entity from the current context
        //SELECT * FROM Product   ===> Fetch all the columns from the table product

        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);

        query.setParameter("price", 3);
        query.setParameter("name", "%a%");  // LIKE

        List<Product> productList = query.getResultList();

        for (Product p : productList) {
            System.out.println(p);
        }
*/

/*      String jpql = "SELECT AVG(p.price) FROM Product p"; // AVG, SUM, MIN, MAX ....

        TypedQuery<Double> q = entityManager.createQuery(jpql, Double.class);

        Double avg = q.getSingleResult();

        System.out.println(avg);
*/

/*      String jpql = "SELECT COUNT(p) FROM Product p";

        TypedQuery<Long> q = entityManager.createQuery(jpql, Long.class);

        Long count = q.getSingleResult();

        System.out.println(count);
*/

/*
        String jpql = """
                    SELECT p.name, AVG(p.price)
                    FROM Product p GROUP BY p.name
                    """;

        TypedQuery<Object[]> q = entityManager.createQuery(jpql, Object[].class);

        q.getResultList().forEach(objects -> {
            System.out.println(objects[0] + " " + objects[1]);
        });// Beer 5.0
           // Chocolate 7.0
*/

/*
        String jpql = "SELECT p FROM Product p WHERE p.name LIKE 'Candy'";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);

        Product result = query.getSingleResult(); //jakarta.persistence.NoResultException, For getResultList() No Exception

        System.out.println(result);

*/


        String jpql = "SELECT p from Product p";

        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);

        Stream<Product> productStream  =  query.getResultStream();
        productStream.forEach(System.out::println);


        entityManager.getTransaction().commit();

        entityManager.close();
    }
}
