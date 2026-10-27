package org.interpreter.controller;

import com.sun.nio.sctp.IllegalReceiveException;
import org.interpreter.exceptions.ADTException;
import org.interpreter.exceptions.MyException;
import org.interpreter.exceptions.StatementException;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.repository.IRepository;



public class Controller {
    private final IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        var stack = state.getExeStack();
        if (stack.isEmpty()) throw new StatementException("program state stack is empty");
        IStmt crtStmt = stack.pop();
        return crtStmt.execute(state);
    }
    public void allStep(boolean display_f) throws MyException {
        PrgState prg = repo.getCrtPrg();
        if (display_f)
            display(prg.toString()); // only if the display flag is on
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            if (display_f)
                display(prg.toString());
        }
    }

    public void display(String program){
        IO.println(program + "\n");
    }


}
