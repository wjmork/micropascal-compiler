package parser;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * This class contains JUnit testing for the high-level production rules of
 * the Recognizer Grammar.
 *
 * @author William Mork
 */
public class RecognizerTest {
    /**
     * Tests parser recognization of the simple.pas file.
     *
     * @result The test fails if the simple.pas file is recognized as invalid.
     */
    @Test
    public void programTest() {
        System.out.println("Testing program...");
        boolean result = true;
        String testProgram = "src/pascal/simple.pas";
        Recognizer testRecognizer = new Recognizer(testProgram, true);
        try {
            testRecognizer.program();
        } catch (Exception e){
            System.out.println("Test failed. A known valid program was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests parser recognization of the declarations() production rules.
     *
     * @result The test fails if a known valid declaration is recognized as invalid.
     */
    @Test
    public void declarationsTest() {
        System.out.println("Testing declarations...");
        boolean result = true;
        String input = "var foo: integer;";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.declarations();
        } catch (Exception e){
            System.out.println("Test failed. A known valid declaration was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success.");
    }

    /**
     * Tests parser recognization of the subprogram_declaration() production rules.
     *
     * @result The test fails if a known valid subprogram declaration is recognized as invalid.
     */
    @Test
    public void subProgramTest() {
        System.out.println("Testing subprogram_declaration...");
        boolean result = true;
        String input = "function func(foo: integer): integer;";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.subprogram_declaration();
        } catch (Exception e){
            System.out.println("test failed. A known valid subprogram_declaration was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }

    /**
     * Tests parser recognization of the statement() production rules.
     *
     * @result The test fails if a known valid statement is recognized as invalid.
     * The "programTest()" method tests statement rules more thoroughly.
     */
    @Test
    public void statementTest() {
        System.out.println("Testing statement...");
        boolean result = true;
        String input = "foo := 1";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.statement();
        } catch (Exception e){
            System.out.println("test failed. A known valid statement was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }

    /**
     * Tests parser recognization of the simple_expression() production rules.
     *
     * @result The test fails if a known valid expression is recognized as invalid.
     */
    @Test
    public void expressionTest() {
        System.out.println("Testing expression...");
        boolean result = true;
        String input = "foo - fee";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.simple_expression();
        } catch (Exception e){
            System.out.println("test failed. A known valid expression was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }

    /**
     * Tests parser recognization of the factor() production rules.
     *
     * @result The test fails if a known valid factor is recognized as invalid.
     */
    @Test
    public void factorTest() {
        System.out.println("Testing factor...");
        boolean result = true;
        String input = "foo [2 - 2]";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.factor();
        } catch (Exception e){
            System.out.println("test failed. A known valid factor was parsed as invalid.");
            result = false;
        }
        Assertions.assertEquals(true, result);
        System.out.println("Success!");
    }
}
