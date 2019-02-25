package parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

public class RecognizerTest {
    public RecognizerTest() {
    }

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Test
    public void programTest() {
        System.out.println("Testing program...");
        String testProgram = "src/pascal/simple.pas";
        Recognizer testRecognizer = new Recognizer(testProgram, true);
        try {
            testRecognizer.program();
        } catch (Exception e){
            fail("test failed.");
        }
        System.out.println("Success!");
    }

    @Test
    public void declarationsTest() {
        System.out.println("Testing declarations...");
        String input = "var foo: integer;";
        Recognizer testRecognizer = new Recognizer(input, false);
        testRecognizer.declarations();
        System.out.println("Success!");
    }

    @Test
    public void subProgramTest() {
        System.out.println("Testing subprogram_declaration...");
        String input = "function func(foo: integer): integer;";
        Recognizer testRecognizer = new Recognizer(input, false);
        testRecognizer.subprogram_declaration();
        System.out.println("Success!");
    }

    @Test
    public void statementTest() {
        System.out.println("Testing term...");
        String input = "foo := 1";
        Recognizer testRecognizer = new Recognizer(input, false);
        testRecognizer.statement();
        System.out.println("Success!");
    }

    @Test
    public void expressionTest() {
        String input = "foo - fee";
        System.out.println("Testing expression...");
        Recognizer testRecognizer = new Recognizer(input, false);
        testRecognizer.simple_expression();
        System.out.println("Success!");
    }
}
