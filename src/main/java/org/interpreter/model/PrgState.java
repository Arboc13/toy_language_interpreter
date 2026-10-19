package org.interpreter.model;

import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IList;
import org.interpreter.model.adt.IStack;
import org.interpreter.model.adt.MyStack;
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
    // getters, setters, toString ...
}
PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
        new MyList<>(), program);
