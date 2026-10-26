package org.interpreter.model.value;

import org.interpreter.model.type.IType;
import org.interpreter.model.type.IntType;

import java.util.Objects;

public record IntValue(int val) implements IValue {
    @Override public IType getType() { return new IntType(); }
    @Override public String toString() { return Integer.toString(val); }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return val == intValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }

}
