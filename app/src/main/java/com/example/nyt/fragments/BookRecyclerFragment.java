package com.example.nyt.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nyt.BookAdapter;
import com.example.nyt.R;
import com.example.nyt.asynctask.AsyncTaskGetDelegate;
import com.example.nyt.asynctask.AsyncTaskInsertDelegate;
import com.example.nyt.asynctask.GetBooksAsyncTask;
import com.example.nyt.asynctask.InsertBooksAsyncTask;
import com.example.nyt.asynctask.InsertBuyLinkAsyncTask;
import com.example.nyt.database.AppDatabase;
import com.example.nyt.model.BestsellerList;
import com.example.nyt.model.BestsellerListResponse;
import com.example.nyt.model.Book;
import com.example.nyt.model.BuyLink;
import com.google.gson.Gson;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookRecyclerFragment extends Fragment implements AsyncTaskInsertDelegate, AsyncTaskGetDelegate {
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private Book[] books;
    private AppDatabase db;

    public BookRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        bookAdapter = new BookAdapter();

        // Start Volley stuff
        // 1. Create request queue.
        // 2. Create response listener and error listener
        // 3. Create Request object using url, response listener, error listener.
        // 4. Put Request object into request queue.

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        // To get the url, you need to read the documentation of the API.
        // Figure out what data you want, and they should tell you how to get the correct URL.
        // Also in this case, I've put my API key in an XML file (secrets.xml).
        // This helps me easily reuse the API key by calling getString.
        // It also makes it easy for me to hide the API key if I want to share my code
        // (I can just ignore the secrets.xml file instead of having to go into all the files and delete it.)
        // You will need to go into the file and put in your own API key first.
        String url = "https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=" + getString(R.string.nyt_api_key);

        // Response.Listener<String>. We define what to do after a response is received.
        // Response.Listener means that it is referring to the inner class Listener, which has been
        // defined inside another class Response.
        // The <String> part corresponds to the type of the response.
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                BestsellerListResponse bestsellerListResponse = gson.fromJson(response, BestsellerListResponse.class);
                BestsellerList bestsellerList = bestsellerListResponse.getResults();

                List<Book> bestsellers = bestsellerList.getBooks();
                db = AppDatabase.getInstance(getContext());
                // Our db insert method call used to be here last week. But that's on the main thread.
                // So we replace it with an AsyncTask that runs the query on a worker thread.
//                db.bookDao().insertAll(bestsellers);
                InsertBooksAsyncTask insertBooksAsyncTask = new InsertBooksAsyncTask();
                insertBooksAsyncTask.setDatabase(db);
                // I'm giving a reference to THIS fragment to be the delegate. I'm telling the
                // AsyncTask, that the fragment that I'm in right now, will be your delegate.
                insertBooksAsyncTask.setDelegate(BookRecyclerFragment.this);
                books = bestsellers.toArray(new Book[bestsellers.size()]);
                insertBooksAsyncTask.execute(books);


                // Notice that I'm not setting the data of the adapter here. This is because
                // after the AsyncTask is executed, we can't guarantee that the results exist yet.
                // We need to rely on the delegate pattern, for the AsyncTask to pass us the results
                // first, before we can use the result in the UI.

                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);

        // This line simply adds the request to the queue. It's not necessarily going to be executed
        // immediately (there could possibly be other requests in the queue).
        // Also, because requests to the internet take time, we cannot guarantee that the response
        // will be received right away.
        // We are NOT GUARANTEED to have a response after this line.
        // That is the purpose of the onResponse method in the response listener.
        requestQueue.add(stringRequest);

        return view;
    }

    // This method is required to be implemented, because we use the interface. This method is called
    // from the AsyncTask once the task has completed. It gives the result back to this class so
    // that we can use the result to update our UI.
    @Override
    public void handleTaskResult(String result) {

        // In this case, this method is called after the Insert task is done. After inserting, I
        // want to update my UI to reflect the new list of Books. To do this, I have made another
        // AsyncTask that runs to get the Books from the database.
        // So after my Insert AsyncTask finishes, this method will be called and it will start
        // a second AsyncTask to get the books.
        GetBooksAsyncTask getBooksAsyncTask = new GetBooksAsyncTask();
        getBooksAsyncTask.setDatabase(AppDatabase.getInstance(getContext()));
        getBooksAsyncTask.setDelegate(this);
        getBooksAsyncTask.execute();

        for (int j = 0; j < books.length; j++) {
            BuyLink[] buyLinks = books[j].getBuyLinks();
            long isbn = books[j].getIsbn();
            InsertBuyLinkAsyncTask insertBuyLinkAsyncTask = new InsertBuyLinkAsyncTask();
            insertBuyLinkAsyncTask.setDb(db);
            for (int i = 0; i < buyLinks.length; i++) {
                buyLinks[i].setIsbn(isbn);

                //Convert http links to Https
                String url = buyLinks[i].getUrl();
                if (url.contains("https:")) {
                    continue;
                } else {
                    buyLinks[i].setUrl(url.substring(0, 4) + "s" + url.substring(4));
                }
            }
            insertBuyLinkAsyncTask.execute(buyLinks);
        }
    }

    // This is a method for the second interface. This Fragment can be a delegate for two different
    // AsyncTasks that return different types of results, because I implement the two interfaces.
    @Override
    public void handleTaskGetResult(List<Book> books) {
        // When the get AsyncTask is done, it'll return to me the list of books. Then I just update
        // the UI.
        bookAdapter.setData(books);
        recyclerView.setAdapter(bookAdapter);
    }
}
