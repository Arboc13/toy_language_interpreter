package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyDictionaryTest {

    @Test
    public void putAndIsDefinedTest(){
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        assertFalse(dict.isDefined("a"));
        dict.put("a", new IntValue(1));
        assertTrue(dict.isDefined("a"));
        assertFalse(dict.isDefined("b"));
    }

    @Test
    public void lookupTest() throws ADTException {
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        dict.put("a", new IntValue(1));
        dict.put("b", new IntValue(2));

        assertEquals(new IntValue(1), dict.lookup("a"));
        assertEquals(new IntValue(2), dict.lookup("b"));
    }

    @Test
    public void lookupTestError(){
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        assertThrows(ADTException.class, () -> dict.lookup("a"));
    }

    @Test
    public void putOverwritesTest() throws ADTException {
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        dict.put("a", new IntValue(1));
        dict.put("a", new IntValue(5));

        assertEquals(new IntValue(5), dict.lookup("a"));
    }

    @Test
    public void updateTest() throws ADTException {
        MyDictionary<String, BoolValue> dict = new MyDictionary<String, BoolValue>();

        dict.put("a", new BoolValue(false));
        dict.update("a", new BoolValue(true));

        assertEquals(new BoolValue(true), dict.lookup("a"));
    }

    @Test
    public void updateTestError(){
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        assertThrows(ADTException.class, () -> dict.update("a", new IntValue(1)));
        assertFalse(dict.isDefined("a"));
    }

    @Test
    public void toStringTest(){
        MyDictionary<String, IntValue> dict = new MyDictionary<String, IntValue>();

        dict.put("a", new IntValue(1));

        assertEquals("{a=1}", dict.toString());
    }
}
