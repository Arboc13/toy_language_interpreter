package org.interpreter.model.prgstate;

import org.interpreter.model.adt.*;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.StringValue;

import java.io.BufferedReader;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private IDictionary<StringValue, BufferedReader> fileTable;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IDictionary<StringValue, BufferedReader> fileTable, IStmt program ) {
        this.exeStack = exeStack; // the statements that still have to be executed; the
                                  // top is executed next
        this.symTable = symTable; // the declared variables and their current values
        this.out = out; // the values printed so far, in order
        this.fileTable = fileTable;
        this.originalProgram = program;
        exeStack.push(program);
    }

    public static PrgState of(IStmt program) { return new PrgState(new MyStack<IStmt>(),
            new MyDictionary<String, IValue>(), new MyList<IValue>(), new MyDictionary<StringValue, BufferedReader>(), program); }

    public IStack<IStmt> getExeStack() {
        return this.exeStack;
    }
    public IDictionary<String, IValue> getSymTable() {
        return this.symTable;
    }
    public IList<IValue> getOut() {
        return this.out;
    }

    @Override
    public String toString(){ return "ExeStack: " + exeStack  + "\nSymTable: " + symTable + "\nOut: " + out + "\nFileTable: " + fileTable.toMap().keySet(); }

    public String toLogString() { var sb = new StringBuilder(); sb.append("ExeStack:\n"); exeStack.toList().forEach(s -> sb.append(s).append('\n')); sb.append("SymTable:\n"); symTable.toMap().forEach((k, v) -> sb.append(k).append(" --> ").append(v).append('\n')); sb.append("Out:\n"); out.toList().forEach(v -> sb.append(v).append('\n')); sb.append("FileTable:\n"); fileTable.toMap().keySet().forEach(f -> sb.append(f.val()).append('\n')); return sb.toString(); }


    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }


    public IDictionary<StringValue, BufferedReader> getFileTable() {
        return this.fileTable;
    }
}
