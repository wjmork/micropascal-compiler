package compiler;

import parser.Parser;

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

        System.out.println(parser.getSymbolTable().toString());
    }
}
