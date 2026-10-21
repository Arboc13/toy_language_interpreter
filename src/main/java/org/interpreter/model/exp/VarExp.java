package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.value.IValue;

public record VarExp(String id) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!symTable.isDefined(id))
            throw new ExpressionException("variable " + id + " is not defined");
        return symTable.lookup(id);
    }
    @Override public String toString() { return id; }
}