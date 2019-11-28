package com.example.nyt.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nyt.BuyLinkAdapter;
import com.example.nyt.R;
import com.example.nyt.asynctask.AsyncTaskGetByIsbnTask;
import com.example.nyt.asynctask.GetBookByISBNAsyncTask;
import com.example.nyt.asynctask.GetBuyLinksAsyncTask;
import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.Book;
import com.example.nyt.model.BuyLink;

import java.util.List;

public class BookDetailActivity extends AppCompatActivity implements AsyncTaskGetByIsbnTask {
    ConstraintLayout bookConstraintLayout;
    TextView titleTextView;
    TextView authorTextView;
    TextView descriptionTextView;
    TextView rankTextView;
    ImageView coverImageView;
    RecyclerView buyLinkRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        bookConstraintLayout = findViewById(R.id.book);
        titleTextView = bookConstraintLayout.findViewById(R.id.tv_title);
        authorTextView = bookConstraintLayout.findViewById(R.id.tv_author);
        rankTextView = bookConstraintLayout.findViewById(R.id.tv_rank);
        coverImageView = bookConstraintLayout.findViewById(R.id.iv_cover);
        descriptionTextView = findViewById(R.id.tv_description);
        buyLinkRecyclerView = findViewById(R.id.buyLinkRecyclerView);


        Intent intent = getIntent();

        long isbn = intent.getLongExtra("isbn", 0);
        AppDatabase db = AppDatabase.getInstance(this);
//        Book book = db.bookDao().findBookByIsbn(isbn);
        GetBookByISBNAsyncTask getBookByISBNAsyncTask = new GetBookByISBNAsyncTask();
        getBookByISBNAsyncTask.setDb(db);
        getBookByISBNAsyncTask.setDelegate(this);
        getBookByISBNAsyncTask.execute(isbn);

        GetBuyLinksAsyncTask getBuyLinks = new GetBuyLinksAsyncTask();
        getBuyLinks.setDb(db);
        getBuyLinks.setDelegate(this);
        getBuyLinks.execute(isbn);

    }

    @Override
    public void handleGetByISBNTask(Book book) {
        titleTextView.setText(book.getTitle());
        authorTextView.setText(book.getAuthor());
        rankTextView.setText("#" + book.getRank());
        descriptionTextView.setText(book.getDescription());

        String imageUrl = book.getBookImage();
        Glide.with(this).load(imageUrl).into(coverImageView);
    }

    @Override
    public void handleGetBuyLinks(List<BuyLink> buyLinks) {
//        StringBuilder sb = new StringBuilder();
//        for(int i = 0; i < buyLinks.size(); i++) {
//            sb.append(buyLinks.get(i).getName() + "\n" + buyLinks.get(i).getUrl() + "\n\n");
//        }
//        buyLinksTextView.setText(sb);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        buyLinkRecyclerView.setLayoutManager(layoutManager);
        BuyLinkAdapter buyLinkAdapter = new BuyLinkAdapter();
        buyLinkAdapter.setData(buyLinks);
        DividerItemDecoration divider = new DividerItemDecoration(getApplicationContext(), layoutManager.getOrientation());
        buyLinkRecyclerView.addItemDecoration(divider);
        buyLinkRecyclerView.setAdapter(buyLinkAdapter);
    }
}
