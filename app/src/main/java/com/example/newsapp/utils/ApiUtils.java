package com.example.newsapp.utils;

import java.io.*;
import java.net.*;

public class ApiUtils {
    public static String getNewsJson() throws IOException {
        String apiKey = "4f08773dcd1c06603c3d7f16b0a5c982";
        String apiUrl = "http://v.juhe.cn/toutiao/index?key=" + URLEncoder.encode(apiKey, "UTF-8");
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();
        return sb.toString();
    }
    public static String getNewsJson(int page) throws IOException {
        String apiKey = "4f08773dcd1c06603c3d7f16b0a5c982";

        String apiUrl = "http://v.juhe.cn/toutiao/index"
                + "?key=" + URLEncoder.encode(apiKey, "UTF-8")
                + "&page=" + page
                + "&page_size=" + 30;

        apiUrl += "&type=" + URLEncoder.encode("top", "UTF-8");


        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) sb.append(line);
        in.close();
        return sb.toString();
    }

}
