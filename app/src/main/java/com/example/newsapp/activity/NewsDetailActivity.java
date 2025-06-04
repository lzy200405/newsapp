package com.example.newsapp.activity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import com.example.newsapp.R;

public class NewsDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String url = getIntent().getStringExtra("news_url");

        if (url != null && !url.isEmpty()) {
            openCustomTab(url);
        } else {
            // URL 无效就直接关闭当前页面，或者你可以改成显示错误页面
            finish();
        }
    }

    private void openCustomTab(String url) {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        // 可选：设置工具栏颜色
        builder.setToolbarColor(getResources().getColor(R.color.brown_500));
        builder.setShowTitle(true);

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

        // 打开浏览器后关闭当前 Activity，用户按返回会回到上一个页面
        finish();
    }
}
