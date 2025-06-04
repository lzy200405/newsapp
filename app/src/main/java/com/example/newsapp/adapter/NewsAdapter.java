package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.newsapp.R;
import com.example.newsapp.activity.NewsDetailActivity;
import com.example.newsapp.model.News;
import com.example.newsapp.utils.SharedPrefsUtils;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<News> list;
    private Context context;
    private boolean isGuest;

    public NewsAdapter(List<News> list, Context context, boolean isGuest) {
        this.list = list;
        this.context = context;
        this.isGuest = isGuest;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        News news = list.get(position);
        if (news == null) return;

        holder.title.setText(news.getTitle());
        holder.summary.setText(news.getAuthor());
        holder.date.setText(news.getDate());

        // 加载缩略图，失败时显示默认图片
        Glide.with(context)
                .load(news.getImageUrl())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.image);

        // 设置收藏图标状态
        boolean isFav = SharedPrefsUtils.isFavorite(context, news);
        holder.btnFavorite.setIconResource(isFav ? R.drawable.ic_favorite : R.drawable.ic_favorite_border);

        // 收藏按钮逻辑切换
        holder.btnFavorite.setOnClickListener(v -> {
            if (SharedPrefsUtils.isFavorite(context, news)) {
                SharedPrefsUtils.removeFavorite(context, news);
                holder.btnFavorite.setIconResource(R.drawable.ic_favorite_border);
                Toast.makeText(context, "已取消收藏", Toast.LENGTH_SHORT).show();
            } else {
                SharedPrefsUtils.addFavorite(context, news);
                holder.btnFavorite.setIconResource(R.drawable.ic_favorite);
                Toast.makeText(context, "已收藏", Toast.LENGTH_SHORT).show();
            }
        });

        // 点击跳转新闻详情页
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(news); // 可回调接口逻辑
            } else {
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra("news_url", news.getUrl());
                intent.putExtra("news_title", news.getTitle());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, summary, date;
        ImageView image;
        MaterialButton btnFavorite;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textTitle);
            summary = itemView.findViewById(R.id.textSummary);
            date = itemView.findViewById(R.id.textDate);
            image = itemView.findViewById(R.id.imageNews);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }

    // 自定义点击接口
    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
