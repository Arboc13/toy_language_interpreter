package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.type.IType;

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
}
