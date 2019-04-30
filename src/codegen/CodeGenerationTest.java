package codegen;

import analysis.SemanticAnalyzer;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import parser.Parser;
import symboltable.SymbolTable;
import syntaxtree.ProgramNode;

/**
 * This class contains JUnit testing for the code generation module.
 *
 * @author William Mork
 */
public class CodeGenerationTest {

    /**
     * Tests that money.pas generates proper MIPS assembly code.
     *
     * @result The test passes if the money.pas file generates proper MIPS assembly code.
     */
    @Test
    public void moneyCodeTest() {
        System.out.println("Code Generation: Testing code generation for money.pas");
        Parser testParser = new Parser("src/pascal/money.pas", true);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        CodeGeneration testCodeGenerator = new CodeGeneration(testRoot, testParser.getSymbolTable());

        String expected = ".data\ndollars:\t.word\t0\nyen:\t.word\t0\nbitcoins:\t.word\t0\n\n.text\nmain:\naddi\t$sp,\t$sp,\t-40\nsw\t$s7,\t36($sp)\nsw\t$s6,\t32($sp)\nsw\t$s5,\t28($sp)\nsw\t$s4,\t24($sp)\nsw\t$s3,\t20($sp)\nsw\t$s2,\t16($sp)\nsw\t$s1,\t12($sp)\nsw\t$s0,\t8($sp)\nsw\t$fp,\t4($sp)\nsw\t$ra,\t0($sp)\naddi\t$s0,\t$zero,\t10000\nsw\t$s0,\tdollars\nla\t$t0,\tdollars\naddi\t$t1,\t$zero,\t110\nmult\t$t0,\t$t1\nmflo\t$s0\nsw\t$s0,\tyen\nla\t$t0,\tdollars\naddi\t$t1,\t$zero,\t3900\ndiv\t$t0,\t$t1\nmflo\t$s0\nsw\t$s0,\tbitcoins\nlw\t$s7,\t36($sp)\nlw\t$s6,\t32($sp)\nlw\t$s5,\t28($sp)\nlw\t$s4,\t24($sp)\nlw\t$s3,\t20($sp)\nlw\t$s2,\t16($sp)\nlw\t$s1,\t12($sp)\nlw\t$s0,\t8($sp)\nlw\t$fp,\t4($sp)\nlw\t$ra,\t0($sp)\naddi\t$sp,\t$sp,\t40\njr\t$ra\n";

        String result = testCodeGenerator.codeWriter();

        Assertions.assertEquals(expected, result);
        System.out.println("Success.");
    }

    /**
     * Tests that a basic program generates proper MIPS assembly code.
     *
     * @result The test passes if a basic program produces proper MIPS assembly code.
     */
    @Test
    public void programCodeTest() {
        System.out.println("Code Generation: Testing code generation for a basic program.");
        Parser testParser = new Parser("src/pascal/simplest.pas", true);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        CodeGeneration testCodeGenerator = new CodeGeneration(testRoot, testParser.getSymbolTable());

        String expected = ".data\n\n.text\nmain:\naddi\t$sp,\t$sp,\t-40\nsw\t$s7,\t36($sp)\nsw\t$s6,\t32($sp)\nsw\t$s5,\t28($sp)\nsw\t$s4,\t24($sp)\nsw\t$s3,\t20($sp)\nsw\t$s2,\t16($sp)\nsw\t$s1,\t12($sp)\nsw\t$s0,\t8($sp)\nsw\t$fp,\t4($sp)\nsw\t$ra,\t0($sp)\nlw\t$s7,\t36($sp)\nlw\t$s6,\t32($sp)\nlw\t$s5,\t28($sp)\nlw\t$s4,\t24($sp)\nlw\t$s3,\t20($sp)\nlw\t$s2,\t16($sp)\nlw\t$s1,\t12($sp)\nlw\t$s0,\t8($sp)\nlw\t$fp,\t4($sp)\nlw\t$ra,\t0($sp)\naddi\t$sp,\t$sp,\t40\njr\t$ra\n";

        String result = testCodeGenerator.codeWriter();

        Assertions.assertEquals(expected, result);
        System.out.println("Success.");
    }

    /**
     * Tests that expressions generate proper MIPS assembly code.
     *
     * @result The test passes if expressions produce proper MIPS assembly code.
     */
    @Test
    public void expressionCodeTest() {
        System.out.println("Code Generation: Testing code generation for a series of basic expressions");
        Parser testParser = new Parser("program foo;\nvar fee, fi, fo, fum: integer;\nbegin\nfee := 4;\nfi := 5 / fee;\nfo := 3 * fee + fi;\nfum := fee * fi / fo\nend\n.\n", false);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        CodeGeneration testCodeGenerator = new CodeGeneration(testRoot, testParser.getSymbolTable());

        String expected = ".data\nfee:\t.word\t0\nfi:\t.word\t0\nfo:\t.word\t0\nfum:\t.word\t0\n\n.text\nmain:\naddi\t$sp,\t$sp,\t-40\nsw\t$s7,\t36($sp)\nsw\t$s6,\t32($sp)\nsw\t$s5,\t28($sp)\nsw\t$s4,\t24($sp)\nsw\t$s3,\t20($sp)\nsw\t$s2,\t16($sp)\nsw\t$s1,\t12($sp)\nsw\t$s0,\t8($sp)\nsw\t$fp,\t4($sp)\nsw\t$ra,\t0($sp)\naddi\t$s0,\t$zero,\t4\nsw\t$s0,\tfee\naddi\t$t0,\t$zero,\t5\nla\t$t1,\tfee\ndiv\t$t0,\t$t1\nmflo\t$s0\nsw\t$s0,\tfi\naddi\t$t1,\t$zero,\t3\nla\t$t2,\tfee\nmult\t$t1,\t$t2\nmflo\t$t0\nla\t$t1,\tfi\nadd\t$s0,\t$t0,\t$t1\nsw\t$s0,\tfo\nla\t$t0,\tfee\nla\t$t2,\tfi\nla\t$t3,\tfo\ndiv\t$t2,\t$t3\nmflo\t$t1\nmult\t$t0,\t$t1\nmflo\t$s0\nsw\t$s0,\tfum\nlw\t$s7,\t36($sp)\nlw\t$s6,\t32($sp)\nlw\t$s5,\t28($sp)\nlw\t$s4,\t24($sp)\nlw\t$s3,\t20($sp)\nlw\t$s2,\t16($sp)\nlw\t$s1,\t12($sp)\nlw\t$s0,\t8($sp)\nlw\t$fp,\t4($sp)\nlw\t$ra,\t0($sp)\naddi\t$sp,\t$sp,\t40\njr\t$ra\n";

        String result = testCodeGenerator.codeWriter();

        Assertions.assertEquals(expected, result);
        System.out.println("Success.");
    }

