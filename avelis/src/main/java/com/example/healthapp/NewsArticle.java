package com.example.healthapp;

import java.time.LocalDateTime;

public class NewsArticle {
    private String id;
    private String title;
    private String summary;
    private String url;
    private LocalDateTime publishedAt;
    private String source;

    public NewsArticle(String id, String title, String summary, String url, LocalDateTime publishedAt, String source) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.url = url;
        this.publishedAt = publishedAt;
        this.source = source;
    }

    // getters and toString
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getUrl() { return url; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
    public String getSource() { return source; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) - %s", publishedAt, title, source, url);
    }
}
