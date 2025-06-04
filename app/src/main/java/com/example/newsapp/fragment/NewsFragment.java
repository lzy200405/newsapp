package com.example.newsapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import com.example.newsapp.R;
import com.example.newsapp.adapter.NewsAdapter;
import com.example.newsapp.model.News;
import com.example.newsapp.utils.ApiUtils;
import com.example.newsapp.utils.SharedPrefsUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList = new ArrayList<>();
    private boolean isGuest;
    private boolean isLoading = false;
    private int currentPage = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, container, false);
        recyclerView = v.findViewById(R.id.recyclerNews);
        isGuest = SharedPrefsUtils.isGuest(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(newsList, getContext(), isGuest);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItemCount <= (lastVisibleItem + 2)) {
                    if (!isGuest) {
                        currentPage++;
                        loadNews();
                    } else {
                        Toast.makeText(getContext(), "游客只能查看前5条新闻", Toast.LENGTH_SHORT).show();
                    }
                    isLoading = true;
                }
            }
        });

        loadNews();
        return v;
    }

    private void loadNews() {
        isLoading = true;
        new Thread(() -> {
            try {
                String response = ApiUtils.getNewsJson(currentPage);
                JSONObject json = new JSONObject(response);

                if (!json.has("result") || json.isNull("result")) {
                    // 可以展示提示或占位符页面
                    Log.e("NewsFragment", "result 字段为空或缺失: " + response.toString());
                    requireActivity().runOnUiThread(() ->
                            Toast.makeText(getContext(), "暂无新闻内容，请稍后再试", Toast.LENGTH_SHORT).show()
                    );                    JSONArray data = null;
                }
                JSONArray data = json.getJSONObject("result").getJSONArray("data");

                int limit = isGuest && currentPage == 1 ? 5 : data.length();
                for (int i = 0; i < limit; i++) {
                    JSONObject item = data.getJSONObject(i);
                    newsList.add(new News(
                            item.getString("title"),
                            item.getString("author_name"),
                            item.getString("url"),
                            item.getString("thumbnail_pic_s"),
                            item.getString("date")
                    ));
                }
                requireActivity().runOnUiThread(() -> {
                    adapter.notifyDataSetChanged();
                    isLoading = false;
                });
                Log.d("NewsFragment", "Response: " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
