package org.interpreter.model.value;

import org.interpreter.model.type.IType;

public interface IValue {
    IType getType();
}


// any implementation of equals must respect the equal rules (hover .equals)
