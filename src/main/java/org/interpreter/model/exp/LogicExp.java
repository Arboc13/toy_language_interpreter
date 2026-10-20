package org.interpreter.model.exp;

import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.MyException;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;

public record LogicExp(LogicOp op, IExp left, IExp right) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if(!(left.eval(symTable) instanceof BoolValue(boolean n1))){
            throw new MyException("First operant is not a boolean");
        }
        if(!(right.eval(symTable) instanceof BoolValue(boolean n2))){
            throw new MyException("Second operant is not a boolean");
        }
        return  switch (op){
            case OR -> new BoolValue(n1 || n2);
            case AND -> new BoolValue(n1 && n2);
        };
    }

    @Override
    public String toString(){
        return "(" + left + " " + op + " " + right + ")";
    }
}
