/**
 * JUnit testing for the symbol table.
 * @author William Mork
 */

package symboltable;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import scanner.TokenType;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.fail;

public class SymbolTableTest {

    private SymbolTable testSymbolTable;

    @BeforeEach
    void setUp() {
        testSymbolTable = new SymbolTable();

        ArrayList<Symbol> testArguments = new ArrayList();

        testArguments.add(new Symbol("testArg1", Kind.VARIABLE, TokenType.INTEGER));
        testArguments.add(new Symbol("testArg2", Kind.VARIABLE, TokenType.REAL));

        //adding program ids
        testSymbolTable.addProgram("testProgram1");
        testSymbolTable.addProgram("testProgram2");

        //adding variables
        testSymbolTable.addVariable("testVariable1", TokenType.INTEGER);
        testSymbolTable.addVariable("testVariable2", TokenType.REAL);

        //adding array id's
        testSymbolTable.addArray("testArray1", TokenType.INTEGER, 0, 10);
        testSymbolTable.addArray("testArray2", TokenType.REAL, -10, 0);

        testSymbolTable.addFunction("testFunction1", TokenType.INTEGER, testArguments);
        testSymbolTable.addFunction("testFunction2", TokenType.REAL, testArguments);
    }

    @AfterEach
    void tearDown() {
        testSymbolTable.symbolTable.clear();
        testSymbolTable = null;
    }


    public SymbolTableTest() {
    }

}