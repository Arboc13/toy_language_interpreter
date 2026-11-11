package org.interpreter.repository;

import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.RepositoryException;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.prgstate.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> states;
    private final String logFilePath;

    public Repository(PrgState initial, String logFilePath){
        states = new ArrayList<PrgState>();
        states.add(initial);
        this.logFilePath = logFilePath;
    }
    @Override
    public PrgState getCrtPrg() {
        return states.getLast();
    }

    @Override
    public void logPrgStateExec() throws MyException {
        try (var logFile = new PrintWriter(
                new BufferedWriter(new FileWriter(logFilePath, true)))) { // append
            logFile.println(getCrtPrg().toLogString());
        } catch (IOException e) {
            throw new RepositoryException("cannot write the log file " + logFilePath
                    + ": " + e.getMessage());
        }
    }


    public void addPrg(PrgState prg){
        states.add(prg);
    }


    public Object get(int index) throws ADTException {
        if (index < 0 || index >= states.size()){
            throw new ADTException("Invalid index.");
        }
        return states.get(index);
    }

    public int size() {
        return states.size();
    }

    @Override
    public String toString(){
        String string = "";
        for (var prg : states){
            string = string + prg.toString() + "\n";
        }
        return string;
    }


}
