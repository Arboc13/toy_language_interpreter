package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.RelOp;
import org.interpreter.model.exp.RelationalExp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WhileStmtTest {

    private final IStmt body = new CompStmt(new PrintStmt(new VarExp("v")),
            new AssignStmt("v", new ArithExp(ArithOp.SUB, new VarExp("v"), new ValueExp(new IntValue(1)))));
    private final WhileStmt loop = new WhileStmt(
            new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))), body);
    private PrgState state;

    @BeforeEach
    public void setUp() throws MyException {
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("v", new IntType()).execute(state);
    }

    private void runLoopFrom(int v) throws MyException {
        state.getSymTable().update("v", new IntValue(v));
        state.getExeStack().push(loop);
        while (!state.getExeStack().isEmpty())
            state.getExeStack().pop().execute(state);
    }

    @Test
    public void trueConditionPushesLoopThenBodyTest() throws MyException {
        state.getSymTable().update("v", new IntValue(1));

        loop.execute(state);

        assertEquals(body, state.getExeStack().pop());
        assertEquals(loop, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void runsZeroTimesTest() throws MyException {
        runLoopFrom(0);

        assertTrue(state.getOut().toList().isEmpty());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    public void runsOnceTest() throws MyException {
        runLoopFrom(1);

        assertEquals(List.of(new IntValue(1)), state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    public void runsSeveralTimesTest() throws MyException {
        runLoopFrom(4);

        assertEquals(List.of(new IntValue(4), new IntValue(3), new IntValue(2), new IntValue(1)),
                state.getOut().toList());
        assertEquals(new IntValue(0), state.getSymTable().lookup("v"));
    }

    @Test
    public void nonBooleanConditionTestError(){
        WhileStmt stmt = new WhileStmt(new ValueExp(new IntValue(1)), new NopStmt());

        assertThrows(StatementException.class, () -> stmt.execute(state));
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void falseConditionPushesNothingTest() throws MyException {
        new WhileStmt(new ValueExp(new BoolValue(false)), body).execute(state);

        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void toStringTest(){
        assertEquals("(while ((v > 0)) (print(v); v = (v - 1)))", loop.toString());
    }
}
