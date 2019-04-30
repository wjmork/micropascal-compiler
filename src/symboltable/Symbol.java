package symboltable;

import scanner.TokenType;

import java.util.ArrayList;

/**
 * The Symbol class defines a symbol object, and contains the symbol lexeme
 * and other appropriate information pertaining to the symbol.
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
    private String address;

    /**
     * Constructs a program symbol.
     * @param l The lexeme of the symbol.
     * @param k The kind of the symbol.
     */
    public Symbol(String l, Kind k) {
        lexeme = l;
        kind = k;
    }

    /**
     * Constructs a variable symbol.
     * @param l The lexeme of the symbol.
     * @param k The kind of the symbol.
     * @param t The type of the symbol.
     */
    public Symbol(String l, Kind k, TokenType t) {
        lexeme = l;
        kind = k;
        tokenType = t;
    }

    /**
     * Constructs an array symbol.
     * @param l The lexeme of the symbol.
     * @param k The kind of the symbol.
     * @param t The type of the symbol.
     * @param start The start index of array.
     * @param stop The stop index of array.
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
     * @param l The lexeme of the symbol.
     * @param k The type of the symbol.
     * @param t The type of the symbol.
     * @param args The symbol arguments.
     */
    public Symbol(String l, Kind k, TokenType t, ArrayList args) {
        lexeme = l;
        kind = k;
        tokenType = t;
        arguments = args;
    }

    /**
     * Returns the Symbol's string identifier.
     * @return The lexeme of the symbol.
     */
    public String getID() {
        return lexeme;
    }

    /**
     * Returns the Symbol's token type.
     * @return The type of the symbol.
     */
    public TokenType getType() { return tokenType; }

    /**
     * Returns the Symbol's kind
     * @return The type of the symbol.
     */
    public Kind getKind() {
        return kind;
    }

    /**
     * Returns the Symbol's start index.
     * @return The start index of the symbol.
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Returns the Symbol's stop index.
     * @return The stop index of the symbol.
     */
    public int getStopIndex() {
        return stopIndex;
    }

    /**
     * Returns the Symbol's arguments.
     * @return The arguments of the symbol.
     */
    public ArrayList getArgs() {
        return arguments;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String memoryAddress) {
        address = memoryAddress;
    }

    /**
     * Generates a string containing the symbol's lexeme and other appropriate information.
     * @return The generated string.
     */
    @Override
    public String toString() {
        if (this.kind == Kind.PROGRAM) {
            return "SYMBOL [LEXEME: " + lexeme + "\t | KIND: " + kind + "]";
        } else if (this.kind == Kind.VARIABLE) {
            return "SYMBOL [LEXEME: " + lexeme + "\t | KIND: " + kind + "\t | TYPE: " + tokenType + "]";
        } else if (this.kind == Kind.ARRAY) {
            return "SYMBOL [LEXEME: " + lexeme + "\t | KIND: " + kind + "\t | TYPE: " + tokenType + "\t | INDICES: " + startIndex + "→" + stopIndex + "]";
        } else if (this.kind == Kind.FUNCTION) {
            return "SYMBOL [LEXEME: " + lexeme + "\t | KIND: " + kind + "\t | TYPE: " + tokenType + "\t | ARGS: " + arguments + "]";
        } else {
            return "Error generating Symbol toString. Symbol object was improperly constructed.";
        }
    }
}
