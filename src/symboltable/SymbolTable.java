package symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import scanner.TokenType;

/** Constructs a hashmap of key-value pairs for each unique identifier used in a pascal program.
 *
 * @author William Mork
 */
public class SymbolTable {
    protected HashMap<String, Symbol> symbolTable;

    /**
     * Constructs a symbol table.
     */
    public SymbolTable() {
        symbolTable = new HashMap<>();
    }

    /**
     * Adds a program symbol to the symbol table.
     *
     * @param lexeme lexeme of the program symbol
     * @return true if the symbol was added to the table
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
     *
     * @param lexeme lexeme of the variable symbol
     * @param tokenType token type of the variable symbol
     * @return true if the symbol was added to the table
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
     *
     * @param lexeme lexeme of the array symbol
     * @param tokenType token type of the array symbol
     * @param start start index of the array
     * @param stop stop index of the array
     * @return true if the symbol was added to the table
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
     *
     * @param lexeme lexeme of the function symbol
     * @param tokenType token type of the function symbol
     * @param arguments arguments of the function symbol
     * @return true if the symbol was added to the table
     */
    public boolean addFunction(String lexeme, TokenType tokenType, ArrayList<Symbol> arguments){
        if (symbolTable.containsKey(lexeme)) {
            return false;
        } else {
            symbolTable.put(lexeme, new Symbol(lexeme, Kind.FUNCTION, tokenType, arguments));
            return true;
        }
    }

    /**
     * Checks if a program symbol with a given lexeme already exists in the symbol table.
     *
     * @param lexeme lexeme of program symbol
     * @return true if program symbol with given lexeme exists in the table
     */
    public boolean isProgram(String lexeme){
        try {
            if (symbolTable.get(lexeme).getKind() == Kind.PROGRAM) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No PROGRAM Symbol exists with the following identifier: " + lexeme);
        }
        return false;
    }

    /**
     * Checks if a variable symbol with a given lexeme already exists in the symbol table.
     *
     * @param lexeme lexeme of variable symbol
     * @return true if variable symbol with given lexeme exists in the table
     */
    public boolean isVariable(String lexeme){
        try {
            if (symbolTable.get(lexeme).getKind() == Kind.VARIABLE) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No VARIABLE Symbol exists with the following identifier: " + lexeme);
        }
        return false;
    }

    /**
     * Checks if an array symbol with a given lexeme already exists in the symbol table.
     *
     * @param lexeme lexeme of array symbol
     * @return true if array symbol with given lexeme exists in the table
     */
    public boolean isArray(String lexeme){
        try {
            if (symbolTable.get(lexeme).getKind() == Kind.ARRAY) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No ARRAY Symbol exists with the following identifier: " + lexeme);
        }
        return false;
    }

    /**
     * Checks if a function symbol with a given lexeme already exists in the symbol table.
     *
     * @param lexeme lexeme of function symbol
     * @return true if function symbol with given lexeme exists in the table
     */
    public boolean isFunction(String lexeme){
        try {
            if (symbolTable.get(lexeme).getKind() == Kind.FUNCTION) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No FUNCTION Symbol exists with the following identifier: " + lexeme);
        }
        return false;
    }

    /**
     * Generates a list of the symbols contained in the symbol table hashmap and their
     * appropriate information
     *
     * @return String containing the generated list
     */
    @Override
    public String toString() {
        StringBuilder tableBuilder = new StringBuilder("SYMBOL TABLE: \n");
        Iterator tableIterator = symbolTable.entrySet().iterator();

        while (tableIterator.hasNext()) {
            Map.Entry symbol = (Map.Entry)tableIterator.next();
            tableBuilder.append(symbol.getValue());
            tableBuilder.append("\n");
        }

        return tableBuilder.toString();
    }
}
