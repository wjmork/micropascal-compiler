package syntaxtree;

import scanner.TokenType;

/**
 * Syntax tree node which represents an operation within an expression.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class OperationNode extends ExpressionNode {
    
    /** The left operator of the operation. */
    private ExpressionNode left;
    
    /** The right operator of the operation. */
    private ExpressionNode right;
    
    /** The type of operation being handled. */
    private TokenType operation;
    
    /**
     * Creates an operation node given an operation token.
     * @param operation The token representing this node's math operation.
     */
    public OperationNode (TokenType operation) {
        this.operation = operation;
    }

    /**
     * Returns the child left operator expression node.
     * @return left child expression node.
     */
    public ExpressionNode getLeft() {
        return(this.left);
    }

    /**
     * Returns the child right operator expression node.
     * @return right child expression node.
     */
    public ExpressionNode getRight() {
        return(this.right);
    }

    /**
     * Returns the operation type.
     * @return The operation token type.
     */
    public TokenType getOperation() {
        return(this.operation);
    }

    /**
     * Sets the left child expression node.
     * @param node The expression node to be set.
     */
    public void setLeft(ExpressionNode node) {
        // If we already have a left, remove it from our child list.
        this.left = node;
    }

    /**
     * Sets the right child expression node.
     * @param node The expression node to be set.
     */
    public void setRight(ExpressionNode node) {
        // If we already have a right, remove it from our child list.
        this.right = node;
    }

    /**
     * Sets the operation type.
     * @param operation The operation token type to be set.
     */
    public void setOperation(TokenType operation) {
        this.operation = operation;
    }
    
    /**
     * Returns the operation token as a String.
     * @return The String version of the operation token.
     */
    @Override
    public String toString() {
        return operation.toString();
    }
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Operation: " + this.operation + "\n";
        answer += left.indentedToString(level + 1);
        answer += right.indentedToString(level + 1);
        return(answer);
    }
}
