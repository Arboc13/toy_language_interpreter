package org.interpreter.model.prgstate;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IList;
import org.interpreter.model.adt.IStack;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyHeap;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IValue;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrgStateTest {

    private final IStmt program = new AssignStmt("a", new ValueExp(new IntValue(1)));

    @Test
    public void constructorPushesProgramTest() throws ADTException {
        IStack<IStmt> stack = new MyStack<IStmt>();
        PrgState state = new PrgState(stack, new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap(), program);

        assertSame(stack, state.getExeStack());
        assertFalse(stack.isEmpty());
        assertEquals(program, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void gettersTest(){
        IDictionary<String, IValue> symTable = new MyDictionary<String, IValue>();
        IList<IValue> out = new MyList<IValue>();
        PrgState state = new PrgState(new MyStack<>(), symTable, out, new MyDictionary<>(), new MyHeap(), program);

        assertSame(symTable, state.getSymTable());
        assertSame(out, state.getOut());
    }

    @Test
    public void settersTest(){
        PrgState state = PrgState.of(program);

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

    @Test
    public void heapGetterTest(){
        MyHeap heap = new MyHeap();
        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), heap, program);

        assertSame(heap, state.getHeap());
    }

    @Test
    public void ofCreatesEmptyHeapTest(){
        assertTrue(PrgState.of(program).getHeap().getContent().isEmpty());
    }

    @Test
    public void logContainsSortedHeapAfterFileTableTest() throws ADTException {
        PrgState state = PrgState.of(program);
        for (int i = 1; i <= 11; i++)      // 11 entries: HashMap order would put 10, 11 out of place as strings
            state.getHeap().allocate(new IntValue(i * 10));
        state.getHeap().update(2, new RefValue(1, new IntType()));

        String log = state.toLogString();
        String heapPart = log.substring(log.indexOf("Heap:\n"));

        assertTrue(log.indexOf("FileTable:") < log.indexOf("Heap:"));
        assertTrue(heapPart.startsWith("Heap:\n1 --> 10\n2 --> (1, int)\n3 --> 30\n"));
        assertTrue(heapPart.endsWith("10 --> 100\n11 --> 110\n"));
    }
}
