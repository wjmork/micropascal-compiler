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
    public void testExp() {
        System.out.println("exp");
        Recognizer instance = new Recognizer("pascal/simplest.pas", true);
        instance.exp();
        System.out.println("It Parsed!");
    }

    @Test
    public void testExp2() {
        System.out.println("exp");
        Recognizer instance = new Recognizer("pascal/simple.pas", true);
        instance.exp();
        System.out.println("It Parsed!");
    }

    @Test
    public void testExp_prime() {
        System.out.println("test exp_prime");
        Recognizer instance = new Recognizer("+ 34", false);
        instance.exp_prime();
    }

    @Test
    public void testTerm() {
        System.out.println("test term");
        Recognizer instance = new Recognizer("23 / 17", false);
        instance.term();
        System.out.println("Parsed a term");
    }

    @Test
    public void testFactor() {
        System.out.println("factor");
        Recognizer instance = new Recognizer("54321", false);
        instance.factor();
    }
}
