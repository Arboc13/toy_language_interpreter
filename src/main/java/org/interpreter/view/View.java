package org.interpreter.view;

import org.interpreter.controller.Controller;
import org.interpreter.exceptions.MyException;
import org.interpreter.model.adt.MyDictionary;
import org.interpreter.model.adt.MyList;
import org.interpreter.model.adt.MyStack;
import org.interpreter.model.prgstate.PrgState;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.value.IValue;
import org.interpreter.repository.Repository;

import java.util.List;

public class View {
    private final List<IStmt> programs;
    private IStmt selected;

    public View(List<IStmt> programs) {
        this.programs = programs;
    }

    public void show() {
        while (true) {
            IO.println("");
            IO.println("1. Select program");
            IO.println("2. Run selected program");
            IO.println("0. Exit");
            String option = IO.readln("> ");
            if (option == null) return;
            switch (option.trim()) {
                case "1" -> selectProgram();
                case "2" -> runProgram();
                case "0" -> { return; }
                default -> IO.println("Invalid option");
            }
        }
    }

    private void selectProgram() {
        for (int i = 0; i < programs.size(); i++)
            IO.println((i + 1) + ". " + programs.get(i));
        String input = IO.readln("Program: ");
        if (input == null) return;
        try {
            int index = Integer.parseInt(input.trim()) - 1;
            if (index < 0 || index >= programs.size()) {
                IO.println("Invalid program number");
                return;
            }
            selected = programs.get(index);
            IO.println("Selected: " + selected);
        } catch (NumberFormatException e) {
            IO.println("Invalid program number");
        }
    }

    private void runProgram() {
        if (selected == null) {
            IO.println("No program selected");
            return;
        }
        Repository repo = new Repository();
        repo.addPrg(new PrgState(new MyStack<IStmt>(), new MyDictionary<String, IValue>(), new MyList<IValue>(), selected));
        try {
            new Controller(repo).allStep(true);
        } catch (MyException e) {
            IO.println("Error: " + e.getMessage());
        }
    }
}
