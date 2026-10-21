package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;

public interface IStack<T> {
    void push(T v);
    T pop() throws ADTException;
    boolean isEmpty();
}
