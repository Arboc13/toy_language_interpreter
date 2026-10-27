package org.interpreter.repository;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.prgstate.PrgState;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{

    private List<PrgState> states;

    public Repository(){
        states = new ArrayList<PrgState>();
    }
    @Override
    public PrgState getCrtPrg() {
        return states.getLast();
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
