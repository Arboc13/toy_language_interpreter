package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record CloseRFileStmt(IExp exp) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("CloseRFileStmt: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("CloseRFileStmt: " + fileName + " is not open");
        try {
            fileTable.lookup(fileName).close();
        } catch (IOException e) {
            throw new StatementException("closeRFile: cannot close " + fileName + ": " + e.getMessage());
        }
        fileTable.remove(fileName);
        return state;
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp + ")";
    }
}