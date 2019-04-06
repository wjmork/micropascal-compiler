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
     * Tests syntax tree generation for the program() function
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to program().
     */
    @Test
    public void programTest() {
        Parser testParser = new Parser("src/pascal/money.pas", true);
        ProgramNode testNode = testParser.program();
        String expected = "Program: sample\n|-- Declarations\n|-- --- Name: dollars\n|-- --- Name: yen\n|-- --- Name: bitcoins\n|-- SubProgramDeclarations\n|-- Compound Statement\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the statement() function
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to statement().
     */
    @Test
    public void compoundStatementTest() {
        Parser testParser = new Parser("begin foo := foo + bar + foo end", false);
        SymbolTable testTable = testParser.getSymbolTable();
        testTable.addVariable("foo", TokenType.INTEGER);
        testTable.addVariable("bar", TokenType.INTEGER);
        CompoundStatementNode testNode = testParser.compound_statement();
        String expected = "Compound Statement\n";
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }

    /**
     * Tests the syntax tree generation for the subprogram_declarations() function
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
     * Tests the syntax tree generation for the declarations() function
     *
     * @result The test fails if the syntax tree is improperly constructed during a
     * call to declarations().
     */
    @Test
    public void declarationsTest() {
        Parser testParser = new Parser("var foo, bar : real ; var foobar : integer ;", false);
        DeclarationsNode testNode = testParser.declarations();
        String expected = "Declarations\n|-- Name: foo\n|-- Name: bar\n|-- Name: foobar\n";;
        String actual = testNode.indentedToString(0);
        Assertions.assertEquals(expected, actual);
        System.out.println("Success.");
    }
}



