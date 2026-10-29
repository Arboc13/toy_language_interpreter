package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;

public record RelationalExp(IExp left, IExp right, RelOp op) implements IExp {
    @Override
    public IValue eval(IDictionary<String, IValue> symTable) throws MyException {
        if (!(left.eval(symTable) instanceof IntValue(int n1)))
            throw new ExpressionException("first operand is not an integer");
        if (!(right.eval(symTable) instanceof IntValue(int n2)))
            throw new ExpressionException("second operand is not an integer");
        return switch (op) {
            case EQ -> new BoolValue(n1 == n2);
            case GT -> new BoolValue(n1 > n2);
            case GE -> new BoolValue(n1 >= n2);
            case LT -> new BoolValue(n1 < n2);
            case LE -> new BoolValue(n1 <= n2);
            case NE -> new BoolValue(n1 != n2);
        };
    }
}
