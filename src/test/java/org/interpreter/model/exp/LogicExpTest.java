package org.interpreter.model.exp;

import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogicExpTest {

    private final IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();

    private LogicExp exp(LogicOp op, boolean left, boolean right){
        return new LogicExp(op, new ValueExp(new BoolValue(left)), new ValueExp(new BoolValue(right)));
    }

    @Test
    public void andTest(){
        assertEquals(new BoolValue(true), exp(LogicOp.AND, true, true).eval(symTable));
        assertEquals(new BoolValue(false), exp(LogicOp.AND, true, false).eval(symTable));
        assertEquals(new BoolValue(false), exp(LogicOp.AND, false, true).eval(symTable));
        assertEquals(new BoolValue(false), exp(LogicOp.AND, false, false).eval(symTable));
    }

    @Test
    public void orTest(){
        assertEquals(new BoolValue(true), exp(LogicOp.OR, true, true).eval(symTable));
        assertEquals(new BoolValue(true), exp(LogicOp.OR, true, false).eval(symTable));
        assertEquals(new BoolValue(true), exp(LogicOp.OR, false, true).eval(symTable));
        assertEquals(new BoolValue(false), exp(LogicOp.OR, false, false).eval(symTable));
    }

    @Test
    public void firstOperandTestError(){
        LogicExp e = new LogicExp(LogicOp.AND, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));

        assertThrows(MyException.class, () -> e.eval(symTable));
    }

    @Test
    public void secondOperandTestError(){
        LogicExp e = new LogicExp(LogicOp.OR, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(1)));

        assertThrows(MyException.class, () -> e.eval(symTable));
    }

    @Test
    public void toStringTest(){
        assertEquals("(true and false)", exp(LogicOp.AND, true, false).toString());
        assertEquals("(true or false)", exp(LogicOp.OR, true, false).toString());
    }
}
