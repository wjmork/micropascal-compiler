package parser;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RecognizerTest {
    public RecognizerTest() {
    }

    @Test
    public void programTest() {
        System.out.println("Testing program...");
        String testProgram = "src/pascal/simple.pas";
        Recognizer testRecognizer = new Recognizer(testProgram, true);
        try {
            testRecognizer.program();
        } catch (Exception e){
            fail("Test failed. A known valid program was parsed as invalid.");
        }
        System.out.println("Success.");
    }

    @Test
    public void declarationsTest() {
        System.out.println("Testing declarations...");
        String input = "var foo: integer;";
        String input2 = "(incoherent)";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.declarations();
        } catch (Exception e){
            fail("Test failed. A known valid declaration was parsed as invalid.");
        }

    }

    @Test
    public void subProgramTest() {
        System.out.println("Testing subprogram_declaration...");
        String input = "function func(foo: integer): integer;";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.subprogram_declaration();
        } catch (Exception e){
            fail("test failed.");
        }
        System.out.println("Success!");
    }

    @Test
    public void statementTest() {
        System.out.println("Testing term...");
        String input = "foo := 1";
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.statement();
        } catch (Exception e){
            fail("test failed.");
        }
        System.out.println("Success!");
    }

    @Test
    public void expressionTest() {
        String input = "foo - fee";
        System.out.println("Testing expression...");
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.simple_expression();
        } catch (Exception e){
            fail("test failed.");
        }
        System.out.println("Success!");
    }

    @Test
    public void factorTest() {
        String input = "foo [2 - 2]";
        System.out.println("Testing expression...");
        Recognizer testRecognizer = new Recognizer(input, false);
        try {
            testRecognizer.factor();
        } catch (Exception e){
            fail("test failed.");
        }
        System.out.println("Success!");
    }
}
