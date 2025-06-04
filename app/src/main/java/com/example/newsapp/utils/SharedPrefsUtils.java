package com.example.newsapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.newsapp.model.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefsUtils {
    private static final String PREF_NAME = "news_app_prefs";
    private static final String KEY_GUEST = "is_guest";
    private static final String KEY_NIGHT = "is_night";
    private static final String KEY_FAVORITES = "favorites";
    private static final String KEY_USERNAME = "saved_username";
    private static final String KEY_PASSWORD = "saved_password";

    public static void registerUser(Context context, String username, String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        prefs.edit().putString("user_" + username, password).apply();
    }

    public static boolean userExists(Context context, String username) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.contains("user_" + username);
    }

    public static boolean checkLogin(Context context, String username, String password) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedPass = prefs.getString("user_" + username, null);
        return savedPass != null && savedPass.equals(password);
    }



    public static boolean isGuest(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_GUEST, true);
    }

    public static void setGuest(Context context, boolean isGuest) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_GUEST, isGuest).apply();
    }

    public static void saveLoginStatus(Context context, boolean isLoggedIn, boolean isGuest) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putBoolean("is_logged_in", isLoggedIn);
        editor.putBoolean(KEY_GUEST, isGuest);
        editor.apply();
    }

    public static boolean isLoggedIn(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean("is_logged_in", false);
    }


    public static boolean isNightMode(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).getBoolean(KEY_NIGHT, false);
    }

    public static void setNightMode(Context context, boolean isNight) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_NIGHT, isNight).apply();
    }

    public static void addFavorite(Context context, News news) {
        List<News> favorites = getFavorites(context);
        if (!favorites.contains(news)) {
            favorites.add(news);
            saveFavorites(context, favorites);
        }
    }

    public static List<News> getFavorites(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_FAVORITES, "");
        if (json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<News>>() {}.getType();
        return new Gson().fromJson(json, type);
    }

    private static void saveFavorites(Context context, List<News> list) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(KEY_FAVORITES, new Gson().toJson(list));
        editor.apply();
    }

    public static boolean isFavorite(Context context, News news) {
        return getFavorites(context).contains(news);
    }

    public static void removeFavorite(Context context, News news) {
        List<News> favorites = getFavorites(context);
        favorites.remove(news);
        saveFavorites(context, favorites);
    }


}
