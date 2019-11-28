package com.example.nyt.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Book.class,
                parentColumns = "isbn",
                childColumns = "isbn_foreign_key"
        )
}, indices = @Index(value = {"name", "url"}, unique = true))
public class BuyLink {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String url;

    @ColumnInfo(name = "isbn_foreign_key")
    private long isbn;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIsbn() {
        return isbn;
    }

    public void setIsbn(long isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "BuyLink is: \nname - " + this.name + " \nurl - " + this.url + "\nisbn - " + this.getIsbn();
    }
}
