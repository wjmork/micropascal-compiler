package symboltable;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import scanner.TokenType;

/**
 * This class contains JUnit testing for basic functions of the symbol table.
 *
 * @author William Mork
 */
public class SymbolTableTest {

    private SymbolTable testSymbolTable;

    /**
     * Populates the symbol table with test identifiers
     */
    @Before
    public void setUp() {
        testSymbolTable = new SymbolTable();

        // adding program identifiers
        testSymbolTable.addProgram("testProgram1");
        testSymbolTable.addProgram("testProgram2");

        // adding variable identifiers
        testSymbolTable.addVariable("testVariable1", TokenType.INTEGER);
        testSymbolTable.addVariable("testVariable2", TokenType.REAL);

        // adding array identifiers
        testSymbolTable.addArray("testArray1", TokenType.INTEGER, 0, 10);
        testSymbolTable.addArray("testArray2", TokenType.REAL, -10, 0);

        // creating test arguments for function identifiers
        ArrayList<Symbol> testArguments = new ArrayList();
        testArguments.add(new Symbol("testArg1", Kind.VARIABLE, TokenType.INTEGER));
        testArguments.add(new Symbol("testArg2", Kind.VARIABLE, TokenType.REAL));

        // adding function identifiers
        testSymbolTable.addFunction("testFunction1", TokenType.INTEGER, testArguments);
        testSymbolTable.addFunction("testFunction2", TokenType.REAL, testArguments);
    }

    /**
     * Tests the symbol table toString() function, fails if there are unexpected
     * characters in the output string.
     */
    @Test
    public void printTable() {
        System.out.println("Testing toString() method...");
        System.out.println(testSymbolTable);
        Assert.assertFalse(testSymbolTable.toString().contains("@"));
        System.out.println("Success.");
    }

    /**
     * Tests the addition of program identifiers to the symbol table
     */
    @Test
    public void addProgram() {
        System.out.println("Testing addProgram method...");
        boolean result;

        // Add a new program identifier to the symbol table
        result = testSymbolTable.addProgram("testProgram3");
        Assertions.assertEquals(true, result);

        // Add an existing program identifier to the symbol table
        result = testSymbolTable.addProgram("testProgram1");
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }

    /**
     * Tests the addition of variable identifiers to the symbol table
     */
    @Test
    public void addVariable() {
        System.out.println("Testing addVariable method...");
        boolean result;

        // Add a new variable identifier to the symbol table
        result = testSymbolTable.addVariable("testVariable3", TokenType.INTEGER);
        Assertions.assertEquals(true, result);

        // Add an existing variable identifier to the symbol table
        result = testSymbolTable.addVariable("testVariable1", TokenType.REAL);
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }

    /**
     * Tests the addition of array identifiers to the symbol table
     */
    @Test
    public void addArray() {
        System.out.println("Testing addArray method...");
        boolean result;

        // Add a new array identifier to the symbol table
        result = testSymbolTable.addArray("testArray3", TokenType.INTEGER, 40, 60);
        Assertions.assertEquals(true, result);

        // Add an existing array identifier to the symbol table
        result = testSymbolTable.addArray("testArray1", TokenType.INTEGER, -1, 0);
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }

    /**
     * Tests the addition of function identifiers to the symbol table
     */
    @Test
    public void addFunction() {
        System.out.println("Testing addFunction method...");
        boolean result;
        ArrayList<Symbol> testArguments2 = new ArrayList();
        testArguments2.add(new Symbol("testArg3", Kind.VARIABLE, TokenType.INTEGER));

        // Add a new function identifier to the symbol table
        result = testSymbolTable.addFunction("testFunction3", TokenType.REAL, testArguments2);
        Assertions.assertEquals(true, result);

        // Add an existing function identifier to the symbol table
        result = testSymbolTable.addFunction("testFunction1", TokenType.REAL, testArguments2);
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }
}