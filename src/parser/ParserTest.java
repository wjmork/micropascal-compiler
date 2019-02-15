package parser;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParserTest {
    public ParserTest() {
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
        Parser instance = new Parser("pascal/simplest.pas", true);
        instance.exp();
        System.out.println("It Parsed!");
    }

    @Test
    public void testExp2() {
        System.out.println("exp");
        Parser instance = new Parser("pascal/simple.pas", true);
        instance.exp();
        System.out.println("It Parsed!");
    }

    @Test
    public void testExp_prime() {
        System.out.println("test exp_prime");
        Parser instance = new Parser("+ 34", false);
        instance.exp_prime();
    }

    @Test
    public void testTerm() {
        System.out.println("test term");
        Parser instance = new Parser("23 / 17", false);
        instance.term();
        System.out.println("Parsed a term");
    }

    @Test
    public void testFactor() {
        System.out.println("factor");
        Parser instance = new Parser("54321", false);
        instance.factor();
    }
}
