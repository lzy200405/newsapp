package com.example.newsapp.model;

public class News {
    private String title, author, url, imageUrl, date;
    public News(String title, String author, String url, String imageUrl, String date) {
        this.title = title; this.author = author;
        this.url = url; this.imageUrl = imageUrl; this.date = date;
    }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getUrl() { return url; }
    public String getImageUrl() { return imageUrl; }
    public String getDate() { return date; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        News news = (News) obj;
        return url != null && url.equals(news.url);
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }

}
