package scanner;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;

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
        File simplest = new File("src/pascal/simplest.pas");
        Reader simpleReader = new FileReader(simplest);
        System.out.println("Successfully imported simplest.pas test file.");

        Scanner testScanner = new Scanner(simpleReader);

        Token currentToken = null;

        do {
            try {
                currentToken = testScanner.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                fail("Invalid Token: " + currentToken);
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests scanning of the simple.pas pascal file.
     *
     * @result simple.pas will be scanned without throwing an exception.
     */
    @Test
    public void testSimple() throws IOException {
        File simple = new File("src/pascal/simple.pas");
        Reader simpleReader = new FileReader(simple);
        System.out.println("Successfully imported simple.pas test file.");

        Scanner testScanner = new Scanner(simpleReader);

        Token currentToken = null;

        do {
            try {
                currentToken = testScanner.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                fail("Invalid Token: " + currentToken);
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}