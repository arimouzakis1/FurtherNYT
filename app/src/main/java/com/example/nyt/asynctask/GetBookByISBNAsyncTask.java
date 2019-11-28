package com.example.nyt.asynctask;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;

public class GetBookByISBNAsyncTask extends AsyncTask<Long, Integer, Book> {
    private AppDatabase db;
    private AsyncTaskGetByIsbnTask delegate;

    @Override
    protected Book doInBackground(Long... longs) {
        return db.bookDao().findBookByIsbn(longs[0]);
    }

    @Override
    protected void onPostExecute(Book book) {
        delegate.handleGetByISBNTask(book);
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public void setDelegate(AsyncTaskGetByIsbnTask delegate) {
        this.delegate = delegate;
    }
}
