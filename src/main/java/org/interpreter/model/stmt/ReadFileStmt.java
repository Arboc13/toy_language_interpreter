package org.interpreter.model.stmt;

import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.exp.IExp;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.StringValue;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public record ReadFileStmt(IExp exp, String varName) implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException{
        if (!(exp.eval(state.getSymTable()) instanceof StringValue fileName))
            throw new StatementException("readFile: " + exp + " is not a string");
        var fileTable = state.getFileTable();
        if (!fileTable.isDefined(fileName))
            throw new StatementException("readFile: " + fileName + " is not open");
        var symTable = state.getSymTable();
        if(!symTable.isDefined(varName))
            throw new StatementException("readFile: " + varName + " is not declared.");
        if (!(symTable.lookup(varName) instanceof IntValue) )
            throw new StatementException("readFile: " + varName + " is not an integer.");
        try {
            // no try-with-resources: the file must stay open until closeRFile
            var reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            int val = (line == null) ? 0 : Integer.parseInt(line.strip()); // end of file -> 0
            symTable.update(varName, new IntValue(val));
        } catch (IOException e) {
            throw new StatementException("readFile: cannot read " + fileName + ": " + e.getMessage());
        } catch (NumberFormatException e) {
            throw new StatementException("readFile: line in " + fileName + " is not an integer: " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ", " + varName + ")"; }
}