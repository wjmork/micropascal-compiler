package compiler;

import parser.Parser;

import java.io.*;

/**
 * Top-level class for the compiler; will be expanded as
 * modules are developed and new features are added.
 *
 * As of 3/14/2019, this class can create a parser instance
 * and populate the symbol table.
 *
 * @author William Mork
 */
public class CompilerMain {

    public static void main(String[] args) {
        Parser parser = new Parser("src/pascal/simple.pas", true);

        parser.program();
        exportSymbolTable(parser.getSymbolTable().toString());
    }

    /**
     * Writes the symbol table to a file.
     * @param symbolTable symbol table to be written to a file.
     */
    public static void exportSymbolTable(String symbolTable) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compiler/symboltable.txt")))) {
            writer.write(symbolTable);
        }
        catch (Exception e) { }
    }
}
