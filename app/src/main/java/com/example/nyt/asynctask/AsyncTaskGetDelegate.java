package com.example.nyt.asynctask;

import com.example.nyt.model.Book;

import java.util.List;

public interface AsyncTaskGetDelegate {
    void handleTaskGetResult(List<Book> books);
}
