package org.demo.jpql2.entities;

import jakarta.persistence.*;

import java.util.List;

/**
 * @author Auodumbar
 * <p>
 * Basically we can use ant jpql query in Named Queries
 */

@Entity
@Table(name = "student", schema = "university")
@NamedQueries({
        @NamedQuery(name = "getAllStudents", query = "SELECT s FROM Student2 s"),
        @NamedQuery(name = "studentGroupByName",
                query = """
                        SELECT NEW org.demo.jpql2.dto.StudentGroupByName(s.name,count(s))
                        FROM Student2 s
                        GROUP BY s.name
                        HAVING s.name LIKE 'A%'
                        """
        )
})
public class Student2 {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "student2")
    private List<Enrollment> enrollments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    @Override
    public String toString() {
        return "Student{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
    
}
