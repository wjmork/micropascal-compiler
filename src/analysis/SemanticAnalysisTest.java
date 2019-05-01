package analysis;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import parser.Parser;
import symboltable.SymbolTable;
import syntaxtree.*;

import java.io.File;

/**
 * This class contains JUnit testing for the semantic analysis module.
 *
 * @author William Mork
 */
public class SemanticAnalysisTest {

    /**
     * Tests that money.pas passes semantic analysis.
     *
     * @result The test passes if the money.pas file is confirmed to pass semantic analysis.
     */
    @Test
    public void moneyTest() {
        System.out.println("Semantic Analysis: Testing analysis for money.pas...");
        boolean result = false;
        File input = new File("src/pascal/money.pas");
        Parser testParser = new Parser(input);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        SemanticAnalyzer testAnalyzer = new SemanticAnalyzer(testRoot, testSymbolTable);

        if (testAnalyzer.validAssignments && testAnalyzer.validDeclarations) {
            result = true;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests that the semantic analyzer properly determines if variables are declared before their respective use cases.
     *
     * @result The test passes if the semantic analyzer properly checks for variable declaration.
     */
    @Test
    public void declarationsTest() {
        System.out.println("Semantic Analysis: Testing variable declarations check...");
        boolean result = false;
        Parser testParser = new Parser("program test; \nvar declared: integer; \nbegin\ndeclared := declared * undeclared \nend\n.");
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        SemanticAnalyzer testAnalyzer = new SemanticAnalyzer(testRoot, testSymbolTable);

        if (testAnalyzer.validAssignments && !testAnalyzer.validDeclarations) {
            result = true;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests that the semantic analyzer properly determines if types are consistent across assignment and operation calls.
     *
     * @result The test passes if the semantic analyzer properly checks for type consistency.
     */
    @Test
    public void assignmentTest() {
        System.out.println("Semantic Analysis: Testing token type check...");
        boolean result = false;
        Parser testParser = new Parser("program test;\nvar var1: integer;\nvar var2: real;\nbegin\nvar1 := var1 * var2\nend\n.");
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        SemanticAnalyzer testAnalyzer = new SemanticAnalyzer(testRoot, testSymbolTable);

        if (!testAnalyzer.validAssignments && testAnalyzer.validDeclarations) {
            result = true;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests that the semantic analyzer properly determines if both prior tests can be simultaneously detected.
     *
     * @result The test passes if the two prior tests run properly.
     */
    @Test
    public void bothChecksTest() {
        System.out.println("Semantic Analysis: Testing token type check...");
        boolean result = false;
        Parser testParser = new Parser("program test;\nvar var1: integer;\nvar var2: real;\nbegin\nvar1 := var1 * var2 * var3\nend\n.\n");
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        SemanticAnalyzer testAnalyzer = new SemanticAnalyzer(testRoot, testSymbolTable);

        if (!testAnalyzer.validAssignments && !testAnalyzer.validDeclarations) {
            result = true;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }
}
