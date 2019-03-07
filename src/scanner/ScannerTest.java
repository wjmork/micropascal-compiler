package scanner;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.Files.readAllLines;

public class ScannerTest {
    public ClassLoader classLoader = getClass().getClassLoader();
    public ScannerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

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