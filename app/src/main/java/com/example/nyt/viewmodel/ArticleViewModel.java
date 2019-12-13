package com.example.nyt.viewmodel;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.nyt.FakeDatabase;
import com.example.nyt.R;
import com.example.nyt.model.Article;
import com.example.nyt.model.TopStoriesResponse;
import com.google.gson.Gson;

import java.util.List;

public class ArticleViewModel extends ViewModel {
    private Context context;
    private MutableLiveData<List<Article>> articles;


    public LiveData<List<Article>> getArticles() {
        if (articles == null) {
            articles = new MutableLiveData<List<Article>>();
            loadArticles();
        }
        return articles;
    }

    private void loadArticles() {
        final RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "https://api.nytimes.com/svc/mostpopular/v2/viewed/1.json?api-key=" + context.getString(R.string.nyt_api_key);

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //this is because NYT when it doesn't have any media returns "" instead of []
                //this makes gson unhappy because it expects am array and gets a string instead
                response = response.replace("media\":\"\"", "media\":[]");
                Gson gson = new Gson();
                TopStoriesResponse topStoriesResponse = gson.fromJson(response, TopStoriesResponse.class);
                FakeDatabase.saveArticlesToFakeDatabase(topStoriesResponse.results);
                articles.setValue(topStoriesResponse.results);
                requestQueue.stop();
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "The request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                requestQueue.stop();
            }
        };

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener,
                errorListener);
        requestQueue.add(stringRequest);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
