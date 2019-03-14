package symboltable;

import scanner.TokenType;

import java.util.ArrayList;

/**
 * The Token class defined a token object containing it's lexeme and type.
 *
 * @author William Mork
 */
public class Symbol {
    String lexeme;
    TokenType tokenType;
    Kind kind;
    int startIndex;
    int stopIndex;
    ArrayList arguments;

    /**
     * Constructs a program symbol.
     *
     * @param l symbol lexeme
     * @param k symbol kind
     */
    public Symbol(String l, Kind k) {
        lexeme = l;
        kind = k;
    }

    /**
     * Constructs a variable symbol.
     *
     * @param l symbol lexeme
     * @param k symbol kind
     * @param t symbol type
     */
    public Symbol(String l, Kind k, TokenType t) {
        lexeme = l;
        kind = k;
        tokenType = t;
    }

    /**
     * Constructs an array symbol.
     *
     * @param l symbol lexeme
     * @param k symbol kind
     * @param t symbol type
     * @param start start index of array
     * @param stop stop index of array
     */
    public Symbol(String l, Kind k, TokenType t, int start, int stop) {
        lexeme = l;
        kind = k;
        tokenType = t;
        startIndex = start;
        stopIndex = stop;
    }

    /**
     * Constructs a function symbol.
     *
     * @param l symbol lexeme
     * @param k symbol kind
     * @param t symbol type
     * @param args symbol arguments
     */
    public Symbol(String l, Kind k, TokenType t, ArrayList args) {
        lexeme = l;
        kind = k;
        tokenType = t;
        arguments = args;
    }

    /**
     * Returns the Symbol's string identifier
     *
     * @return symbol's lexeme
     */
    public String getID() {
        return lexeme;
    }

    /**
     * Returns the Symbol's token type.
     *
     * @return symbol's type
     */
    public TokenType getType() {
        return tokenType;
    }

    /**
     * Returns the Symbol's kind
     *
     * @return symbol's kind
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Returns the Symbol's start index.
     *
     * @return start index of the symbol
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Returns the Symbol's stop index.
     *
     * @return stop index of the symbol
     */
    public int getStopIndex() {
        return stopIndex;
    }

    /**
     * Returns the Symbol's arguments
     *
     * @return symbol's arguments
     */
    public ArrayList getArgs() {
        return arguments;
    }

    /**
     * Generates a string containing the symbol's lexeme and other appropriate information.
     *
     * @return the generated string
     */
    @Override
    public String toString() {
        if (this.kind == Kind.PROGRAM) {
            return "SYMBOL [LEXEME: " + lexeme + " | KIND: " + kind + "]";
        } else if (this.kind == Kind.VARIABLE) {
            return "SYMBOL [LEXEME: " + lexeme + " | KIND: " + kind + " | TYPE: " + tokenType + "]";
        } else if (this.kind == Kind.ARRAY) {
            return "SYMBOL [LEXEME: " + lexeme + " | KIND: " + kind + " | TYPE: " + tokenType + " | INDICES: " + startIndex + "→" + stopIndex + "]";
        } else if (this.kind == Kind.FUNCTION) {
            return "SYMBOL [LEXEME: " + lexeme + " | KIND: " + kind + " | TYPE: " + tokenType + " | ARGS: " + arguments + "]";
        } else {
            return "Error generating Symbol toString. Symbol object was improperly constructed.";
        }
    }
}
