package com.example.nyt.asynctask;

import com.example.nyt.model.Book;
import com.example.nyt.model.BuyLink;

import java.util.List;

public interface AsyncTaskGetByIsbnTask {
    void handleGetByISBNTask(Book book);

    void handleGetBuyLinks(List<BuyLink> buyLinks);
}
