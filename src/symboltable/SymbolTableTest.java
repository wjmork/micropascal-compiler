/**
 * JUnit testing for the symbol table.
 * @author William Mork
 */

package symboltable;

import java.util.ArrayList;

import org.junit.Before;
import scanner.TokenType;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions.assertEquals;
//import org.junit.jupiter.api.Assertions.fail;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SymbolTableTest {

    private SymbolTable testSymbolTable;

    @Before
    public void setUp() {
        testSymbolTable = new SymbolTable();

        //ArrayList<Symbol> testArguments = new ArrayList();

        //testArguments.add(new Symbol("testArg1", Kind.VARIABLE, TokenType.INTEGER));
        //testArguments.add(new Symbol("testArg2", Kind.VARIABLE, TokenType.REAL));

        //adding program ids
        testSymbolTable.addProgram("testProgram1");
        testSymbolTable.addProgram("testProgram2");

        //adding variables
        testSymbolTable.addVariable("testVariable1", TokenType.INTEGER);
        testSymbolTable.addVariable("testVariable2", TokenType.REAL);

        //adding array id's
        testSymbolTable.addArray("testArray1", TokenType.INTEGER, 0, 10);
        testSymbolTable.addArray("testArray2", TokenType.REAL, -10, 0);

        //testSymbolTable.addFunction("testFunction1", TokenType.INTEGER, testArguments);
        //testSymbolTable.addFunction("testFunction2", TokenType.REAL, testArguments);
    }

    @AfterEach
    public void tearDown() {
        testSymbolTable.symbolTable.clear();
        testSymbolTable = null;
    }

    @Test
    public void printTable() {
        System.out.println("Testing toString...");
        testSymbolTable.toString();
        assertFalse(testSymbolTable.toString().contains("@"));
    }

    @Test
    public void addProgram() {
        System.out.println("Testing addProgram function...");
        boolean result;

        String name = "testProgram3";
        result = testSymbolTable.addProgram(name);
        assertEquals(true, result);
        System.out.println("Program successfully added to symbol table.");

        // Add a program already in the symbol table
        name = "testProgram1";
        result = testSymbolTable.addProgram(name);
        assertEquals(false, result);
        System.out.println("Program already exists in symbol table with ID: " + name + ".");
    }
}