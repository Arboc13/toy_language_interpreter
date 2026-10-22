package org.interpreter.model.prgstate;

import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IList;
import org.interpreter.model.adt.IStack;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IValue;

public class PrgState {
    private IStack<IStmt> exeStack;
    private IDictionary<String, IValue> symTable;
    private IList<IValue> out;
    private final IStmt originalProgram;

    public PrgState(IStack<IStmt> exeStack, IDictionary<String, IValue> symTable,
                    IList<IValue> out, IStmt program) {
        this.exeStack = exeStack;
        this.symTable = symTable;
        this.out = out;
        this.originalProgram = program;
        exeStack.push(program);
    }

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
    public String toString(){ return "";}

    public void setExeStack(IStack<IStmt> exeStack) {
        this.exeStack = exeStack;
    }

    public void setOut(IList<IValue> out) {
        this.out = out;
    }

    public void setSymTable(IDictionary<String, IValue> symTable) {
        this.symTable = symTable;
    }


    // getters, setters, toString ...
}
