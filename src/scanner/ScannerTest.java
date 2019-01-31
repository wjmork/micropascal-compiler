package scanner;

import org.junit.Test;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static junit.framework.TestCase.fail;

public class ScannerTest {
    public ClassLoader classLoader = getClass().getClassLoader();

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

    @Test
    public void testSimplest() throws IOException {
        File simplest = new File("src/pascal/simplest.pas");
        Reader simpleReader = new FileReader(simplest);
        System.out.println("Successfully imported simplest.pas test file.");

        Scanner testScanner2 = new Scanner(simpleReader);

        Token currentToken = null;

        do {
            try {
                currentToken = testScanner2.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                fail("Invalid Token: " + currentToken);
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner2.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStrings() {
        String testString = "Test, token, 6*10^23";
        Token currentToken = null;
        Scanner testScanner3 = new Scanner(testString);
        try {
            currentToken = testScanner3.nextToken();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Token recognized: " + currentToken);
    }
}