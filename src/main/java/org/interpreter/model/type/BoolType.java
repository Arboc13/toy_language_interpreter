package org.interpreter.model.type;

import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;

public record BoolType() implements IType {
    @Override public IValue defaultValue() { return new BoolValue(false); }
    @Override public String toString() { return "bool"; }
}