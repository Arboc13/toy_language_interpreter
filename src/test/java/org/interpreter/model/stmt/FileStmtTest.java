package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.StringValue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class FileStmtTest {
    @TempDir
    Path dir;

    private PrgState state;
    private ValueExp file;          // "<dir>/numbers.in" containing 15, 50
    private StringValue fileName;

    @BeforeEach
    void setUp() throws Exception {
        Path f = dir.resolve("numbers.in");
        Files.writeString(f, "15\n50\n");
        fileName = new StringValue(f.toString());
        file = new ValueExp(fileName);
        state = PrgState.of(new NopStmt());
        state.getExeStack().pop();
        new VarDeclStmt("x", new IntType()).execute(state);
    }

    @AfterEach
    void closeReaders() throws Exception {
        // Windows locks open files -> @TempDir cleanup would fail
        for (BufferedReader r : state.getFileTable().toMap().values()) r.close();
    }

    // ---------- openRFile (S8) ----------

    @Test
    void openAddsTheFileToTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertTrue(state.getFileTable().isDefined(fileName));
    }

    @Test
    void openNonStringThrows() {
        assertThrows(StatementException.class,
                () -> new OpenRFileStmt(new ValueExp(new IntValue(1))).execute(state));
    }

    @Test
    void openTwiceThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new OpenRFileStmt(file).execute(state));
    }

    @Test
    void openMissingFileThrows() {
        var missing = new ValueExp(new StringValue(dir.resolve("nope.in").toString()));
        assertThrows(StatementException.class, () -> new OpenRFileStmt(missing).execute(state));
    }

    // ---------- readFile (S9) ----------

    @Test
    void readGivesNumbersInOrderThenZero() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(15), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(50), state.getSymTable().lookup("x"));
        new ReadFileStmt(file, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readEmptyFileGivesZero() throws Exception {
        Path empty = dir.resolve("empty.in");
        Files.writeString(empty, "");
        var e = new ValueExp(new StringValue(empty.toString()));
        new OpenRFileStmt(e).execute(state);
        new ReadFileStmt(e, "x").execute(state);
        assertEquals(new IntValue(0), state.getSymTable().lookup("x"));
    }

    @Test
    void readUndeclaredVariableThrows() throws MyException {
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "y").execute(state));
    }

    @Test
    void readIntoNonIntVariableThrows() throws MyException {
        new VarDeclStmt("b", new BoolType()).execute(state);
        new OpenRFileStmt(file).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "b").execute(state));
    }

    @Test
    void readFileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new ReadFileStmt(file, "x").execute(state));
    }

    @Test
    void readNonStringFileExpThrows() {
        assertThrows(StatementException.class,
                () -> new ReadFileStmt(new ValueExp(new IntValue(1)), "x").execute(state));
    }

    @Test
    void readNonIntegerLineThrows() throws Exception {
        Path bad = dir.resolve("bad.in");
        Files.writeString(bad, "abc\n");
        var b = new ValueExp(new StringValue(bad.toString()));
        new OpenRFileStmt(b).execute(state);
        assertThrows(StatementException.class, () -> new ReadFileStmt(b, "x").execute(state));
    }

    // ---------- closeRFile (S10) ----------

    @Test
    void closeRemovesTheFileFromTheFileTable() throws MyException {
        new OpenRFileStmt(file).execute(state);
        new CloseRFileStmt(file).execute(state);
        assertFalse(state.getFileTable().isDefined(fileName));
    }

    @Test
    void closeFileNotOpenThrows() {
        assertThrows(StatementException.class, () -> new CloseRFileStmt(file).execute(state));
    }

    @Test
    void closeNonStringThrows() {
        assertThrows(StatementException.class,
                () -> new CloseRFileStmt(new ValueExp(new IntValue(1))).execute(state));
    }

    // ---------- toString (infix form, section 2.5 / log file) ----------

    @Test
    void toStringIsInfix() {
        var v = new VarExp("varf");
        assertEquals("openRFile(varf)", new OpenRFileStmt(v).toString());
        assertEquals("readFile(varf, varc)", new ReadFileStmt(v, "varc").toString());
        assertEquals("closeRFile(varf)", new CloseRFileStmt(v).toString());
    }
}
