package org.interpreter.model.stmt;

import org.interpreter.model.prgstate.PrgState;

import java.util.Objects;

public record CompStmt(IStmt first, IStmt second) implements IStmt {


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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompStmt compStmt = (CompStmt) o;
        return Objects.equals(first, compStmt.first) && Objects.equals(second, compStmt.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
