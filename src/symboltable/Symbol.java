package symboltable;

import scanner.TokenType;
import java.util.ArrayList;

public class Symbol {
    String lexeme;
    TokenType tokenType;
    Kind kind;
    int startIndex;
    int stopIndex;
    ArrayList<Symbol> arguments;

    // Program symbol constructor
    public Symbol(String l, Kind k) {
        lexeme = l;
        kind = k;
    }

    // Variable symbol constructor
    public Symbol(String l, TokenType t, Kind k) {
        lexeme = l;
        tokenType = t;
        kind = k;
    }

    // Array symbol constructor
    public Symbol(String l, TokenType t, Kind k, int start, int stop) {
        lexeme = l;
        tokenType = t;
        kind = k;
        startIndex = start;
        stopIndex = stop;
    }

    // Function symbol constructor
    public Symbol(String l, TokenType t, Kind k, ArrayList args) {
        lexeme = l;
        tokenType = t;
        kind = k;
        arguments = args;
    }
}
