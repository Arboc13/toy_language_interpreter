package org.interpreter.model.adt;

public class MyDictionary<K,V> implements IDictionary{
    @Override
    public boolean isDefined(Object key) {
        return false;
    }

    @Override
    public Object lookup(Object key) throws ADTException {
        return null;
    }

    @Override
    public void put(Object key, Object value) {

    }

    @Override
    public void update(Object key, Object value) throws ADTException {

    }
}
