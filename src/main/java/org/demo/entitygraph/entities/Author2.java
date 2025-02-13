package org.demo.entitygraph.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

/**
 * @author Auodumbar
 */

@Entity
@Table(name = "authors" ,schema = "graph")
public class Author2 {

    @Id
    private int id;

    private String name;

    @ManyToMany
    private Set<Book2> booksList;

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

    public Set<Book2> getBooksList() {
        return booksList;
    }

    public void setBooksList(Set<Book2> booksList) {
        this.booksList = booksList;
    }

    @Override
    public String toString() {
        return "Author{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}