package com.example.healthapp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MockNewsService implements NewsService {
    @Override
    public List<NewsArticle> fetchLatest(int max) {
        List<NewsArticle> list = new ArrayList<>();
        list.add(new NewsArticle("1", "Vaccine rollout speeds up", "Short summary", "https://news.example/1", LocalDateTime.now().minusDays(1), "HealthNews"));
        list.add(new NewsArticle("2", "Hospital networks merge", "Short summary", "https://news.example/2", LocalDateTime.now().minusDays(2), "IndustryDaily"));
        return list.subList(0, Math.min(max, list.size()));
    }
}
