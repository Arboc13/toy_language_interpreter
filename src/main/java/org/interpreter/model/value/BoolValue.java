package org.interpreter.model.value;

import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IType;
import org.interpreter.model.type.IntType;

import java.util.Objects;

public record BoolValue(boolean val) implements IValue {
    @Override public IType getType() { return new BoolType(); }
    @Override public String toString() { return Boolean.toString(val); }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return val == boolValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}
