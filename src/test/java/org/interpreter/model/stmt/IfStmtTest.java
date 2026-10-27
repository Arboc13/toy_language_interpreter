package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IfStmtTest {

    private final IStmt thenS = new AssignStmt("v", new ValueExp(new IntValue(2)));
    private final IStmt elseS = new AssignStmt("v", new ValueExp(new IntValue(3)));
    private PrgState state;

    @BeforeEach
    public void setUp() throws ADTException {
        state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), new NopStmt());
        state.getExeStack().pop();
    }

    @Test
    public void trueConditionPushesThenTest() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(true)), thenS, elseS).execute(state);

        assertEquals(thenS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void falseConditionPushesElseTest() throws MyException {
        new IfStmt(new ValueExp(new BoolValue(false)), thenS, elseS).execute(state);

        assertEquals(elseS, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void conditionFromVariableTest() throws MyException {
        state.getSymTable().put("a", new BoolValue(true));

        new IfStmt(new VarExp("a"), thenS, elseS).execute(state);

        assertEquals(thenS, state.getExeStack().pop());
    }

    @Test
    public void nonBooleanConditionTestError(){
        IfStmt stmt = new IfStmt(new ValueExp(new IntValue(1)), thenS, elseS);

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void toStringTest(){
        assertEquals("(if a then v = 2 else v = 3)", new IfStmt(new VarExp("a"), thenS, elseS).toString());
    }
}
