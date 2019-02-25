package parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
        String testProgram1 = "pascal/simplest.pas";
        String testProgram2 = "pascal/simplest.pas";
        Recognizer testRecognizer = new Recognizer(testProgram1, true);
        Recognizer testRecognizer2 = new Recognizer(testProgram1, true);
        testRecognizer.program();
        testRecognizer2.program();
        System.out.println("Success!");
    }

    @Test
    public void declarationsTest() {
        System.out.println("Testing declarations...");
        String input = "var foo: integer;";
        Recognizer testRecognizer3 = new Recognizer(input, false);
        testRecognizer3.declarations();
        System.out.println("Success!");
    }

    @Test
    public void subProgramTest() {
        System.out.println("Testing subprogram_declaration...");
        String input = "function func(foo: integer): integer;";
        Recognizer testRecognizer4 = new Recognizer(input, false);
        testRecognizer4.subprogram_declaration();
        System.out.println("Success!");
    }

    @Test
    public void statementTest() {
        System.out.println("Testing term...");
        String input = "foo := 1";
        Recognizer testRecognizer5 = new Recognizer(input, false);
        testRecognizer5.statement();
        System.out.println("Success!");
    }

    @Test
    public void expressionTest() {
        String input = "foo + fi";
        System.out.println("Testing expression...");
        Recognizer testRecognizer6 = new Recognizer(input, false);
        testRecognizer6.expression();
        System.out.println("Success!");
    }

    @Test
    public void factorTest() {
        String input = "foo < fee";
        System.out.println("Testing factor...");
        Recognizer testRecognizer7 = new Recognizer(input, false);
        testRecognizer7.factor();
    }
}
