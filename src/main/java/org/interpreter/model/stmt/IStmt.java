package org.interpreter.model.stmt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.prgstate.PrgState;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException, ADTException;
}

