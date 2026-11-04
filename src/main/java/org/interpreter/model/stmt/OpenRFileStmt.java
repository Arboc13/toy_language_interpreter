package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.adt.IDictionary;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record OpenRFileStmt(IExp exp) implements IStmt { // S8

    @Override
    public PrgState execute(PrgState state) throws MyException
    {
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("openRFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (fileTable.isDefined(fileName))
            throw new StatementException("openRFile: " + fileName + " is already open");
        try {
            // no try-with-resources: the file must stay open until close RFile
            var reader = new BufferedReader(new FileReader(fileName.val()));
            fileTable.put(fileName, reader);
        } catch (IOException e) {
            throw new StatementException("openRFile: cannot open " + fileName + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "openRFile(" + exp + ")"; } }

// TODO: ReadFileStmt(IExp exp, String varName) – S9
// check varName (declared, type int) and the file (string, in FileTable);
// readLine(): null -> 0, otherwise Integer.parseInt(line.strip());
// turn IOException and NumberFormatException into StatementException
// TODO: CloseRFileStmt(IExp exp) – S10
// close() the reader, then fileTable.remove(fileName)