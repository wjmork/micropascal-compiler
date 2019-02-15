package scanner;

import java.util.HashMap;

/**
 * A lookup table to find the token types for symbols based on the String.
 * @author Erik Steinmetz
 */
public class LookupTable extends HashMap<String,TokenType> {
    
    /**
     * Creates a lookup table, loading all the token types.
     */
    public LookupTable() {
        this.put( ";", TokenType.SEMI);
        this.put( ",", TokenType.COMMA);
        this.put( ".", TokenType.PERIOD);
        this.put( ":", TokenType.COLON);
        this.put( "[", TokenType.LBRACE);
        this.put( "]", TokenType.RBRACE);
        this.put( "(", TokenType.LPAREN);
        this.put( ")", TokenType.RPAREN);
        this.put( "+", TokenType.PLUS);
        this.put( "-", TokenType.MINUS);
        this.put( "=", TokenType.EQUAL);
        this.put( "<>", TokenType.NOTEQ);
        this.put( "<", TokenType.LTHAN);
        this.put( "<=", TokenType.LTHANEQ);
        this.put( ">", TokenType.GTHAN);
        this.put( ">=", TokenType.GTHANEQ);
        this.put( "*", TokenType.ASTERISK);
        this.put( "/", TokenType.FSLASH);
        this.put( ":=", TokenType.ASSIGN);
    } 
}
