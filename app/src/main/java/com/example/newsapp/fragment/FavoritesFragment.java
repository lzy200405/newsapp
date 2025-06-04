package com.example.newsapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.*;
import com.example.newsapp.R;
import com.example.newsapp.activity.NewsDetailActivity;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.model.News;
import com.example.newsapp.utils.SharedPrefsUtils;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> favoriteList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = v.findViewById(R.id.recyclerFavorites);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        favoriteList = SharedPrefsUtils.getFavorites(getContext());
        adapter = new NewsAdapter(favoriteList, getContext(), false);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(news -> {
            Intent intent = new Intent(getContext(), NewsDetailActivity.class);
            intent.putExtra("news_url", news.getUrl());
            intent.putExtra("news_title", news.getTitle());
            startActivity(intent);
        });

        return v;
    }
}
