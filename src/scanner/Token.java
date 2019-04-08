package scanner;

/**
 * The Token class defines a token object containing it's lexeme and type.
 *
 * @author William Mork
 */
public class Token {

    /** lexeme, or name reference of the token. */
    public String lexeme;

    /** type of the token. */
    public TokenType type;

    /**
     * Creates a token with the given lexeme and type.
     * @param lex The lexeme for this token.
     * @param type The type for this token.
     */
    public Token(String lex, TokenType type) {
        this.lexeme = lex;
        this.type = type;
    }

    /**
     * Returns the lexeme of a Token.
     * @return The lexeme of the token.
     */
    public String getLexeme() {
        return this.lexeme;
    }

    /**
     * Returns a Token's type.
     * @return The type of the token.
     */
    public TokenType getType() {
        return this.type;
    }

    /**
     * Creates the String representation of this token including
     * the lexeme and type.
     * @return The String representation of this token.
     */
    @Override
    public String toString() {
        return "Token Type: " + this.type + ", Lexeme: " + this.lexeme;
    }
}