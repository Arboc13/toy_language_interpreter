package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;

import java.util.Objects;

public record PrintStmt(IExp exp) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getOut().add(exp.eval(state.getSymTable()));
        return state;
    }

    @Override public String toString() { return "print(" + exp + ")"; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PrintStmt printStmt = (PrintStmt) o;
        return Objects.equals(exp, printStmt.exp);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(exp);
    }
}
