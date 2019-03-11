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
    public Symbol(String id, Kind k, TokenType t) {
        identifier = id;
        kind = k;
        tokenType = t;
    }

    // Array symbol constructor
    public Symbol(String id, Kind k, TokenType t, int start, int stop) {
        identifier = id;
        kind = k;
        tokenType = t;
        startIndex = start;
        stopIndex = stop;
    }

    // Function symbol constructor
    public Symbol(String id, Kind k, TokenType t, ArrayList args) {
        identifier = id;
        kind = k;
        tokenType = t;
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

    // Returns the Symbol's arguments
    public ArrayList<Symbol> getArgs() {
        return arguments;
    }

    @Override
    public String toString() {
        if (this.kind == Kind.PROGRAM) {
            return "SYMBOL [ID: " + identifier + " | KIND: " + kind + "]";
        } else if (this.kind == Kind.VARIABLE) {
            return "SYMBOL [ID: " + identifier + " | KIND: " + kind + " | TYPE: " + tokenType + "]";
        } else if (this.kind == Kind.ARRAY) {
            return "SYMBOL [ID: " + identifier + " | KIND: " + kind + " | TYPE: " + tokenType + " | INDEX: " + startIndex + "→" + stopIndex + "]";
        } else if (this.kind == Kind.FUNCTION) {
            return "SYMBOL [ID: " + identifier + " | KIND: " + kind + " | TYPE: " + tokenType + " | ARGS: " + arguments + "]";
        } else {
            return "Error generating Symbol toString. Symbol object was improperly constructed.";
        }
    }
}
