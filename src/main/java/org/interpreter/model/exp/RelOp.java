package org.interpreter.model.exp;

public enum RelOp {
    EQ("=="), GT(">"), GE(">="),LT("<"), LE("<="),
    NE("!=");
    private final String symbol;

    RelOp(String symbol) {this.symbol = symbol; }

    @Override
    public String toString() {
        return symbol;
    }

}
