package org.demo.jpql2.dto;

import org.demo.jpql2.entities.Student2;

public record CountedEnrollmentForStudent(Student2 s, Long count){

}