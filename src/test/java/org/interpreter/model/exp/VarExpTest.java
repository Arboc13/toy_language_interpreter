package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarExpTest {

    @Test
    public void evalTest() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
        symTable.put("a", new IntValue(3));

        assertEquals(new IntValue(3), new VarExp("a").eval(symTable));
    }

    @Test
    public void evalTestError(){
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();

        assertThrows(ExpressionException.class, () -> new VarExp("a").eval(symTable));
    }

    @Test
    public void inArithExpTest() throws MyException {
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
        symTable.put("a", new IntValue(3));

        ArithExp e = new ArithExp(ArithOp.ADD, new VarExp("a"), new ValueExp(new IntValue(4)));

        assertEquals(new IntValue(7), e.eval(symTable));
    }

    @Test
    public void toStringTest(){
        assertEquals("a", new VarExp("a").toString());
    }
}
