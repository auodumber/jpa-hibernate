package org.demo.criteriaquery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * @author Auodumbar
 */
@Entity
public class Customer {

    @Id
    private int id;

    private String name;

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

    @Override
    public String toString() {
        return "Customer{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
