package org.interpreter.model.adt;

public interface IList<T> {
    void add(T v);                        // PrintStmt appends
    T get(int index) throws ADTException; // out of bounds -> ADTException
    int size();
}
