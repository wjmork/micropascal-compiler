package syntaxtree;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import parser.*;
import scanner.TokenType;
import symboltable.SymbolTable;

import java.io.File;

/**
 * This class contains JUnit testing for the high-level functions of
 * the syntax tree.
 *
 * @author William Mork
 */
public class SyntaxTreeTest {

    /**
     * Tests syntax tree generation for the program() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to program().
     */
    @Test
    public void programTest() {
        File input = new File("src/pascal/money.pas");
        Parser testParser = new Parser(input);
        ProgramNode testNode = testParser.program();
        String expected = "Program: sample\n|-- Declarations\n|-- --- Name: dollars, Type: INTEGER\n|-- --- Name: yen, Type: INTEGER\n|-- --- Name: bitcoins, Type: INTEGER\n|-- SubProgramDeclarations\n|-- Compound Statement\n|-- --- Assignment\n|-- --- --- Name: dollars, Type: INTEGER\n|-- --- --- Value: 10000, Type: INTEGER\n|-- --- Assignment\n|-- --- --- Name: yen, Type: INTEGER\n|-- --- --- Operation: MULTIPLY\n|-- --- --- --- Name: dollars, Type: INTEGER\n|-- --- --- --- Value: 110, Type: INTEGER\n|-- --- Assignment\n|-- --- --- Name: bitcoins, Type: INTEGER\n|-- --- --- Operation: DIVIDE\n|-- --- --- --- Name: dollars, Type: INTEGER\n|-- --- --- --- Value: 3900, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the statement() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to statement().
     */
    @Test
    public void compoundStatementTest() {
        Parser testParser = new Parser("begin foo := foo + bar + foo end");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        CompoundStatementNode testNode = testParser.compound_statement();
        String expected = "Compound Statement\n|-- Assignment\n|-- --- Name: foo, Type: INTEGER\n|-- --- Operation: PLUS\n|-- --- --- Operation: PLUS\n|-- --- --- --- Name: foo, Type: INTEGER\n|-- --- --- --- Name: bar, Type: INTEGER\n|-- --- --- Name: foo, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the subprogram_declarations() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to subprogram_declarations().
     */
    @Test
    public void subprogram_declarationsTest() {
        Parser testParser = new Parser("function testfunc(foo : real) : real ; begin end");
        SubProgramDeclarationsNode testNode = testParser.subprogram_declarations();
        String expected = "SubProgramDeclarations\n|-- ";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the declarations() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to declarations().
     */
    @Test
    public void declarationsTest() {
        Parser testParser = new Parser("var foo, bar : real ; var foobar : integer ;");
        DeclarationsNode testNode = testParser.declarations();
        String expected = "Declarations\n|-- Name: foo, Type: REAL\n|-- Name: bar, Type: REAL\n|-- Name: foobar, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the factor() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to factor().
     */
    @Test
    public void factorTest() {
        // Testing a single value
        Parser testParser = new Parser("0");
        ExpressionNode testNode = testParser.factor();
        String expected = "Value: 0, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        // Testing a single identifier
        testParser = new Parser("foo");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testNode = testParser.factor();
        expected = "Name: foo, Type: INTEGER\n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        // Testing a simple function / procedure call
        testParser = new Parser("foo(1, 2*3)");
        testParser.getSymbolTable().addFunction("foo", TokenType.INTEGER);
        testNode = testParser.factor();
        expected = "Name: foo, Type: INTEGER\nArguments: \n|-- Value: 1, Type: INTEGER\n|-- Operation: MULTIPLY\n|-- --- Value: 2, Type: INTEGER\n|-- --- Value: 3, Type: INTEGER\n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        // Testing a function / procedure call with second argument
        testParser = new Parser("foo(1, 2*bar)");
        testParser.getSymbolTable().addProcedure("foo");
        testNode = testParser.factor();
        actual = testNode.indentedToString(0);
        expected = "Name: foo, Type: null\nArguments: \n|-- Value: 1, Type: INTEGER\n|-- Operation: MULTIPLY\n|-- --- Value: 2, Type: INTEGER\n|-- --- Name: bar, Type: null\n";
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the simple_expression() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to simple_expression().
     */
    @Test
    public void simple_expressionTest() {
        Parser testParser = new Parser("foo * bar");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        ExpressionNode testNode = testParser.simple_expression();
        String expected = "Operation: MULTIPLY\n|-- Name: foo, Type: INTEGER\n|-- Name: bar, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the statement() function.
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to statement().
     */
    @Test
    public void statementTest() {
        // Simple variable assignment and expression
        Parser testParser = new Parser("foo := foo - bar");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        StatementNode testNode = testParser.statement();
        String expected = "Assignment\n|-- Name: foo, Type: INTEGER\n|-- Operation: MINUS\n|-- --- Name: foo, Type: INTEGER\n|-- --- Name: bar, Type: INTEGER\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        /* Deprecated Test
        // Testing a simple if statement
        testParser = new Parser("if foo > bar then foo := foo - bar else foo := foo + bar");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        testNode = testParser.statement();
        System.out.println(testNode.indentedToString(0));
        expected = "If:\n|-- Name: foo\n|-- Assignment\n|-- --- Name: foo\n|-- --- Operation: MINUS\n|-- --- --- Name: foo\n|-- --- --- Name: bar\n|-- Assignment\n|-- --- Name: foo\n|-- --- Operation: PLUS\n|-- --- --- Name: foo\n|-- --- --- Name: bar\n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
        */

        /* Deprecated Test
        // Testing a simple while statement
        testParser = new Parser("while foo > bar do foo := foo - 1");
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        testNode = testParser.statement();
        expected = "While:\n|-- Name: foo\n|-- Assignment\n|-- --- Name: foo\n|-- --- Operation: MINUS\n|-- --- --- Name: foo\n|-- --- --- Value: 1\n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
        */
    }
}