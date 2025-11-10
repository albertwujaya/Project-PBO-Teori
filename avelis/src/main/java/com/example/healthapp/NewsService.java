package com.example.healthapp;

import java.util.List;

public interface NewsService {
    /**
     * Fetch latest health-industry news. Implementations may fetch from the web
     * or return mock data.
     */
    List<NewsArticle> fetchLatest(int max);
}
