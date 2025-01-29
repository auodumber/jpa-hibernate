package org.demo.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

/**
 * @author Auodumbar
 */
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private int id;
    private String name;
    private LocalDate date;
    private String country;

    public Student(){}

    public Student(String name, LocalDate date, String country) {
        this.name = name;
        this.date = date;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", country='" + country + '\'' +
                '}';
    }
}
