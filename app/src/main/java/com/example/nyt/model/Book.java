package com.example.nyt.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Book {

    // ISBN can be our ID for books (we use isbn13 instead of isbn10)
    @PrimaryKey
    @SerializedName("primary_isbn13")
    private long isbn;

    private int rank;
    private String description;

    private String title;
    private String author;

    @SerializedName("book_image")
    private String bookImage;

    @Ignore
    @SerializedName("buy_links")
    private BuyLink[] buyLinks;

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public BuyLink[] getBuyLinks() {
        return buyLinks;
    }

    // Here I decide to write BuyLink as an inner class rather than in a separate file, because
    // it's very unlikely that BuyLink will be important anywhere else. It will always be part of a
    // book, and only part of a book.

}