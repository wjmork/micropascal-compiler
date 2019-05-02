package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 *
 * @author William Mork
 */
public class ArrayNode extends VariableNode {

    public ExpressionNode expressionNode;

    /**
     * Creates an array node with a given name.
     * @param name The name of this array node.
     */
    public ArrayNode(String name) {
        super(name);
        expressionNode = null;
    }

    /**
     * Creates an arrayNode with a given name and token type.
     *
     * @param name The name of this array node
     * @param tokenType The token type of this array node
     */
    public ArrayNode(String name, TokenType tokenType) {
        super(name);
        expressionNode = null;
        this.tokenType = tokenType;
    }

    /**
     * Returns the expression node containing array parameters.
     *
     * @return This expression node containing array parameters.
     */
    public ExpressionNode getExpressionNode() {
        return this.expressionNode;
    }

    /**
     * Sets the expression node containing with the array parameters.
     *
     * @param expressionNode The expression node containing array parameters.
     */
    public void setExpressionNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

}