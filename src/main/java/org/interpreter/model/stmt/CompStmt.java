package org.interpreter.model.stmt;

import org.interpreter.model.prgstate.PrgState;

public record CompStmt(IStmt first, IStmt second) implements IStmt {

    // S1
    @Override
    public PrgState execute(PrgState state) {
        state.getExeStack().push(second);
        state.getExeStack().push(first);
        return state;
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
