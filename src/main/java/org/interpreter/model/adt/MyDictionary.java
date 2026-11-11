package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K, V> implements IDictionary<K, V> {
    private final Map<K, V> dict = new HashMap<>();

    @Override
    public boolean isDefined(K key) {
        return dict.containsKey(key);
    }

    @Override
    public V lookup(K key) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Key doesn't exist");
        return dict.get(key);
    }

    @Override
    public void put(K key, V value) {
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Key doesn't exist");
        dict.replace(key, value);
    }

    @Override
    public void remove(K key) throws ADTException {
        if (!isDefined(key))
            throw new ADTException("Key doesn't exist");
        dict.remove(key);
    }

    @Override
    public Map<K, V> toMap() {
        return Map.copyOf(dict);
    }

    @Override
    public String toString() { return dict.toString(); }
}
