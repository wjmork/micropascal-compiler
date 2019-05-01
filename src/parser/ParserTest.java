package parser;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

/**
 * This class contains JUnit testing for the high-level production rules of
 * the pascal Grammar.
 *
 * @author William Mork
 */
public class ParserTest {

    /**
     * Tests for proper instantiation of a symbol table within a parser instance.
     * Population of the symbol table is tested within the SymbolTableTest.java class.
     *
     * @result The test fails if the symbol table is not instantiated correctly.
     */
    @Test
    public void symbolTableInstanceTest() {
        System.out.println("Testing instantiation of symbol table...");
        boolean result = true;
        Parser testParser = new Parser("");
        try {
            SymbolTable testSymbolTable = testParser.getSymbolTable();
        } catch (Exception e){
            System.out.println("Symbol table was not properly generated for parser instance.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests parsing of the declarations() production rules.
     *
     * @result The test fails if a known valid declaration is parsed as invalid.
     */
    @Test
    public void declarationsTest() {
        System.out.println("Testing declarations...");
        boolean result = true;
        String input = "var foo: integer;";
        Parser testParser = new Parser(input);
        try {
            testParser.declarations();
        } catch (Exception e){
            System.out.println("Test failed. A known valid declaration was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests parsing of the subprogram_declaration() production rules.
     *
     * @result The test fails if a known valid subprogram declaration is parsed as invalid.
     */
    @Test
    public void subProgramTest() {
        System.out.println("Testing subprogram_declaration...");
        boolean result = true;
        String input = "function func(foo: integer): integer; begin end.";
        Parser testParser = new Parser(input);
        try {
            testParser.subprogram_declaration();
        } catch (Exception e){
            System.out.println("test failed. A known valid subprogram_declaration was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }

    /**
     * Tests parsing of the statement() production rules.
     *
     * @result The test fails if a known valid statement is parsed as invalid,
     * or if ambiguities between assignment and procedure_statement() are not
     * resolved.
     */
    @Test
    public void statementTest() {
        System.out.println("Testing statement...");
        String input = "foo := 1";
        String input2 = "testProcedure(a, b, c);";

        // tests for an assignment call from statement()
        Parser testParser = new Parser(input);
        testParser.getSymbolTable().addVariable("foo", TokenType.ID);
        testParser.statement();
        Assertions.assertTrue(testParser.getSymbolTable().isVariable("foo"));
        Assertions.assertFalse(testParser.getSymbolTable().isProcedure("foo"));

        // tests for a procedure call from statement()
        testParser = new Parser(input2);
        testParser.getSymbolTable().addProcedure("testProcedure");
        testParser.statement();
        Assertions.assertFalse(testParser.getSymbolTable().isVariable("testProcedure"));
        Assertions.assertTrue(testParser.getSymbolTable().isProcedure("testProcedure"));
        System.out.println("Success!");
    }

    /**
     * Tests parsing of the simple_expression() production rules.
     *
     * @result The test fails if a known valid expression is parsed as invalid.
     */
    @Test
    public void expressionTest() {
        System.out.println("Testing expression...");
        boolean result = true;
        String input = "foo - fee";
        Parser testParser = new Parser(input);
        try {
            testParser.simple_expression();
        } catch (Exception e){
            System.out.println("test failed. A known valid expression was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }

    /**
     * Tests parsing of the factor() production rules.
     *
     * @result The test fails if a known valid factor is parsed as invalid.
     */
    @Test
    public void factorTest() {
        System.out.println("Testing factor...");
        boolean result = true;
        String input = "foo [2 - 2]";
        Parser testParser = new Parser(input);
        try {
            testParser.factor();
        } catch (Exception e){
            System.out.println("test failed. A known valid factor was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }
}
