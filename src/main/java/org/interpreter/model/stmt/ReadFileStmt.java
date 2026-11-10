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
            // no try-with-resources: the file must stay open until close RFile
            var reader = fileTable.lookup(fileName);
            String line = reader.readLine();
            if(line == null){
                int val = Integer.parseInt(line.strip());
                symTable.update(varName, new IntValue(val));
            }

        } catch (IOException e) {
            throw new StatementException("ReadFileStmt: cannot open " + fileName + ": " + e.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "readFile(" + exp + ")"; }
}


// TODO: ReadFileStmt(IExp exp, String varName) – S9
// check varName (declared, type int) and the file (string, in FileTable);
// readLine(): null -> 0, otherwise Integer.parseInt(line.strip());
// turn IOException and NumberFormatException into StatementException