package com.example.nyt;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;

import java.util.Arrays;

public class InsertBookAsyncTask extends AsyncTask<Book, Integer, String> {
    private AppDatabase db;
    private BookDelegate delegate;

    @Override
    protected String doInBackground(Book... books) {
        db.bookDao().insertAll(Arrays.asList(books));
        return "Insert Statement Completed";
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.handleInsertTask(s);
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public void setDelegate(BookDelegate delegate) {
        this.delegate = delegate;
    }
}
