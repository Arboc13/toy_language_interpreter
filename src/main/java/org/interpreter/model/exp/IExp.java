package org.interpreter.model.exp;

import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.value.IValue;

public interface IExp {
    IValue eval(IDictionary<String, IValue> symTable) throws MyException;
}
// TODO: ValueExp (returns its value), VarExp (looks the variable up),
// LogicOp + LogicExp (and, or – both operands must be booleans)
