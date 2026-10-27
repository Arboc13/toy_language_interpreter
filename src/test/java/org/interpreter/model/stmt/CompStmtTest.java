package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompStmtTest {

    private final IStmt first = new AssignStmt("a", new ValueExp(new IntValue(1)));
    private final IStmt second = new AssignStmt("b", new ValueExp(new IntValue(2)));

    @Test
    public void executeTest() throws ADTException {
        CompStmt stmt = new CompStmt(first, second);
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), stmt);
        state.getExeStack().pop();

        assertSame(state, stmt.execute(state));
        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void toStringTest(){
        assertEquals("(a = 1; b = 2)", new CompStmt(first, second).toString());
    }
}
