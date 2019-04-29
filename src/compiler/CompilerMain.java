package compiler;

import analysis.SemanticAnalyzer;
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
        Parser parser = new Parser("src/pascal/money.pas", true);
        ProgramNode rootNode = parser.program();

        exportSyntaxTree(rootNode);
        exportSymbolTable(parser.getSymbolTable().toString());

        System.out.println("File successfully parsed.");
        System.out.println("The syntax tree can be found in the src/compiler/ directory.");
        System.out.println("The symbol table can be found in the src/compiler/ directory.");

        SemanticAnalyzer analysis = new SemanticAnalyzer(rootNode, parser.getSymbolTable());
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
    public static void exportSyntaxTree(ProgramNode root) {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/compiler/syntaxtree.txt")))) {
            writer.write(root.indentedToString(0));
        }
        catch (Exception e) { }
    }
}
