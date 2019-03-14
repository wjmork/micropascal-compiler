package scanner;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * This class contains JUnit testing for the scanner.
 *
 * @author William Mork
 */
public class ScannerTest {
    public ClassLoader classLoader = getClass().getClassLoader();

    /**
     * Tests scanning of the simplest.pas pascal file.
     *
     * @result simplest.pas will be scanned without throwing an exception.
     */
    @Test
    public void testSimplest() throws IOException {
        boolean result = true;
        System.out.println("Testing scanning of simple.pas test file...");
        File simplest = new File("src/pascal/simplest.pas");
        Reader simpleReader = new FileReader(simplest);
        System.out.println("File read success.");

        Scanner testScanner = new Scanner(simpleReader);

        Token currentToken = null;

        do {
            try {
                currentToken = testScanner.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                System.out.println("Invalid Token: " + currentToken);
                result = false;
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(true, result);
    }

    /**
     * Tests scanning of the simple.pas pascal file.
     *
     * @result simple.pas will be scanned without throwing an exception.
     */
    @Test
    public void testSimple() throws IOException {
        boolean result = true;
        System.out.println("Testing scanning of simple.pas test file...");
        File simple = new File("src/pascal/simple.pas");
        Reader simpleReader = new FileReader(simple);
        System.out.println("File read success.");

        Scanner testScanner = new Scanner(simpleReader);

        Token currentToken = null;

        do {
            try {
                currentToken = testScanner.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                System.out.println("Invalid Token: " + currentToken);
                result = false;
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(true, result);
    }
}