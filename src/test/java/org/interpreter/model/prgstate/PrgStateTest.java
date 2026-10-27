package org.interpreter.model.prgstate;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IList;
import org.interpreter.model.adt.IStack;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {

    private final IStmt program = new AssignStmt("a", new ValueExp(new IntValue(1)));

    @Test
    public void constructorPushesProgramTest() throws ADTException {
        IStack<IStmt> stack = new MyStack<IStmt>();
        PrgState state = new PrgState(stack, new MyDictionary<String, IValue>(), new MyList<IValue>(), program);

        assertSame(stack, state.getExeStack());
        assertFalse(stack.isEmpty());
        assertEquals(program, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void gettersTest(){
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
        IList<IValue> out = new MyList<IValue>();
        PrgState state = new PrgState(new MyStack<IStmt>(), symTable, out, program);

        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
    }

    @Test
    public void settersTest(){
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), program);

        IStack<IStmt> stack = new MyStack<IStmt>();
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
        IList<IValue> out = new MyList<IValue>();

        state.setExeStack(stack);
        state.setSymTable(symTable);
        state.setOut(out);

        assertSame(stack, state.getExeStack());
        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
    }
}
