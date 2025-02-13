package org.demo.entitygraph.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;
import java.util.Set;

/**
 * @author Auodumbar
 */

@Entity
@Table(name = "books",schema = "graph")
public class Book2 {

    @Id
    private int id;
    private String title;

    @ManyToMany(mappedBy = "booksList")
    private Set<Author2> authorsList;

    @ManyToMany(mappedBy = "booksList")
    private Set<BookShop2> bookShopList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Author2> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(Set<Author2> authorsList) {
        this.authorsList = authorsList;
    }

    public Set<BookShop2> getBookShopList() {
        return bookShopList;
    }

    public void setBookShopList(Set<BookShop2> bookShopList) {
        this.bookShopList = bookShopList;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               '}';
    }
}