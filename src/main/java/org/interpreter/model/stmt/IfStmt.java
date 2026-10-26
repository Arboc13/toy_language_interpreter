package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.BoolValue;

import java.util.Objects;

public record IfStmt(IExp cond, IStmt thenS, IStmt elseS) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if (!(cond.eval(state.getSymTable()) instanceof BoolValue(boolean c)))
            throw new StatementException("condition " + cond + " is not a boolean");
        state.getExeStack().push(c ? thenS : elseS);
        return state;
    }

    @Override
    public String toString() {
        return "(if " + cond + " then " + thenS + " else " + elseS + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IfStmt ifStmt = (IfStmt) o;
        return Objects.equals(cond, ifStmt.cond) && Objects.equals(thenS, ifStmt.thenS) && Objects.equals(elseS, ifStmt.elseS);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cond, thenS, elseS);
    }
}
