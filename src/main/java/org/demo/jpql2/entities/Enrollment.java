package org.demo.jpql2.entities;

import jakarta.persistence.*;

/**
 * @author Auodumbar
 */
@Entity
@Table(name = "enrollment",schema = "university")
public class Enrollment {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student2 student2;

    @ManyToOne
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student2 getStudent2() {
        return student2;
    }

    public void setStudent2(Student2 student2) {
        this.student2 = student2;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", student2=" + student2 +
                ", course=" + course +
                '}';
    }
}
