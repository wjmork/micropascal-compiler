package syntaxtree;

import scanner.TokenType;

/**
 * Syntax tree node which represents a general expression.
 *
 * @author William Mork
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

    // Data type of this expression node.
    public TokenType tokenType;

    // Default constructor with null token type.
    public ExpressionNode() {
        tokenType = null;
    }

    // Constructor for a specified token type.
    public ExpressionNode(TokenType tokenType) {
        tokenType = tokenType;
    }

    /**
     * Returns the token type of this node.
     *
     * @return Token type of this node. (INTEGER or REAL)
     */
    public TokenType getType() {
        return tokenType;
    }

    /**
     * Sets the token type of this node.
     *
     * @param tokenType Token type of this node. (INTEGER or REAL)
     */
    public void setType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
