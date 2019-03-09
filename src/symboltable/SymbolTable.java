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

    public boolean isProgram(String name){
        try {
            if (symbolTable.get(name).getKind() == Kind.PROGRAM) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No PROGRAM Symbol exists with the following identifier: " + name);
        }
        return false;
    }

    public boolean isVariable(String name){
        try {
            if (symbolTable.get(name).getKind() == Kind.VARIABLE) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No VARIABLE Symbol exists with the following identifier: " + name);
        }
        return false;
    }

    public boolean isArray(String name){
        try {
            if (symbolTable.get(name).getKind() == Kind.ARRAY) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No ARRAY Symbol exists with the following identifier: " + name);
        }
        return false;
    }

    public boolean isFunction(String name){
        try {
            if (symbolTable.get(name).getKind() == Kind.FUNCTION) {
                return true;
            }
        }
        catch (Exception e)
        {
            System.out.println("No FUNCTION Symbol exists with the following identifier: " + name);
        }
        return false;
    }

}
