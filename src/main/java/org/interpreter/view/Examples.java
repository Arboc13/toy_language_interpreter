package org.interpreter.view;

import org.interpreter.model.exp.ArithExp;
import org.interpreter.model.exp.ArithOp;
import org.interpreter.model.exp.RelOp;
import org.interpreter.model.exp.ReadHeapExp;
import org.interpreter.model.exp.RelationalExp;
import org.interpreter.model.exp.ValueExp;
import org.interpreter.model.exp.VarExp;
import org.interpreter.model.stmt.AssignStmt;
import org.interpreter.model.stmt.CloseRFileStmt;
import org.interpreter.model.stmt.CompStmt;
import org.interpreter.model.stmt.IStmt;
import org.interpreter.model.stmt.IfStmt;
import org.interpreter.model.stmt.NewStmt;
import org.interpreter.model.stmt.OpenRFileStmt;
import org.interpreter.model.stmt.PrintStmt;
import org.interpreter.model.stmt.ReadFileStmt;
import org.interpreter.model.stmt.VarDeclStmt;
import org.interpreter.model.stmt.WhileStmt;
import org.interpreter.model.stmt.WriteHeapStmt;
import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.interpreter.model.type.RefType;
import org.interpreter.model.type.StringType;
import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.StringValue;

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

    // string varf; varf = fileName; openRFile(varf); int varc;
    // readFile(varf, varc); print(varc); readFile(varf, varc); print(varc); closeRFile(varf)
    public static IStmt fileExample(String fileName) {
        return new CompStmt(new VarDeclStmt("varf", new StringType()),
                new CompStmt(new AssignStmt("varf", new ValueExp(new StringValue(fileName))),
                new CompStmt(new OpenRFileStmt(new VarExp("varf")),
                new CompStmt(new VarDeclStmt("varc", new IntType()),
                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")),
                new CompStmt(new ReadFileStmt(new VarExp("varf"), "varc"),
                new CompStmt(new PrintStmt(new VarExp("varc")),
                        new CloseRFileStmt(new VarExp("varf"))))))))));
    }

    public static final IStmt EX4 = fileExample("test.in");

    // int a; int b; a = 5; b = 7; (if a < b then print(a) else print(b))
    public static final IStmt EX5 = new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
            new CompStmt(new AssignStmt("a", new ValueExp(new IntValue(5))),
            new CompStmt(new AssignStmt("b", new ValueExp(new IntValue(7))),
                    new IfStmt(new RelationalExp(RelOp.LT, new VarExp("a"), new VarExp("b")),
                            new PrintStmt(new VarExp("a")),
                            new PrintStmt(new VarExp("b")))))));

    // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(v); print(a)
    public static final IStmt EX6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new NewStmt("a", new VarExp("v")),
            new CompStmt(new PrintStmt(new VarExp("v")),
                    new PrintStmt(new VarExp("a")))))));

    // Ref int v; new(v, 20); Ref Ref int a; new(a, v); print(rH(v)); print(rH(rH(a)) + 5)
    public static final IStmt EX7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new NewStmt("a", new VarExp("v")),
            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
                    new PrintStmt(new ArithExp(ArithOp.ADD,
                            new ReadHeapExp(new ReadHeapExp(new VarExp("a"))),
                            new ValueExp(new IntValue(5)))))))));

    // Ref int v; new(v, 20); print(rH(v)); wH(v, 30); print(rH(v) + 5)
    public static final IStmt EX8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v"))),
            new CompStmt(new WriteHeapStmt("v", new ValueExp(new IntValue(30))),
                    new PrintStmt(new ArithExp(ArithOp.ADD,
                            new ReadHeapExp(new VarExp("v")), new ValueExp(new IntValue(5))))))));

    // Ref int v; new(v, 20); Ref Ref int a; new(a, v); new(v, 30); print(rH(rH(a)))
    public static final IStmt EX9 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(20))),
            new CompStmt(new VarDeclStmt("a", new RefType(new RefType(new IntType()))),
            new CompStmt(new NewStmt("a", new VarExp("v")),
            new CompStmt(new NewStmt("v", new ValueExp(new IntValue(30))),
                    new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));

    // int v; v = 4; (while (v > 0) (print(v); v = v - 1)); print(v)
    public static final IStmt EX10 =
            new CompStmt(new VarDeclStmt("v", new IntType()),
            new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
            new CompStmt(new WhileStmt(
                    new RelationalExp(RelOp.GT, new VarExp("v"), new ValueExp(new IntValue(0))),
                    new CompStmt(new PrintStmt(new VarExp("v")),
                            new AssignStmt("v", new ArithExp(ArithOp.SUB,
                                    new VarExp("v"), new ValueExp(new IntValue(1)))))),
                    new PrintStmt(new VarExp("v")))));

    public static final List<IStmt> ALL = List.of(EX1, EX2, EX3, EX4, EX5, EX6, EX7, EX8, EX9, EX10);
}
