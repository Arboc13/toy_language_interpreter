package org.interpreter.model.adt;

import org.interpreter.model.type.IntType;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Test
    public void addTest(){
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

    }
}