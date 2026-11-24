package org.interpreter.model.type;

import org.interpreter.model.value.IValue;
import org.interpreter.model.value.RefValue;

public record RefType(IType inner) implements IType {
    @Override public IValue defaultValue() { return new RefValue(0, inner); } // null
    @Override public String toString() { return "Ref(" + inner + ")"; }
}
