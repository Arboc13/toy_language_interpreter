package org.interpreter;

import org.interpreter.view.Examples;
import org.interpreter.view.View;

public class Main {
    static void main() {
        new View(Examples.ALL).show();
    }
}
