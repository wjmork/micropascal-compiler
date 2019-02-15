/* William  Mork */
/* CSC 451 */
/* Token.java */

package scanner;

public class Token {

    public String lexeme;
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

    // Returns the lexeme of a Token.
    public String getLexeme() {
        return this.lexeme;
    }

    // Returns a Token's type.
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