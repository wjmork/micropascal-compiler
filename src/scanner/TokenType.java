package scanner;

/**
 * This class enumerates the Types, Keywords, and Symbols recognized as valid tokens by the scanner.
 *
 * @author William Mork
 */
public enum TokenType {

    // Types
    ID, NUMBER,

    // Keywords
    AND, ARRAY, BEGIN, DIV, DO, ELSE, END, FUNCTION, IF, INTEGER, MOD, NOT, OF, OR, PROCEDURE, PROGRAM, REAL, THEN, VAR, WHILE, READ, WRITE, RETURN,

    // Symbols
    SEMI, COMMA, PERIOD, COLON, LBRACE, RBRACE, LPAREN, RPAREN, PLUS, MINUS, EQUAL, NOTEQ, LTHAN, LTHANEQ, GTHAN, GTHANEQ, ASTERISK, FSLASH, ASSIGN

}
