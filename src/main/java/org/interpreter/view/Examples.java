package org.interpreter.view;

import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.CompStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.stmt.IfStmt;
import org.interpreter.model.stmt.PrintStmt;
import org.interpreter.model.stmt.VarDeclStmt;
import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;

import java.util.List;

public final class Examples {

    private Examples() {}

    public static final IStmt EX1 = new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))),
                    new PrintStmt(new VarExp("v"))));

    public static final IStmt EX2 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
                    new CompStmt(new AssignStmt("a",
                            new ArithExp(ArithOp.ADD, new ValueExp(new IntValue(2)),
                                    new ArithExp(ArithOp.MUL, new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5))))),
                            new CompStmt(new AssignStmt("b",
                                    new ArithExp(ArithOp.ADD, new VarExp("a"), new ValueExp(new IntValue(1)))),
                                    new PrintStmt(new VarExp("b"))))));

    public static final IStmt EX3 = new CompStmt(new VarDeclStmt("a", new BoolType()),
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v"))))));

    public static final IStmt EX4 = new CompStmt(new VarDeclStmt("a", new BoolType()),
            new CompStmt(new VarDeclStmt("v", new IntType()),
                    new CompStmt( new PrintStmt(new VarExp("a")),
                    new CompStmt(new AssignStmt("a", new ValueExp(new BoolValue(true))),
                            new CompStmt(new IfStmt(new VarExp("a"),
                                    new AssignStmt("v", new ValueExp(new IntValue(2))),
                                    new AssignStmt("v", new ValueExp(new IntValue(3)))),
                                    new PrintStmt(new VarExp("v")))))));

    public static final List<IStmt> ALL = List.of(EX1, EX2, EX3, EX4);
}
