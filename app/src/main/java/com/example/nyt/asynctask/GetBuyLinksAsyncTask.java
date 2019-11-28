package com.example.nyt.asynctask;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.BuyLink;

import java.util.List;

public class GetBuyLinksAsyncTask extends AsyncTask<Long, Integer, List<BuyLink>> {
    private AppDatabase db;
    private AsyncTaskGetByIsbnTask delegate;

    @Override
    protected List<BuyLink> doInBackground(Long... longs) {
        return db.bookDao().getAllBuyLinksForCertainISBN(longs[0]);
    }

    @Override
    protected void onPostExecute(List<BuyLink> buyLinks) {
        delegate.handleGetBuyLinks(buyLinks);
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }

    public void setDelegate(AsyncTaskGetByIsbnTask delegate) {
        this.delegate = delegate;
    }
}
