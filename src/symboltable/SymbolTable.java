package symboltable;

import scanner.TokenType;

import java.util.HashMap;

public class SymbolTable {
    protected HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<>();
    }

    public boolean addProgram(){
        return false;
    }

    public boolean addVariable(){
        return false;
    }

    public boolean addArray(){
        return false;
    }

    public boolean addFunction(){
        return false;
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
