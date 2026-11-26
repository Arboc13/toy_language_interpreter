package org.interpreter.model.exp;

import org.interpreter.exceptions.ExpressionException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.adt.IHeap;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.RefValue;

public record ReadHeapExp(IExp exp) implements IExp { // E6
    @Override
    public IValue eval(IDictionary<String, IValue> symTable, IHeap heap) throws MyException {
        if (!(exp.eval(symTable, heap) instanceof RefValue ref))
            throw new ExpressionException("rH: " + exp + " is not a reference");
        if (!heap.isDefined(ref.address()))
            throw new ExpressionException("rH: address " + ref.address() + " is not in the heap");
        return heap.get(ref.address());
    }
    @Override public String toString() { return "rH(" + exp + ")"; }
}
