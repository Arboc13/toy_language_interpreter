package org.interpreter.model.exp;

import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IHeap;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyHeap;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueExpTest {

    private final IHeap heap = new MyHeap();

    @Test
    public void evalTest() throws MyException {
        MyDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();

        assertEquals(new IntValue(5), new ValueExp(new IntValue(5)).eval(symTable, heap));
        assertEquals(new BoolValue(true), new ValueExp(new BoolValue(true)).eval(symTable, heap));
    }

    @Test
    public void toStringTest(){
        assertEquals("5", new ValueExp(new IntValue(5)).toString());
        assertEquals("true", new ValueExp(new BoolValue(true)).toString());
    }
}
