package symboltable;

import scanner.TokenType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;
import java.util.Iterator;
import java.util.Map;

/** Constructs a hashmap of key-value pairs for each unique identifier used in a pascal program.
 *
 * @author William Mork
 */
public class SymbolTable {
    public HashMap<String, Symbol> symbolTable;
    public String fileName = null;

    /**
     * Constructs a symbol table.
     */
    public SymbolTable() {
        symbolTable = new HashMap<>();
    }

    /**
     * Adds a program symbol to the symbol table.
     * @param lexeme The lexeme of the program symbol.
     * @return True if the symbol was added to the table.
     */
    public boolean addProgram(String lexeme) {
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.PROGRAM));
            return true;
        }
    }

    /**
     * Adds a variable symbol to the symbol table.
     * @param lexeme The lexeme of the variable symbol.
     * @param tokenType The token type of the variable symbol.
     * @return True if the symbol was added to the table.
     */
    public boolean addVariable(String lexeme, TokenType tokenType) {
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.VARIABLE, tokenType));
            return true;
        }
    }

    /**
     * Adds an array symbol to the symbol table.
     * @param lexeme The lexeme of the array symbol.
     * @param tokenType The token type of the array symbol.
     * @param start The start index of the array.
     * @param stop The stop index of the array.
     * @return True if the symbol was added to the table.
     */
    public boolean addArray(String lexeme, TokenType tokenType, int start, int stop){
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.ARRAY, tokenType, start, stop));
            return true;
        }
    }

    /**
     * Adds a function symbol to the symbol table.
     * @param lexeme The lexeme of the function symbol.
     * @param tokenType The token type of the function symbol.
     * @return True if the symbol was added to the table.
     */
    public boolean addFunction(String lexeme, TokenType tokenType){
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.FUNCTION, tokenType));
            return true;
        }
    }

    /**
     * Adds a procedure symbol to the symbol table.
     * @param lexeme The lexeme of the procedure symbol.
     * @return True if the symbol was added to the table.
     */
    public boolean addProcedure(String lexeme){
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.PROCEDURE));
            return true;
        }
    }

    /**
     * Returns the token type of a symbol.
     * @param name The lexeme of symbol.
     * @return Token type of the symbol.
     */
    public TokenType getType(String name) {
        return symbolTable.get(name).getType();
    }

    /**
     * Checks if a program symbol with a given lexeme already exists in the symbol table.
     * @param lexeme The lexeme of the program symbol.
     * @return True if a program symbol with the given lexeme already exists in the table.
     */
    public boolean isProgram(String lexeme){
        return symbolTable.containsKey(lexeme) && symbolTable.get(lexeme).getKind() == Kind.PROGRAM;
    }

    /**
     * Checks if a variable symbol with a given lexeme already exists in the symbol table.
     * @param lexeme The lexeme of the variable symbol.
     * @return True if a variable symbol with the given lexeme already exists in the table.
     */
    public boolean isVariable(String lexeme){
        return symbolTable.containsKey(lexeme) && symbolTable.get(lexeme).getKind() == Kind.VARIABLE;
    }

    /**
     * Checks if an array symbol with a given lexeme already exists in the symbol table.
     * @param lexeme The lexeme of the array symbol.
     * @return True if an array symbol with the given lexeme already exists in the table.
     */
    public boolean isArray(String lexeme){
        return symbolTable.containsKey(lexeme) && symbolTable.get(lexeme).getKind() == Kind.ARRAY;
    }

    /**
     * Checks if a function symbol with a given lexeme already exists in the symbol table.
     * @param lexeme The lexeme of the function symbol.
     * @return True if a function symbol with the given lexeme already exists in the table.
     */
    public boolean isFunction(String lexeme){
        return symbolTable.containsKey(lexeme) && symbolTable.get(lexeme).getKind() == Kind.FUNCTION;
    }

    /**
     * Checks if a procedure symbol with a given lexeme already exists in the symbol table.
     * @param lexeme The lexeme of the procedure symbol.
     * @return True if a procedure symbol with the given lexeme already exists in the table.
     */
    public boolean isProcedure(String lexeme){
        return symbolTable.containsKey(lexeme) && symbolTable.get(lexeme).getKind() == Kind.PROCEDURE;
    }

    public boolean exists(String lexeme) {
        if (isVariable(lexeme) || isProcedure(lexeme) || isArray(lexeme) || isProgram(lexeme) || isFunction(lexeme)) {
            return true;
        } else {
            return false;
        }
    }

    public Symbol getSymbol(String lexeme) {
        if (symbolTable.containsKey(lexeme)) {
            return symbolTable.get(lexeme);
        }
        return null;
    }

    /**
     * Generates a list of the symbols contained in the symbol table hashmap and their
     * appropriate information using a stringbuilder.
     * @return A String containing the generated list.
     */
    @Override
    public String toString() {
        StringBuilder tableBuilder;
        if (fileName == null) {
            tableBuilder = new StringBuilder("SYMBOL TABLE: \n");
        } else {
            tableBuilder = new StringBuilder("SYMBOL TABLE for " + fileName + ": \n");
        }
        Iterator tableIterator = symbolTable.entrySet().iterator();

        while (tableIterator.hasNext()) {
            Map.Entry symbol = (Map.Entry)tableIterator.next();
            tableBuilder.append(symbol.getValue());
            tableBuilder.append("\n");
        }

        return tableBuilder.toString();
    }
}
