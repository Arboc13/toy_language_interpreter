package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.RefValue;

public record NewStmt(String varName, IExp exp) implements IStmt { // S11
    @Override
    public PrgState execute(PrgState state) throws MyException {
        var symTable = state.getSymTable();
        if (!symTable.isDefined(varName))
            throw new StatementException("new: " + varName + " is not declared");
        if (!(symTable.lookup(varName) instanceof RefValue ref))
            throw new StatementException("new: " + varName + " is not a reference");
        IValue value = exp.eval(symTable, state.getHeap());
        if (!value.getType().equals(ref.locationType()))
            throw new StatementException("new: " + exp + " has type " + value.getType()
                    + ", expected " + ref.locationType());
        int address = state.getHeap().allocate(value);
        symTable.update(varName, new RefValue(address, ref.locationType()));
        return state;
    }
    @Override public String toString() { return "new(" + varName + ", " + exp + ")"; }
}
