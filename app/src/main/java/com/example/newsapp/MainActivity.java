package com.example.newsapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.example.newsapp.fragment.FavoritesFragment;
import com.example.newsapp.fragment.NewsFragment;
import com.example.newsapp.fragment.SettingsFragment;
import com.example.newsapp.utils.SharedPrefsUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 夜间模式设置
        boolean isNight = SharedPrefsUtils.isNightMode(this);
        int currentMode = AppCompatDelegate.getDefaultNightMode();
        int targetMode = isNight ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;

        if (currentMode != targetMode) {
            AppCompatDelegate.setDefaultNightMode(targetMode);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottomNav);

        // 设置监听器
        nav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.nav_news:
                    fragment = new NewsFragment();
                    break;
                case R.id.nav_favorites:
                    fragment = new FavoritesFragment();
                    break;
                case R.id.nav_settings:
                    fragment = new SettingsFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        });


        // 设置默认 fragment
        nav.setSelectedItemId(R.id.nav_news);



    }
}
