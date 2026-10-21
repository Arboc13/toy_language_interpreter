package org.interpreter.model.adt;

import java.util.*;

public class MyList<T> implements IList{

    private final List<T> list = new ArrayList<T>();
    @Override
    public void add(Object v) {
        list.add((T)v);
    }

    @Override
    public Object get(int index) throws ADTException {
        if (index >= list.size()){
            throw new ADTException("Invalid index.");
        }
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }
}
