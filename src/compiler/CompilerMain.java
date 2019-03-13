package compiler;

import parser.Parser;

public class CompilerMain {

    public static void main(String[] args) {
        Parser parser = new Parser("src/pascal/simplest.pas", true);
        try {parser.program();} catch(Exception e){}

        System.out.println(parser.getSymbolTable().toString());
    }
}
