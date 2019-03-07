package symboltable;

import java.util.ArrayList;

public class Symbol {
    String lexeme;
    String type;
    String kind;
    int startIndex;
    int stopIndex;
    ArrayList<Symbol> arguments;

    // Program symbol constructor
    public Symbol(String l, String k) {
        lexeme = l;
        kind = k;
    }

    // Variable symbol constructor
    public Symbol(String l, String t, String k) {
        lexeme = l;
        type = t;
        kind = k;
    }

    // Array symbol constructor
    public Symbol(String l, String t, String k, int start, int stop) {
        lexeme = l;
        type = t;
        kind = k;
        startIndex = start;
        stopIndex = stop;
    }

    // Function symbol constructor
    public Symbol(String l, String t, String k, ArrayList args) {
        lexeme = l;
        type = t;
        kind = k;
        arguments = args;
    }
}
