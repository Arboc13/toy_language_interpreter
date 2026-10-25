package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStackTest {

    @Test
    public void pushPopTest(){
        MyStack<IntValue> stack = new MyStack<IntValue>();

        stack.push(new IntValue(1));
        stack.push(new IntValue(2));
        stack.push(new IntValue(3));

        assertEquals(new IntValue(3), stack.pop());
        assertEquals(new IntValue(2), stack.pop());
        assertEquals(new IntValue(1), stack.pop());
    }

    @Test
    public void isEmptyTest(){
        MyStack<IntValue> stack = new MyStack<IntValue>();

        assertTrue(stack.isEmpty());
        stack.push(new IntValue(1));
        assertFalse(stack.isEmpty());
        stack.pop();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void popTestError(){
        MyStack<IntValue> stack = new MyStack<IntValue>();

        assertThrows(ADTException.class, stack::pop);
    }

    @Test
    public void toStringTest(){
        MyStack<IntValue> stack = new MyStack<IntValue>();

        stack.push(new IntValue(1));
        stack.push(new IntValue(2));

        assertEquals("[2, 1]", stack.toString());
    }
}
