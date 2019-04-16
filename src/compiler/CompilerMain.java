package compiler;

import parser.Parser;
import syntaxtree.ProgramNode;

import java.io.*;

/**
 * Top-level class for the compiler; will be expanded as
 * modules are developed and new features are added.
 *
 * As of 3/15/2019, this class can create a parser instance,
 * populate the symbol table and generate a syntax tree.
 *
 * @author William Mork
 * @version 0.4.1
 */
public class CompilerMain {

    public static void main(String[] args) {
        Parser parser;
        try {
            parser = new Parser("src/pascal/" + args[0], true);
            exportSyntaxTree(parser);
            exportSymbolTable(parser.getSymbolTable().toString());
        } catch(Exception e) {
            System.out.println("Could not locate input file. Make sure your micro-pascal file is in the src/pascal/ directory and is spelled correctly as a parameter in the console.");
        }
        System.out.println("File successfully parsed.");
        System.out.println("The syntax tree for " + args[0] + " can be found in the src/compiler/ directory.");
        System.out.println("The symbol table for " + args[0] + " can be found in the src/compiler/ directory.");
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
