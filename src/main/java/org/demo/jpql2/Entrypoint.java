package org.demo.jpql2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.demo.jpql2.dto.CountedEnrollmentForStudent;
import org.demo.jpql2.dto.EnrolledStudent;
import org.demo.util.PersistenceUtil;

/**
 * @author Auodumbar
 */
public class Entrypoint {

    public static void main(String[] args) {
        EntityManager entityManager = PersistenceUtil.getEntityManager();
        entityManager.getTransaction().begin();

/*
        String jpql = getVariousJoins(2);
        TypedQuery<Object[]> q = entityManager.createQuery(jpql, Object[].class);
        q.getResultList().forEach(o -> System.out.println(o[0] + " " + o[1]));
*/

/*
        String jpql = getEnrolledStudentProjection();
        TypedQuery<EnrolledStudent> query = entityManager.createQuery(jpql, EnrolledStudent.class);
        query.getResultList().forEach(data -> System.out.println( data.getStudent2() +" "+data.getEnrollment()));
*/
/*

        String jpql = getCountedEnrollments();
        TypedQuery<CountedEnrollmentForStudent> query = entityManager.createQuery(jpql, CountedEnrollmentForStudent.class);
        query.getResultList().forEach(data -> System.out.println(data.s()+" " +data.count()));

*/


        entityManager.getTransaction().commit();
        entityManager.close();
    }

    //all are same except LEFT and RIGHT join
    private static String getVariousJoins(int type) {
        return switch (type) {
            case 1 -> "SELECT s, e FROM Student2 s INNER JOIN s.enrollments e";
            case 2 -> "SELECT s, e FROM Student2 s JOIN s.enrollments e";
            case 3 -> "SELECT s, e FROM Student2 s, Enrollment e WHERE s.id = e.student.id";
            case 4 -> "SELECT s, e FROM Student2 s, Enrollment e WHERE s = e.student";
            case 5 -> "SELECT s, e FROM Student2 s LEFT JOIN s.enrollments e";
            default -> "SELECT s, e FROM Student2 s RIGHT JOIN s.enrollments e";
        };
    }

    private static String getEnrolledStudentProjection(){
        return "SELECT NEW org.demo.jpql2.dto.EnrolledStudent(s, e) FROM Student2 s RIGHT JOIN s.enrollments e";
    }

    private static String getCountedEnrollments(){
        String jpql1 = """
                    SELECT s FROM Student2 s WHERE
                        (SELECT COUNT(e) FROM Enrollment e WHERE e.student2.id = s.id) > 1
                    """;

        String jpql2 = """
                    SELECT NEW org.demo.jpql2.dto.CountedEnrollmentForStudent(s, (SELECT count(e) FROM Enrollment e WHERE e.student2 = s))
                    FROM Student2 s
                    """;

        return jpql2;
    }


}
