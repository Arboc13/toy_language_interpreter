package org.interpreter.model.exp;

import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelationalExpTest {

    private final IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();


    public RelationalExp exp(RelOp op, int left, int right){
        return new RelationalExp( new ValueExp(new IntValue(left)), new ValueExp(new IntValue(right)), op);
    }

    @Test
    public void eqTest() throws MyException {
        assertEquals(new BoolValue(true), exp(RelOp.EQ, 10,10).eval(symTable));
        assertEquals(new BoolValue(false), exp(RelOp.EQ, 10,11).eval(symTable));
    }

    @Test
    public void gtTest() throws MyException {
        assertEquals(new BoolValue(true), exp(RelOp.GT, 11,10).eval(symTable));
        assertEquals(new BoolValue(false), exp(RelOp.GT, 10,11).eval(symTable));
    }
    @Test
    public void geTest() throws MyException {
        assertEquals(new BoolValue(true), exp(RelOp.GE, 10,10).eval(symTable));
        assertEquals(new BoolValue(true), exp(RelOp.GE, 11,10).eval(symTable));
        assertEquals(new BoolValue(false), exp(RelOp.GE, 10,11).eval(symTable));
    }

    @Test
    public void ltTest() throws MyException {
        assertEquals(new BoolValue(true), exp(RelOp.LT, 10,11).eval(symTable));
        assertEquals(new BoolValue(false), exp(RelOp.LT, 10,10).eval(symTable));
    }

    @Test
    public void leTest() throws MyException {
        assertEquals(new BoolValue(true), exp(RelOp.LE, 10,10).eval(symTable));
        assertEquals(new BoolValue(true), exp(RelOp.LE, 10,11).eval(symTable));
        assertEquals(new BoolValue(false), exp(RelOp.LE, 11,10).eval(symTable));
    }

    @Test
    public void neTest() throws MyException{
        assertEquals(new BoolValue(false), exp(RelOp.NE, 10,10).eval(symTable));
        assertEquals(new BoolValue(true), exp(RelOp.NE, 11,10).eval(symTable));

    }

}