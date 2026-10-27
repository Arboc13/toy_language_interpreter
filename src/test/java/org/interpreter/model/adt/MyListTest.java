package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Test
    public void addTest() throws ADTException {
        MyList<IntValue> list = new MyList<IntValue>();

        list.add(new IntValue(10));
        list.add(new IntValue(15));
        list.add(new IntValue(20));

        assertEquals(3, list.size());

        assertEquals(new IntValue(10), list.get(0));
        assertEquals(new IntValue(15), list.get(1));
        assertEquals(new IntValue(20), list.get(2));
    }

    @Test
    public void getTestError(){
        MyList<IntValue> list = new MyList<IntValue>();

        assertThrows(ADTException.class, () -> list.get(0));

        list.add(new IntValue(10));

        assertThrows(ADTException.class, () -> list.get(1));
        assertThrows(ADTException.class, () -> list.get(-1));
    }

    @Test
    public void sizeTest(){
        MyList<IntValue> list = new MyList<IntValue>();

        assertEquals(0, list.size());
        list.add(new IntValue(1));
        assertEquals(1, list.size());
    }

    @Test
    public void toStringTest(){
        MyList<IntValue> list = new MyList<IntValue>();

        list.add(new IntValue(1));
        list.add(new IntValue(2));

        assertEquals("[1, 2]", list.toString());
    }
}
