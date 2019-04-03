package compiler;

import parser.Parser;
import syntaxtree.ProgramNode;

import java.io.*;

/**
 * Top-level class for the compiler; will be expanded as
 * modules are developed and new features are added.
 *
 * As of 3/15/2019, this class can create a parser instance
 * and populate the symbol table.
 *
 * @author William Mork
 */
public class CompilerMain {

    public static void main(String[] args) {
        Parser parser = new Parser("src/pascal/simple.pas", true);

        parser.program();
        exportSymbolTable(parser.getSymbolTable().toString());

        // This parser is a separate instance for now and is used to test the syntax tree output.
        Parser parser2 = new Parser("src/pascal/simple.pas", true);
        exportSyntaxTree(parser2);
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

    /**
     * Writes the indented syntax tree to a file.
     * @param parser the parser instance for which the syntax tree will be generated.
     */
    public static void exportSyntaxTree(Parser parser) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compiler/syntaxtree.txt")))) {
            writer.write(parser.program().indentedToString(0));
        }
        catch (Exception e) { }
    }
}
