package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.IntType;
import org.interpreter.model.type.RefType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapStmtTest {

    private PrgState state;

    @BeforeEach
    public void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("v", new RefType(new IntType())).execute(state);
    }

    @Test
    public void newAllocatesAtAddressOneAndUpdatesTheVariableTest() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);

        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    public void newTwiceAllocatesANewAddressTest() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new NewStmt("v", new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new RefValue(2, new IntType()), state.getSymTable().lookup("v"));
        assertEquals(new IntValue(20), state.getHeap().get(1));
        assertEquals(new IntValue(30), state.getHeap().get(2));
    }

    @Test
    public void newWithNestedReferenceTest() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new VarDeclStmt("a", new RefType(new RefType(new IntType()))).execute(state);
        new NewStmt("a", new VarExp("v")).execute(state);

        assertEquals(new RefValue(2, new RefType(new IntType())), state.getSymTable().lookup("a"));
        assertEquals(new RefValue(1, new IntType()), state.getHeap().get(2));
    }

    @Test
    public void newUndeclaredVariableTestError(){
        NewStmt stmt = new NewStmt("x", new ValueExp(new IntValue(20)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
    }

    @Test
    public void newNonReferenceVariableTestError() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        NewStmt stmt = new NewStmt("x", new ValueExp(new IntValue(20)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
    }

    @Test
    public void newWrongTypeTestError() throws MyException {
        NewStmt stmt = new NewStmt("v", new ValueExp(new BoolValue(true)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
        assertEquals(new RefValue(0, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    public void newToStringTest(){
        assertEquals("new(v, 20)", new NewStmt("v", new ValueExp(new IntValue(20))).toString());
    }

    @Test
    public void writeChangesTheHeapLocationTest() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        new WriteHeapStmt("v", new ValueExp(new IntValue(30))).execute(state);

        assertEquals(new IntValue(30), state.getHeap().get(1));
        assertEquals(1, state.getHeap().getContent().size());
        assertEquals(new RefValue(1, new IntType()), state.getSymTable().lookup("v"));
    }

    @Test
    public void writeUndeclaredVariableTestError(){
        WriteHeapStmt stmt = new WriteHeapStmt("x", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    public void writeNonReferenceVariableTestError() throws MyException {
        new VarDeclStmt("x", new IntType()).execute(state);
        WriteHeapStmt stmt = new WriteHeapStmt("x", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    public void writeAddressNotInHeapTestError(){
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new IntValue(30)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getHeap().getContent().isEmpty());
    }

    @Test
    public void writeWrongTypeTestError() throws MyException {
        new NewStmt("v", new ValueExp(new IntValue(20))).execute(state);
        WriteHeapStmt stmt = new WriteHeapStmt("v", new ValueExp(new BoolValue(true)));

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertEquals(new IntValue(20), state.getHeap().get(1));
    }

    @Test
    public void writeToStringTest(){
        assertEquals("wH(v, 30)", new WriteHeapStmt("v", new ValueExp(new IntValue(30))).toString());
    }
}
