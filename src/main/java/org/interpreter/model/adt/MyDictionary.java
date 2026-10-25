package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary<K,V> implements IDictionary{
    private final Map<K,V> dict = new HashMap<K,V>();
    @Override
    public boolean isDefined(Object key) {
        return dict.containsKey(key);
    }

    @Override
    public Object lookup(Object key) throws ADTException {

        if (!this.isDefined(key))
            throw new ADTException("Key doesn't exist");
        return dict.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        dict.put((K) key, (V) value);
    }

    @Override
    public void update(Object key, Object value) throws ADTException {
        if(!this.isDefined(key))
            throw new ADTException("Key doesn't exist");

        dict.replace((K)key, (V)value);
    }

    @Override
    public String toString(){ return dict.toString(); }
}
