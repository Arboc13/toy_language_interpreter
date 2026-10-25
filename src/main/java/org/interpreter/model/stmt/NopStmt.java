package org.interpreter.model.stmt;

import org.interpreter.model.prgstate.PrgState;

public record NopStmt() implements IStmt {

    @Override
    public PrgState execute(PrgState state) {
        return state;
    }

    @Override public String toString() { return "nop"; }
}
