package com.example.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.newsapp.MainActivity;
import com.example.newsapp.utils.SharedPrefsUtils;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 读取夜间模式设置并应用（必须在 super.onCreate 前）
        boolean isNight = SharedPrefsUtils.isNightMode(this);
        AppCompatDelegate.setDefaultNightMode(
                isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );

        super.onCreate(savedInstanceState);

        // 判断是否已登录
        boolean isLoggedIn = SharedPrefsUtils.isLoggedIn(this);
        if (isLoggedIn) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }

        finish(); // 关闭启动页
    }
}
