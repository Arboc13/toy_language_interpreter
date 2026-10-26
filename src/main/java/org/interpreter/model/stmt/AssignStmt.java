package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.value.IValue;
import org.interpreter.model.prgstate.PrgState;

import java.util.Objects;

public record AssignStmt(String id, IExp exp) implements IStmt {

    // S2
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(id))
            throw new StatementException("variable " + id + " was not declared");
        IValue val = exp.eval(symTable);
        if (!val.getType().equals(symTable.lookup(id).getType()))
            throw new StatementException("declared type of variable " + id
                    + " and type of the assigned expression do not match");
        symTable.update(id, val);
        return state;
    }
    @Override public String toString() { return id + " = " + exp; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AssignStmt that = (AssignStmt) o;
        return Objects.equals(exp, that.exp) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exp);
    }
}


// TODO: VarDeclStmt(String name, IType type) – S3
// PrintStmt(IExp exp) – S4
// IfStmt(IExp cond, IStmt thenS, IStmt elseS) – S5
// NopStmt()

