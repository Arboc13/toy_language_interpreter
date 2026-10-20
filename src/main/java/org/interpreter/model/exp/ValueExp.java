package org.interpreter.model.exp;

import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyException;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;

public record ValueExp(IValue exp) implements IExp{

    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        return exp;
    }

    @Override
    public String toString(){ return exp.toString(); }


}

// TODO: ValueExp (returns its value), VarExp (looks the variable up),
// LogicOp + LogicExp (and, or – both operands must be booleans)