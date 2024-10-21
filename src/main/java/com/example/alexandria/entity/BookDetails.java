package com.example.alexandria.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "book_details")
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String summary;
    private Integer pageCount;
    private String year;
    private String isbn;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookDetails() {

    }

    public BookDetails(String summary, Integer pageCount, String year, String isbn) {
        this.summary = summary;
        this.pageCount = pageCount;
        this.year = year;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public String  getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}