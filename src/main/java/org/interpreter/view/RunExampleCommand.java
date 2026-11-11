package org.interpreter.view;

import org.interpreter.controller.Controller;
import org.interpreter.exceptions.MyException;

public class RunExampleCommand extends Command {

    private final Controller controller;
    private boolean executed = false;

    public RunExampleCommand(String key, String description, Controller controller)
    {
        super(key, description);
        this.controller = controller;
    }

    @Override public void execute() {
        if (executed) {
            // an example runs only once
            System.out.println("This example has already been executed.");
            return;
        } executed = true;

        try {
            controller.allStep(true);
        } catch (MyException e) {
            // errors the controller cannot solve
            System.out.println("Error: " + e.getMessage());
        }
    }
}


