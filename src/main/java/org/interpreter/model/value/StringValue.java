package org.interpreter.model.value;


import org.interpreter.model.type.IType;
import org.interpreter.model.type.StringType;

import java.util.Objects;


public record StringValue(String val) implements IValue {
    @Override public IType getType() { return new StringType(); }
    @Override public String toString() { return "\"" + val + "\""; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StringValue that = (StringValue) o;
        return Objects.equals(val, that.val);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(val);
    }
}

