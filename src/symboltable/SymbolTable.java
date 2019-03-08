package symboltable;

import java.util.HashMap;

public class SymbolTable {
    protected HashMap<String, Symbol> symbolTable;

    public SymbolTable() {
        symbolTable = new HashMap<>();
    }

    public boolean addProgram(String name){
        if (symbolTable.containsKey(name)) {
            return false;
        } else {
            symbolTable.put(name, new Symbol(name, Kind.PROGRAM));
            return true;
        }
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
