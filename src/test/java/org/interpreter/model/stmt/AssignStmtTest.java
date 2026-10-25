package org.interpreter.model.stmt;

import org.interpreter.exceptions.StatementException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AssignStmtTest {

    private PrgState state(IStmt stmt){
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), stmt);
        state.getExeStack().pop();
        return state;
    }

    @Test
    public void executeTest(){
        AssignStmt stmt = new AssignStmt("a", new ValueExp(new IntValue(5)));
        PrgState state = state(stmt);
        state.getSymTable().put("a", new IntValue(0));

        assertSame(state, stmt.execute(state));
        assertEquals(new IntValue(5), state.getSymTable().lookup("a"));
    }

    @Test
    public void executeWithExpressionTest(){
        AssignStmt stmt = new AssignStmt("a", new ArithExp(ArithOp.ADD, new VarExp("a"), new ValueExp(new IntValue(2))));
        PrgState state = state(stmt);
        state.getSymTable().put("a", new IntValue(3));

        stmt.execute(state);

        assertEquals(new IntValue(5), state.getSymTable().lookup("a"));
    }

    @Test
    public void notDeclaredTestError(){
        AssignStmt stmt = new AssignStmt("a", new ValueExp(new IntValue(5)));
        PrgState state = state(stmt);

        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    public void typeMismatchTestError(){
        AssignStmt stmt = new AssignStmt("a", new ValueExp(new BoolValue(true)));
        PrgState state = state(stmt);
        state.getSymTable().put("a", new IntValue(0));

        assertThrows(StatementException.class, () -> stmt.execute(state));
    }

    @Test
    public void toStringTest(){
        assertEquals("a = (1 + 2)", new AssignStmt("a", new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(1)), new ValueExp(new IntValue(2)))).toString());
    }
}
