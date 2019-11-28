package com.example.nyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nyt.BuyLinkAdapter;
import com.example.nyt.R;

public class BuyLinkDetailActivity extends AppCompatActivity {
    private WebView buyLinkWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_link_detail);

        buyLinkWebView = findViewById(R.id.buyLinkWebView);
        Intent intent = getIntent();
        String url = intent.getStringExtra(BuyLinkAdapter.INTENT_URL_EXTRA);
        buyLinkWebView.loadUrl(url);
    }
}
