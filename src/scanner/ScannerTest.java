package scanner;

import org.junit.Test;

import java.io.*;

import static junit.framework.TestCase.fail;

public class ScannerTest {
    public ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void testSimple() {
        Reader simple = null;
        try {
            simple = new BufferedReader(new FileReader(new File(classLoader.getResource("pascal/simplest.pas").getFile())));
            System.out.println("Successfully imported simple.pas test file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Error reading simple.pas test file.");
        }

        Scanner testScanner = new Scanner(simple);
        Token currentToken = null;

        // as long as badToken is null, scanner is reading valid tokens.
        do {
            try {
                currentToken = testScanner.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                System.out.println("Invalid Token: " + currentToken);
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
    public void testSimplest() {
        Reader simplest = null;
        try {
            simplest = new BufferedReader(new FileReader(new File(classLoader.getResource("pascal/simplest.pas").getFile())));
            System.out.println("Successfully imported simplest.pas test file.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            fail("Error reading simplest.pas test file.");
        }

        Scanner testScanner2 = new Scanner(simplest);
        Token currentToken = null;

        // as long as badToken is null, scanner is reading valid tokens.
        do {
            try {
                currentToken = testScanner2.nextToken();
                if (currentToken != null) {
                    System.out.println("Token recognized: " + currentToken);
                }
            } catch (IOException e) {
                System.out.println("Invalid Token: " + currentToken);
                e.printStackTrace();
            }
        } while (currentToken != null);
        try {
            testScanner2.yyclose();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}