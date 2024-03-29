package symboltable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import scanner.TokenType;

import java.util.ArrayList;

/**
 * This class contains JUnit testing for basic functions of the symbol table.
 *
 * @author William Mork
 */
public class SymbolTableTest {

    private SymbolTable testSymbolTable;

    /**
     * Populates the symbol table with test identifiers.
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
        // ArrayList<Symbol> testArguments = new ArrayList();
        // testArguments.add(new Symbol("testArg1", Kind.VARIABLE, TokenType.INTEGER));
        // testArguments.add(new Symbol("testArg2", Kind.VARIABLE, TokenType.REAL));

        // adding function identifiers
        testSymbolTable.addFunction("testFunction1", TokenType.INTEGER);
        testSymbolTable.addFunction("testFunction2", TokenType.REAL);

        // adding procedure identifiers
        testSymbolTable.addProcedure("testProcedure1");
        testSymbolTable.addProcedure("testProcedure2");
    }

    /**
     * Tests the symbol table toString() function.
     * @result fails if there are unexpected characters in the output string.
     */
    @Test
    public void printTableTest() {
        System.out.println("Testing toString() method...");
        System.out.println(testSymbolTable);
        Assert.assertFalse(testSymbolTable.toString().contains("@"));
        System.out.println("Success.");
    }

    /**
     * Tests the addition of program identifiers to the symbol table.
     * @result fails if program identifiers are improperly added to the symbol table.
     */
    @Test
    public void addProgramTest() {
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
     * Tests the addition of variable identifiers to the symbol table.
     * @result fails if variable identifiers are improperly added to the symbol table.
     */
    @Test
    public void addVariableTest() {
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
     * Tests the addition of array identifiers to the symbol table.
     * @result fails if array identifiers are improperly added to the symbol table.
     */
    @Test
    public void addArrayTest() {
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
     * Tests the addition of function identifiers to the symbol table.
     * @result fails if function identifiers are improperly added to the symbol table.
     */
    @Test
    public void addFunctionTest() {
        System.out.println("Testing addFunction method...");
        boolean result;
        // ArrayList<Symbol> testArguments2 = new ArrayList();
        // testArguments2.add(new Symbol("testArg3", Kind.VARIABLE, TokenType.INTEGER));

        // Add a new function identifier to the symbol table
        result = testSymbolTable.addFunction("testFunction3", TokenType.REAL);
        Assertions.assertEquals(true, result);

        // Add an existing function identifier to the symbol table
        result = testSymbolTable.addFunction("testFunction1", TokenType.REAL);
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }

    /**
     * Tests the addition of procedure identifiers to the symbol table.
     * @result fails if procedure identifiers are improperly added to the symbol table.
     */
    @Test
    public void addProcedureTest() {
        System.out.println("Testing addProcedure method...");
        boolean result;
        // ArrayList<Symbol> testArguments2 = new ArrayList();
        // testArguments2.add(new Symbol("testArg3", Kind.VARIABLE, TokenType.INTEGER));

        // Add a new function identifier to the symbol table
        result = testSymbolTable.addProcedure("testProcedure3");
        Assertions.assertEquals(true, result);

        // Add an existing function identifier to the symbol table
        result = testSymbolTable.addProcedure("testProcedure1");
        Assertions.assertEquals(false, result);
        System.out.println("Success.");
    }

    /**
     * Tests the isProgram() method.
     * @result fails if the existence of a program symbol is unknown within
     * the symbol table.
     */
    @Test
    public void isProgramTest() {
        System.out.println("Testing isProgram method...");
        boolean result;

        // Test if a program known to exist in the symbol table is in the symbol table
        result = testSymbolTable.isProgram("testProgram1");
        Assertions.assertEquals(true, result);

        // Test if a program that is not in the symbol table is in the symbol table
        result = testSymbolTable.isProgram("testProgram3");
        Assertions.assertEquals(false, result);
    }

    /**
     * Tests the isVariable() method.
     * @result fails if the existence of a variable symbol is unknown within
     * the symbol table.
     */
    @Test
    public void isVariableTest() {
        System.out.println("Testing isVariable method...");
        boolean result;

        // Test if a variable known to exist in the symbol table is in the symbol table
        result = testSymbolTable.isVariable("testVariable1");
        Assertions.assertEquals(true, result);

        // Test if a variable that is not in the symbol table is in the symbol table
        result = testSymbolTable.isVariable("testVariable3");
        Assertions.assertEquals(false, result);
    }

    /**
     * Tests the isArray() method.
     * @result fails if the existence of an array symbol is unknown within
     * the symbol table.
     */
    @Test
    public void isArrayTest() {
        System.out.println("Testing isArray method...");
        boolean result;

        // Test if an array known to exist in the symbol table is in the symbol table
        result = testSymbolTable.isArray("testArray1");
        Assertions.assertEquals(true, result);

        // Test if an array that is not in the symbol table is in the symbol table
        result = testSymbolTable.isArray("testArray3");
        Assertions.assertEquals(false, result);
    }

    /**
     * Tests the isFunction() method.
     * @result fails if the existence of a function symbol is unknown within
     * the symbol table.
     */
    @Test
    public void isFunctionTest() {
        System.out.println("Testing isFunction method...");
        boolean result;

        // Test if a function known to exist in the symbol table is in the symbol table
        result = testSymbolTable.isFunction("testFunction1");
        Assertions.assertEquals(true, result);

        // Test if a function that is not in the symbol table is in the symbol table
        result = testSymbolTable.isFunction("testFunction3");
        Assertions.assertEquals(false, result);
    }

    /**
     * Tests the isProcedure() method.
     * @result fails if the existence of a procedure symbol is unknown within
     * the symbol table.
     */
    @Test
    public void isProcedureTest() {
        System.out.println("Testing isProcedure method...");
        boolean result;

        // Test if a procedure known to exist in the symbol table is in the symbol table
        result = testSymbolTable.isProcedure("testProcedure1");
        Assertions.assertEquals(true, result);

        // Test if a procedure that is not in the symbol table is in the symbol table
        result = testSymbolTable.isProcedure("testProcedure3");
        Assertions.assertEquals(false, result);
    }

}