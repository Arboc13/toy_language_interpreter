package org.interpreter.model.value;

import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IType;
import org.interpreter.model.type.IntType;

public record BoolValue(boolean val) implements IValue {
    @Override public IType getType() { return new BoolType(); }
    @Override public String toString() { return Boolean.toString(val); }
}
