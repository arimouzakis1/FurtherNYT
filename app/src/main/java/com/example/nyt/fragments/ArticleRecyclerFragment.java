package com.example.nyt.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nyt.ArticleAdapter;
import com.example.nyt.R;
import com.example.nyt.activities.MainActivity;
import com.example.nyt.model.Article;
import com.example.nyt.viewmodel.ArticleViewModel;

import java.util.List;


public class ArticleRecyclerFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Article> articleList;

    public ArticleRecyclerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_recycler, container, false);
        recyclerView = view.findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        // REFER to the comments in BookRecyclerAdapter

        final ArticleAdapter articleAdapter = new ArticleAdapter();

        //Using ViewModel to Observe changes in data
        ArticleViewModel viewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
        viewModel.setContext(getContext());
        Observer<List<Article>> articleObserver = new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                articleAdapter.setData(articles);
                recyclerView.setAdapter(articleAdapter);
            }
        };
        viewModel.getArticles().observe(this, articleObserver);

        return view;


    }

    // This is just an example of a way that the Fragment can communicate with the parent Activity.
    // Specifically, this is using a method belonging to the parent.
    @Override
    public void onResume() {
        super.onResume();
        MainActivity parent = (MainActivity) getActivity();
        parent.showCoolMessage("cool (from ArticleRecyclerFragment onResume)");
    }
}
