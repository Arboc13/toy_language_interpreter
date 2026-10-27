package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NopStmtTest {

    @Test
    public void executeChangesNothingTest() throws ADTException {
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new NopStmt());
        state.getExeStack().pop();
        state.getSymTable().put("v", new IntValue(1));

        assertSame(state, new NopStmt().execute(state));
        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(1), state.getSymTable().lookup("v"));
        assertEquals(0, state.getOut().size());
    }

    @Test
    public void toStringTest(){
        assertEquals("nop", new NopStmt().toString());
    }
}
