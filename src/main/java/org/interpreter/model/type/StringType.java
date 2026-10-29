package org.interpreter.model.type;


import org.interpreter.model.value.IValue;
import org.interpreter.model.value.StringValue;

public record StringType() implements IType {
    @Override public IValue defaultValue() { return new StringValue(""); }
    @Override public String toString() { return "text"; }
}
