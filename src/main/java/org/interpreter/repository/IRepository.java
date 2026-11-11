package org.interpreter.repository;

import org.interpreter.exceptions.MyException;
import org.interpreter.model.prgstate.PrgState;

public interface IRepository {
    PrgState getCrtPrg();
    void logPrgStateExec() throws MyException;
}
