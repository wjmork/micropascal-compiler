package scanner;

import java.util.HashMap;

/**
 * A lookup table to find the token types for symbols based on the String.
 * @author Erik Steinmetz
 */
public class LookupTable extends HashMap<String,ExpTokenType> {
    
    /**
     * Creates a lookup table, loading all the token types.
     */
    public LookupTable() {
        this.put( "+", ExpTokenType.PLUS);
        this.put( "-", ExpTokenType.MINUS);
        this.put( "*", ExpTokenType.MULTIPLY);
        this.put( "/", ExpTokenType.DIVIDE);
        this.put( "(", ExpTokenType.LEFT_PAREN);
        this.put( ")", ExpTokenType.RIGHT_PAREN);
    } 
}
