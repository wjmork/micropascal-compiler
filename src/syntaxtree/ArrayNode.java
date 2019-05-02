package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 *
 * @author William Mork
 */
public class ArrayNode extends VariableNode {

    /** Expression node containing array information. */
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
     * @param name The name of this array node.
     * @param type The token type of this array node.
     */
    public ArrayNode(String name, TokenType type) {
        super(name);
        tokenType = type;
        expressionNode = null;
    }

    /**
     * Returns the expression node containing array parameters.
     * @return This expression node containing array parameters.
     */
    public ExpressionNode getExpressionNode() {
        return this.expressionNode;
    }

    /**
     * Sets the expression node containing array parameters.
     * @param expressionNode The expression node containing array parameters.
     */
    public void setExpressionNode(ExpressionNode expressionNode) {
        this.expressionNode = expressionNode;
    }

    /**
     * Creates a string representation of this array node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Array: " + super.getName() + ", Type: " + tokenType + "\n";
        if (expressionNode != null) {
            answer += this.expressionNode.indentedToString(level + 1);
        }
        return answer;
    }
}