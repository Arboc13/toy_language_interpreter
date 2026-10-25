package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArithExpTest {

    private final IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();

    private ArithExp exp(ArithOp op, int left, int right){
        return new ArithExp(op, new ValueExp(new IntValue(left)), new ValueExp(new IntValue(right)));
    }

    @Test
    public void addTest(){
        assertEquals(new IntValue(7), exp(ArithOp.ADD, 3, 4).eval(symTable));
    }

    @Test
    public void subTest(){
        assertEquals(new IntValue(-1), exp(ArithOp.SUB, 3, 4).eval(symTable));
    }

    @Test
    public void mulTest(){
        assertEquals(new IntValue(12), exp(ArithOp.MUL, 3, 4).eval(symTable));
    }

    @Test
    public void divTest(){
        assertEquals(new IntValue(3), exp(ArithOp.DIV, 7, 2).eval(symTable));
    }

    @Test
    public void divByZeroTestError(){
        assertThrows(ExpressionException.class, () -> exp(ArithOp.DIV, 7, 0).eval(symTable));
    }

    @Test
    public void nestedTest(){
        ArithExp nested = new ArithExp(ArithOp.MUL, exp(ArithOp.ADD, 1, 2), new ValueExp(new IntValue(4)));

        assertEquals(new IntValue(12), nested.eval(symTable));
    }

    @Test
    public void firstOperandTestError(){
        ArithExp e = new ArithExp(ArithOp.ADD, new ValueExp(new BoolValue(true)), new ValueExp(new IntValue(1)));

        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    public void secondOperandTestError(){
        ArithExp e = new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(1)), new ValueExp(new BoolValue(true)));

        assertThrows(ExpressionException.class, () -> e.eval(symTable));
    }

    @Test
    public void toStringTest(){
        assertEquals("(3 + 4)", exp(ArithOp.ADD, 3, 4).toString());
        assertEquals("(3 - 4)", exp(ArithOp.SUB, 3, 4).toString());
        assertEquals("(3 * 4)", exp(ArithOp.MUL, 3, 4).toString());
        assertEquals("(3 / 4)", exp(ArithOp.DIV, 3, 4).toString());
    }
}
