package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;

public interface IStack<T> {
    void push(T v);
    T pop() throws ADTException;
    boolean isEmpty();
}
// TODO: IDictionary<K, V> / MyDictionary<K, V>: isDefined, lookup, put, update
// (wraps a HashMap)
// IList<T> / MyList<T>: add, get, size (wraps an ArrayList)