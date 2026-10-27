package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.ExpressionException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrintStmtTest {

    private PrgState state;

    @BeforeEach
    public void setUp() throws ADTException {
        state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void appendsToOutInOrderTest() throws MyException {
        new PrintStmt(new ValueExp(new IntValue(1))).execute(state);
        new PrintStmt(new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)))).execute(state);

        assertEquals(2, state.getOut().size());
        assertEquals(new IntValue(1), state.getOut().get(0));
        assertEquals(new IntValue(3), state.getOut().get(1));
    }

    @Test
    public void printsVariableTest() throws MyException {
        state.getSymTable().put("v", new IntValue(7));

        new PrintStmt(new VarExp("v")).execute(state);

        assertEquals(new IntValue(7), state.getOut().get(0));
    }

    @Test
    public void undefinedVariableTestError(){
        assertThrows(ExpressionException.class, () -> new PrintStmt(new VarExp("v")).execute(state));
        assertEquals(0, state.getOut().size());
    }

    @Test
    public void toStringTest(){
        assertEquals("print(v)", new PrintStmt(new VarExp("v")).toString());
    }
}