    /**
     * Tests that statements generate proper MIPS assembly code.
     *
     * @result The test passes if statements produce proper MIPS assembly code.
     */
    @Test
    public void statementCodeTest() {
        System.out.println("Code Generation: Testing code generation for a series of basic statements");
        Parser testParser = new Parser("program foo;\nvar fee, fi, fo, fum: integer;\nbegin\nfee := 4;\nfi := 5;\nfo := 3 * fee + fi;\nif fo < 13\nthen\nfo := 13\nelse\nfo := 26\nend\n.\n", false);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        CodeGeneration testCodeGenerator = new CodeGeneration(testRoot, testParser.getSymbolTable());

        String expected = ".data\nfee:\t.word\t0\nfi:\t.word\t0\nfo:\t.word\t0\nfum:\t.word\t0\n\n.text\nmain:\naddi\t$sp,\t$sp,\t-40\nsw\t$s7,\t36($sp)\nsw\t$s6,\t32($sp)\nsw\t$s5,\t28($sp)\nsw\t$s4,\t24($sp)\nsw\t$s3,\t20($sp)\nsw\t$s2,\t16($sp)\nsw\t$s1,\t12($sp)\nsw\t$s0,\t8($sp)\nsw\t$fp,\t4($sp)\nsw\t$ra,\t0($sp)\naddi\t$s0,\t$zero,\t4\nsw\t$s0,\tfee\naddi\t$s0,\t$zero,\t5\nsw\t$s0,\tfi\naddi\t$t1,\t$zero,\t3\nla\t$t2,\tfee\nmult\t$t1,\t$t2\nmflo\t$t0\nla\t$t1,\tfi\nadd\t$s0,\t$t0,\t$t1\nsw\t$s0,\tfo\naddi\t$t0,\t$zero,\t13\nbge\t$t0,\t$t1,\telse0\naddi\t$s0,\t$zero,\t13\nsw\t$s0,\tfo\nj\tendIf0\nelse0:\naddi\t$s1,\t$zero,\t26\nsw\t$s1,\tfo\n\nendIf0:\nlw\t$s7,\t36($sp)\nlw\t$s6,\t32($sp)\nlw\t$s5,\t28($sp)\nlw\t$s4,\t24($sp)\nlw\t$s3,\t20($sp)\nlw\t$s2,\t16($sp)\nlw\t$s1,\t12($sp)\nlw\t$s0,\t8($sp)\nlw\t$fp,\t4($sp)\nlw\t$ra,\t0($sp)\naddi\t$sp,\t$sp,\t40\njr\t$ra\n";

        String result = testCodeGenerator.codeWriter();

        Assertions.assertEquals(expected, result);
        System.out.println("Success.");
    }

    /**
     * Tests that declarations generate proper MIPS assembly code.
     *
     * @result The test passes if declarations produce proper MIPS assembly code.
     */
    @Test
    public void declarationsCodeTest() {
        System.out.println("Code Generation: Testing code generation for a series of basic declarations");
        Parser testParser = new Parser("program foo;\nvar fee, fi: integer;\nvar fo, fum: real;\nbegin\nend\n.\n", false);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        CodeGeneration testCodeGenerator = new CodeGeneration(testRoot, testParser.getSymbolTable());

        String expected = ".data\nfee:\t.word\t0\nfi:\t.word\t0\nfo:\t.word\t0\nfum:\t.word\t0\n\n.text\nmain:\naddi\t$sp,\t$sp,\t-40\nsw\t$s7,\t36($sp)\nsw\t$s6,\t32($sp)\nsw\t$s5,\t28($sp)\nsw\t$s4,\t24($sp)\nsw\t$s3,\t20($sp)\nsw\t$s2,\t16($sp)\nsw\t$s1,\t12($sp)\nsw\t$s0,\t8($sp)\nsw\t$fp,\t4($sp)\nsw\t$ra,\t0($sp)\nlw\t$s7,\t36($sp)\nlw\t$s6,\t32($sp)\nlw\t$s5,\t28($sp)\nlw\t$s4,\t24($sp)\nlw\t$s3,\t20($sp)\nlw\t$s2,\t16($sp)\nlw\t$s1,\t12($sp)\nlw\t$s0,\t8($sp)\nlw\t$fp,\t4($sp)\nlw\t$ra,\t0($sp)\naddi\t$sp,\t$sp,\t40\njr\t$ra\n";

        String result = testCodeGenerator.codeWriter();

        Assertions.assertEquals(expected, result);
        System.out.println("Success.");
    }
}
