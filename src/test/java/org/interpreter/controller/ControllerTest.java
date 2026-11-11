package org.interpreter.controller;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.CompStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IntValue;
import org.interpreter.repository.Repository;
import org.interpreter.view.Examples;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    @TempDir
    Path dir;

    private Controller controller(PrgState state) {
        return new Controller(new Repository(state, dir.resolve("log.txt").toString()), false);
    }

    private PrgState state(IStmt program) {
        PrgState state = PrgState.of(program);
        state.getSymTable().put("a", new IntValue(0));
        state.getSymTable().put("b", new IntValue(0));
        return state;
    }

    private PrgState run(IStmt program) throws MyException {
        PrgState state = PrgState.of(program);
        controller(state).allStep();
        return state;
    }

    @Test
    public void oneStepTest() throws MyException {
        IStmt first = new AssignStmt("a", new ValueExp(new IntValue(1)));
        IStmt second = new AssignStmt("b", new ValueExp(new IntValue(2)));
        PrgState state = state(new CompStmt(first, second));

        controller(state).oneStep(state);

        assertEquals(first, state.getExeStack().pop());
        assertEquals(second, state.getExeStack().pop());
        assertTrue(state.getExeStack().isEmpty());
    }

    @Test
    public void oneStepTestError() throws ADTException {
        PrgState state = state(new AssignStmt("a", new ValueExp(new IntValue(1))));
        state.getExeStack().pop();

        assertThrows(StatementException.class, () -> controller(state).oneStep(state));
    }

    @Test
    public void allStepTest() throws MyException {
        IStmt program = new CompStmt(
                new AssignStmt("a", new ValueExp(new IntValue(3))),
                new AssignStmt("b", new ArithExp(ArithOp.MUL, new VarExp("a"), new ValueExp(new IntValue(4)))));
        PrgState state = state(program);

        controller(state).allStep();

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(new IntValue(3), state.getSymTable().lookup("a"));
        assertEquals(new IntValue(12), state.getSymTable().lookup("b"));
    }

    @Test
    public void example1PrintsTwoTest() throws MyException {
        PrgState state = run(Examples.EX1);

        assertTrue(state.getExeStack().isEmpty());
        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    public void example2PrintsEighteenTest() throws MyException {
        PrgState state = run(Examples.EX2);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(18), state.getOut().get(0));
    }

    @Test
    public void example3PrintsTwoTest() throws MyException {
        PrgState state = run(Examples.EX3);

        assertEquals(1, state.getOut().size());
        assertEquals(new IntValue(2), state.getOut().get(0));
    }

    @Test
    public void fileExampleReadsBothNumbersAndClosesTheFile() throws Exception {
        Path in = dir.resolve("test.in");
        Files.writeString(in, "15\n50\n");

        PrgState s = run(Examples.fileExample(in.toString()));

        assertEquals(2, s.getOut().size());
        assertEquals(new IntValue(15), s.getOut().get(0));
        assertEquals(new IntValue(50), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    public void fileExampleWithEmptyFilePrintsZeroZero() throws Exception {
        Path in = dir.resolve("empty.in");
        Files.writeString(in, "");

        PrgState s = run(Examples.fileExample(in.toString()));

        assertEquals(new IntValue(0), s.getOut().get(0));
        assertEquals(new IntValue(0), s.getOut().get(1));
        assertTrue(s.getFileTable().toMap().isEmpty());
    }

    @Test
    public void example5PrintsFiveTest() throws MyException {
        PrgState s = run(Examples.EX5);

        assertEquals(1, s.getOut().size());
        assertEquals(new IntValue(5), s.getOut().get(0));
    }

    @Test
    public void logFileContainsTheFourSectionsInOrder() throws Exception {
        run(Examples.EX1);

        String log = Files.readString(dir.resolve("log.txt"));
        int a = log.indexOf("ExeStack:"), b = log.indexOf("SymTable:"),
            c = log.indexOf("Out:"), d = log.indexOf("FileTable:");
        assertTrue(0 <= a && a < b && b < c && c < d);
    }

    @Test
    public void logFileStartsWithTheInitialState() throws Exception {
        run(Examples.EX1);

        String log = Files.readString(dir.resolve("log.txt"));
        assertTrue(log.startsWith("ExeStack:\n" + Examples.EX1 + "\n"));
    }
}
