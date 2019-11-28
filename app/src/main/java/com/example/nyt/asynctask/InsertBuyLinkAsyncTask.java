package com.example.nyt.asynctask;

import android.os.AsyncTask;

import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.BuyLink;

import java.util.Arrays;

public class InsertBuyLinkAsyncTask extends AsyncTask<BuyLink, Integer, Void> {
    private AppDatabase db;

    @Override
    protected Void doInBackground(BuyLink... lists) {
        db.bookDao().insertAllBuyLinks(Arrays.asList(lists));
        return null;
    }

    public void setDb(AppDatabase db) {
        this.db = db;
    }
}
