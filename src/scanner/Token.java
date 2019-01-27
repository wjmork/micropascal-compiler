/* William  Mork */
/* CSC 450 */
/* Token.java */

package scanner;

public class Token {

    public String lexeme;
    public TokenType type;

    public Token(String l, TokenType t) {
        this.lexeme = l;
        this.type = t;
    }

    // Returns the lexeme of a Token.
    public String getLexeme() {
        return this.lexeme;
    }

    // Returns a Token's type.
    public TokenType getType() {
        return this.type;
    }

    @Override
    // Token toString.
    public String toString() {
        return "Token Type: " + this.type + ", Lexeme: " + this.lexeme;
    }
}