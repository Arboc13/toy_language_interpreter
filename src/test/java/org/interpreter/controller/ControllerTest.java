package org.interpreter.controller;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.CompStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.stmt.IfStmt;
import org.interpreter.model.stmt.PrintStmt;
import org.interpreter.model.stmt.VarDeclStmt;
import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    private PrgState state(IStmt program){
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), program);
        state.getSymTable().put("a", new IntValue(0));
        state.getSymTable().put("b", new IntValue(0));
        return state;
    }

    @Test
    public void oneStepTest() throws MyException {
        IStmt first = new AssignStmt("a", new ValueExp(new IntValue(1)));
        IStmt second = new AssignStmt("b", new ValueExp(new IntValue(2)));
        PrgState state = state(new CompStmt(first, second));
        Controller controller = new Controller(() -> state);

        controller.oneStep(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void oneStepTestError() throws ADTException {
        PrgState state = state(new AssignStmt("a", new ValueExp(new IntValue(1))));
        state.getExeStack().pop();
        Controller controller = new Controller(() -> state);

        assertThrows(StatementException.class, () -> controller.oneStep(state));
    }

    @Test
    public void allStepTest() throws MyException {
        IStmt program = new CompStmt(
                new AssignStmt("a", new ValueExp(new IntValue(3))),
                new AssignStmt("b", new ArithExp(ArithOp.MUL, new VarExp("a"), new ValueExp(new IntValue(4)))));
        PrgState state = state(program);
        Controller controller = new Controller(() -> state);

        controller.allStep(true);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(3), state.getSymTable().lookup("a"));
        assertEquals(new IntValue(12), state.getSymTable().lookup("b"));
    }

    private PrgState run(IStmt program) throws MyException {
        PrgState state = new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), program);
        new Controller(() -> state).allStep(true);
        return state;
    }

    @Test
    public void example1PrintsTwoTest() throws MyException {
        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                        new PrintStmt(new VarExp("v"))));

        PrgState state = run(ex1);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    public void example2PrintsEighteenTest() throws MyException {
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(new AssignStmt("a",
                                new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                                        new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                                new CompStmt(new AssignStmt("b",
                                        new ArithExp(ArithOp.ADD, new VarExp("a"), new ValueExp(new IntValue(1)))),
                                        new PrintStmt(new VarExp("b"))))));

        PrgState state = run(ex2);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    public void example3PrintsTwoTest() throws MyException {
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
                new CompStmt(new VarDeclStmt("v", new IntType()),
                        new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                                new CompStmt(new IfStmt(new VarExp("a"),
                                        new AssignStmt("v", new ValueExp(new IntValue(2))),
                                        new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                        new PrintStmt(new VarExp("v"))))));

        PrgState state = run(ex3);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }
}
