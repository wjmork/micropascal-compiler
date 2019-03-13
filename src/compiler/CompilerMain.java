package compiler;

import parser.Parser;

public class CompilerMain {

    public static void main(String[] args) {
        Parser parser = new Parser("src/pascal/simplest.pas", true);
        parser.program();
        System.out.println(parser.getSymbolTable().toString());
    }
}
