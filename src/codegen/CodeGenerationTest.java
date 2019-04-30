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
     * Tests that money.pas passes semantic analysis
     *
     * @result The test passes if the money.pas file is confirmed to pass semantic analysis.
     */
    @Test
    public void moneyTest() {
        System.out.println("Code Generation: Testing code generation for money.pas");
        boolean result = false;
        Parser testParser = new Parser("src/pascal/money.pas", true);
        ProgramNode testRoot = testParser.program();
        SymbolTable testSymbolTable = testParser.getSymbolTable();

        SemanticAnalyzer testAnalyzer = new SemanticAnalyzer(testRoot, testSymbolTable);

        if (testAnalyzer.validAssignments && testAnalyzer.validDeclarations) {
            result = true;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }
}
