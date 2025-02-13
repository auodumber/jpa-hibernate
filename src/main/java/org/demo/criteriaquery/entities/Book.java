package org.demo.criteriaquery.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.List;

/**
 * @author Auodumbar
 */

@Entity
@Table(name = "books")
public class Book {

    @Id
    private int id;
    private String title;

    @ManyToMany(mappedBy = "booksList")  //Attribute Name is Used While Joining to Show this booksList is used
    private List<Author> authorsList;

    @ManyToMany(mappedBy = "booksList")
    private List<BookShop> bookShopList;

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

    public List<Author> getAuthorsList() {
        return authorsList;
    }

    public void setAuthorsList(List<Author> authors) {
        this.authorsList = authors;
    }

    public List<BookShop> getBookShopList() {
        return bookShopList;
    }

    public void setBookShopList(List<BookShop> bookShopList) {
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