package org.interpreter.model.value;

import org.interpreter.model.type.IType;
import org.interpreter.model.type.RefType;

public record RefValue(int address, IType locationType) implements IValue {
    @Override public IType getType() { return new RefType(locationType); }
    @Override public String toString() {
        return "(" + address + ", " + locationType + ")";
    }
}
