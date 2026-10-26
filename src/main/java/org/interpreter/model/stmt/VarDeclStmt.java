package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.IType;

import java.util.Objects;

public record VarDeclStmt(String name, IType type) implements IStmt {

    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (symTable.isDefined(name))
            throw new StatementException("variable " + name + " is already declared");
        symTable.put(name, type.defaultValue());
        return state;
    }

    @Override public String toString() { return type + " " + name; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VarDeclStmt that = (VarDeclStmt) o;
        return Objects.equals(type, that.type) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }
}
