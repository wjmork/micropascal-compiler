package symboltable;

import scanner.TokenType;
import java.util.ArrayList;

public class Symbol {
    String identifier;
    TokenType tokenType;
    Kind kind;
    int startIndex;
    int stopIndex;
    ArrayList<Symbol> arguments;

    // Program symbol constructor
    public Symbol(String id, Kind k) {
        identifier = id;
        kind = k;
    }

    // Variable symbol constructor
    public Symbol(String id, TokenType t, Kind k) {
        identifier = id;
        tokenType = t;
        kind = k;
    }

    // Array symbol constructor
    public Symbol(String id, TokenType t, Kind k, int start, int stop) {
        identifier = id;
        tokenType = t;
        kind = k;
        startIndex = start;
        stopIndex = stop;
    }

    // Function symbol constructor
    public Symbol(String id, TokenType t, Kind k, ArrayList args) {
        identifier = id;
        tokenType = t;
        kind = k;
        arguments = args;
    }

    // Returns the Symbol's string identifier
    public String getID() {
        return identifier;
    }

    // Returns the Symbol's token type.
    public TokenType getType() {
        return tokenType;
    }

    // Returns the Symbol's kind
    public Kind getKind() {
        return kind;
    }

    // Returns the Symbol's start index.
    public int getStartIndex() {
        return startIndex;
    }

    // Returns the Symbol's stop index.
    public int getStopIndex() {
        return stopIndex;
    }
}
