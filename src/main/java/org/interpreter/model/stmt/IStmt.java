package org.interpreter.model.stmt;

import org.interpreter.model.adt.ADTException;
import org.interpreter.model.adt.MyException;
import org.interpreter.model.prgstate.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, ADTException;
}

