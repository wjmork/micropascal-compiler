package symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import scanner.TokenType;

public class SymbolTable {
    protected HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<>();
    }

    public boolean addProgram(String name) {
        if (symbolTable.containsKey(name)) {
            return false;
        } else {
            symbolTable.put(name, new Symbol(name, Kind.PROGRAM));
            return true;
        }
    }

    public boolean addVariable(String name, TokenType tokenType) {
        if (symbolTable.containsKey(name)) {
            return false;
        } else {
            symbolTable.put(name, new Symbol(name, Kind.VARIABLE, tokenType));
            return true;
        }
    }

    public boolean addArray(String name, TokenType tokenType, int start, int stop){
        if (symbolTable.containsKey(name)) {
            return false;
        } else {
            symbolTable.put(name, new Symbol(name, Kind.ARRAY, tokenType, start, stop));
            return true;
        }
    }

    public boolean addFunction(String name, TokenType tokenType, ArrayList<Symbol> arguments){
        if (symbolTable.containsKey(name)) {
            return false;
        } else {
            symbolTable.put(name, new Symbol(name, Kind.FUNCTION, tokenType, arguments));
            return true;
        }
    }

    public boolean isProgram(){
        return false;
    }

    public boolean isVariable(){
        return false;
    }

    public boolean isArray(){
        return false;
    }

    public boolean isFunction(){
        return false;
    }

}
