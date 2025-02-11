package org.demo.jpql2.dto;

import org.demo.jpql2.entities.Enrollment;
import org.demo.jpql2.entities.Student2;

/**
 * @author Auodumbar
 */
public class EnrolledStudent {

    private Student2 student2;

    private Enrollment enrollment;

    public EnrolledStudent() {
    }

    public EnrolledStudent(Student2 student2, Enrollment enrollment) {
        this.student2 = student2;
        this.enrollment = enrollment;
    }

    public Student2 getStudent2() {
        return student2;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }
}
