package org.interpreter.model.type;

import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;

public record IntType() implements IType {
    @Override public IValue defaultValue() { return new IntValue(0); }
    @Override public String toString() { return "int"; }
}
