package scanner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExpScannerTest {
    public ExpScannerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testYytext() throws IOException {
        System.out.println("yytext");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("expressions/simplest.pas");
        } catch (FileNotFoundException var6) {
            ;
        }

        InputStreamReader isr = new InputStreamReader(fis);
        ExpScanner instance = new ExpScanner(isr);
        instance.nextToken();
        String expResult = "34";
        String result = instance.yytext();
        Assert.assertEquals(expResult, result);
        instance.nextToken();
        expResult = "+";
        result = instance.yytext();
        Assert.assertEquals(expResult, result);
        instance.nextToken();
        expResult = "17";
        result = instance.yytext();
        Assert.assertEquals(expResult, result);
        instance.nextToken();
        expResult = "*";
        result = instance.yytext();
        Assert.assertEquals(expResult, result);
        instance.nextToken();
        expResult = "7";
        result = instance.yytext();
        Assert.assertEquals(expResult, result);
    }

    @Test
    public void testNextToken() throws Exception {
        System.out.println("nextToken");
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("expressions/simplest.pas");
        } catch (FileNotFoundException var6) {
            ;
        }

        InputStreamReader isr = new InputStreamReader(fis);
        ExpScanner instance = new ExpScanner(isr);
        ExpTokenType expResult = ExpTokenType.NUMBER;
        ExpTokenType result = instance.nextToken().getType();
        Assert.assertEquals(expResult, result);
        expResult = ExpTokenType.PLUS;
        result = instance.nextToken().getType();
        Assert.assertEquals(expResult, result);
        expResult = ExpTokenType.NUMBER;
        result = instance.nextToken().getType();
        Assert.assertEquals(expResult, result);
        expResult = ExpTokenType.MULTIPLY;
        result = instance.nextToken().getType();
        Assert.assertEquals(expResult, result);
        expResult = ExpTokenType.NUMBER;
        result = instance.nextToken().getType();
        Assert.assertEquals(expResult, result);
    }
}
