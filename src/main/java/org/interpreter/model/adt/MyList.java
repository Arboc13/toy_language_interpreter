package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<T> implements IList<T> {
    private final List<T> list = new ArrayList<>();

    @Override
    public void add(T v) {
        list.add(v);
    }

    @Override
    public T get(int index) throws ADTException {
        if (index < 0 || index >= list.size())
            throw new ADTException("Invalid index.");
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public List<T> toList() {
        return List.copyOf(list);
    }

    @Override
    public String toString() { return list.toString(); }
}
