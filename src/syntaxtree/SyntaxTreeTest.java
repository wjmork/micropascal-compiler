package syntaxtree;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import parser.*;
import scanner.TokenType;
import symboltable.SymbolTable;

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
        Parser testParser = new Parser("src/pascal/money.pas", true);
        ProgramNode testNode = testParser.program();
        String expected = "Program: sample\n|-- Declarations\n|-- --- Name: dollars\n|-- --- Name: yen\n|-- --- Name: bitcoins\n|-- SubProgramDeclarations\n|-- Compound Statement\n|-- --- Assignment\n|-- --- --- Name: dollars\n|-- --- --- Value: 1000000\n|-- --- Assignment\n|-- --- --- Name: yen\n|-- --- --- Operation: MULTIPLY\n|-- --- --- --- Name: dollars\n|-- --- --- --- Value: 110\n|-- --- Assignment\n|-- --- --- Name: bitcoins\n|-- --- --- Operation: DIVIDE\n|-- --- --- --- Name: dollars\n|-- --- --- --- Value: 3900\n";
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
        Parser testParser = new Parser("begin foo := foo + bar + foo end", false);
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testParser.getSymbolTable().addVariable("bar", TokenType.INTEGER);
        CompoundStatementNode testNode = testParser.compound_statement();
        String expected = "Compound Statement\n|-- Assignment\n|-- --- Name: foo\n|-- --- Operation: PLUS\n|-- --- --- Operation: PLUS\n|-- --- --- --- Name: foo\n|-- --- --- --- Name: bar\n|-- --- --- Name: foo\n";
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
        Parser testParser = new Parser("function testfunc(foo : real) : real ; begin end", false);
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
        Parser testParser = new Parser("var foo, bar : real ; var foobar : integer ;", false);
        DeclarationsNode testNode = testParser.declarations();
        String expected = "Declarations\n|-- Name: foo\n|-- Name: bar\n|-- Name: foobar\n";
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
        Parser testParser = new Parser("0", false);
        //SymbolTable testTable = testParser.getSymbolTable();
        //testTable.addFunction("foo", TokenType.INTEGER);
        //testTable.addVariable("bar", TokenType.INTEGER);
        ExpressionNode testNode = testParser.factor();
        String expected = "Value: 0\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        // Testing a single identifier
        testParser = new Parser("foo", false);
        testParser.getSymbolTable().addVariable("foo", TokenType.INTEGER);
        testNode = testParser.factor();
        expected = "Name: foo\n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");

        // Testing a simple function
        testParser = new Parser("foo(1, 2*3)", false);
        testParser.getSymbolTable().addFunction("foo", TokenType.INTEGER);
        testNode = testParser.factor();
        expected = "Name: foo \n";
        actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }
}



