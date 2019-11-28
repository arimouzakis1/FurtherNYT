package com.example.nyt.asynctask;  // CHANGE ME

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;

import java.util.List;

public class GetBooksAsyncTask extends AsyncTask<Void, Integer, List<Book>> {
    // This is just a scaffold example for a task that would handle inserting books into the database.
    // You need to complete the doInBackground and onPostExecute methods.
    // Then you will need to make your own class for a task that handles retrieving Books from the
    // database.
    // Refer to the tutorial slide for more information.

    // We store a variable for an object that implements our interface, so we know that whatever
    // is in here, knows how to handle the result of our task.
    private AsyncTaskGetDelegate delegate;

    // This asynctask will also need to be given a database instance, so it can perform database
    // queries. If your Room database class is named something else, change this.
    private AppDatabase database;

    public void setDelegate(AsyncTaskGetDelegate delegate) {
        this.delegate = delegate;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    @Override
    protected List<Book> doInBackground(Void... voids) {
        return database.bookDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Book> result) {
        // Once doInBackground is completed, this method will run.
        // The "result" comes from doInBackground.

        // This is where we give our result to the delegate and let them handle it.
        // Our delegate should be the original Fragment/Activity, so then it can use the result to
        // update the UI, for example.

        delegate.handleTaskGetResult(result);
    }
}

