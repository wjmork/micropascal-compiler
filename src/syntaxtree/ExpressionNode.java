package syntaxtree;

import scanner.TokenType;

/**
 * Syntax tree node which represents a general expression.
 *
 * @author William Mork
 */
public abstract class ExpressionNode extends SyntaxTreeNode {

    /** The data type of this expression node. */
    protected TokenType tokenType;

    /**
     * Default constructor for an expression node.
     */
    public ExpressionNode() {
        tokenType = null;
    }

    /**
     * Contstructor for an expression node with a provided data type.
     * @param tokenType the token type of the expression node.
     */
    public ExpressionNode(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    /**
     * Returns the token type of this node.
     * @return The token type of this node. (INTEGER or REAL)
     */
    public TokenType getType() {
        return tokenType;
    }

    /**
     * Sets the token type of this node.
     * @param tokenType The token type of this node. (INTEGER or REAL)
     */
    public void setType(TokenType tokenType) {
        this.tokenType = tokenType;
    }
}
