package org.demo.entitygraph.entities;

/**
 * @author Auodumbar
 */


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "book_shops",schema = "graph")
public class BookShop2 {

    @Id
     private int id;

    private String name;

    @ManyToMany
    private List<Book2> booksList;

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

    public List<Book2> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book2> booksList) {
        this.booksList = booksList;
    }

    @Override
    public String toString() {
        return "BookShop2{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
