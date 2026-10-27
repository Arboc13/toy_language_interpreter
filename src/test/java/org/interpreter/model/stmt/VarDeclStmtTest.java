package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VarDeclStmtTest {

    private PrgState state;

    @BeforeEach
    public void setUp() throws ADTException {
        state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void declaresIntWithDefaultZeroTest() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);

        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    public void declaresBoolWithDefaultFalseTest() throws MyException {
        new VarDeclStmt("a", new BoolType()).execute(state);

        assertEquals(new BoolValue(false), state.getSymTable().lookup("a"));
    }

    @Test
    public void redeclaredVariableTestError() throws MyException {
        new VarDeclStmt("v", new IntType()).execute(state);

        assertThrows(StatementException.class, () -> new VarDeclStmt("v", new BoolType()).execute(state));
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    public void toStringTest(){
        assertEquals("int v", new VarDeclStmt("v", new IntType()).toString());
        assertEquals("bool a", new VarDeclStmt("a", new BoolType()).toString());
    }
}
