package com.example.healthapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryRepository<T> {
    private final Map<String, T> store = new ConcurrentHashMap<>();

    public void save(String id, T item) { store.put(id, item); }
    public Optional<T> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public List<T> findAll() { return new ArrayList<>(store.values()); }
    public void delete(String id) { store.remove(id); }
    public boolean exists(String id) { return store.containsKey(id); }
}
