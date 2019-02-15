package scanner;

/**
 * Token for the expression grammar.
 * @author Erik Steinmetz
 */
public class ExpToken {
    
    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////
    
    private String lexeme;
    private ExpTokenType type;

    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
    
    /**
     * Creates a token with the given lexeme and type.
     * @param lex The lexeme for this token.
     * @param tokenType The type for this token.
     */
    public ExpToken( String lex, ExpTokenType tokenType) {
        this.lexeme = lex;
        this.type = tokenType;
    }
    
    ///////////////////////////////
    //     Getters and Setters
    ///////////////////////////////
    
    public String getLexeme() { return this.lexeme;}
    public ExpTokenType getType() { return this.type;}

    ///////////////////////////////
    //       Methods
    ///////////////////////////////
    
    /**
     * Creates the String representation of this token including
     * the lexeme and type.
     * @return The String representation of this token.
     */
    @Override
    public String toString() {
        return "Token: \"" + this.lexeme + "\" of type: " + this.type;
    }
}
