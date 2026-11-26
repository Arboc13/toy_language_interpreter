package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IHeap;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyHeap;
import org.interpreter.model.type.IntType;
import org.interpreter.model.type.RefType;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReadHeapExpTest {

    private IDictionary<String, IValue> symTable;
    private IHeap heap;

    @BeforeEach
    public void setUp(){
        symTable = new MyDictionary<String, IValue>();
        heap = new MyHeap();
        heap.allocate(new IntValue(20));
        heap.allocate(new RefValue(1, new IntType()));
        symTable.put("v", new RefValue(1, new IntType()));
        symTable.put("a", new RefValue(2, new RefType(new IntType())));
    }

    @Test
    public void evalTest() throws MyException {
        assertEquals(new IntValue(20), new ReadHeapExp(new VarExp("v")).eval(symTable, heap));
    }

    @Test
    public void evalNestedTest() throws MyException {
        assertEquals(new RefValue(1, new IntType()), new ReadHeapExp(new VarExp("a")).eval(symTable, heap));
        assertEquals(new IntValue(20), new ReadHeapExp(new ReadHeapExp(new VarExp("a"))).eval(symTable, heap));
    }

    @Test
    public void inArithExpTest() throws MyException {
        ArithExp e = new ArithExp(ArithOp.ADD, new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5)));

        assertEquals(new IntValue(25), e.eval(symTable, heap));
    }

    @Test
    public void nonReferenceTestError(){
        ReadHeapExp e = new ReadHeapExp(new ValueExp(new IntValue(1)));

        assertThrows(ExpressionException.class, () -> e.eval(symTable, heap));
    }

    @Test
    public void addressNotInHeapTestError() throws MyException {
        symTable.update("v", new RefValue(7, new IntType()));
        ReadHeapExp e = new ReadHeapExp(new VarExp("v"));

        assertThrows(ExpressionException.class, () -> e.eval(symTable, heap));
    }

    @Test
    public void nullAddressTestError(){
        ReadHeapExp e = new ReadHeapExp(new ValueExp(new RefValue(0, new IntType())));

        assertThrows(ExpressionException.class, () -> e.eval(symTable, heap));
    }

    @Test
    public void toStringTest(){
        assertEquals("rH(rH(a))", new ReadHeapExp(new ReadHeapExp(new VarExp("a"))).toString());
    }
}
