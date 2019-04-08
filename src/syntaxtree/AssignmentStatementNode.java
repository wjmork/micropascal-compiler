package syntaxtree;

/**
 * Syntax tree node which represents a single assignment statement.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class AssignmentStatementNode extends StatementNode {

    /** Variable node containing the left value of this assignment statement. */
    private VariableNode lvalue;

    /** Expression node of this assignment statement. */
    private ExpressionNode expression;

    /**
     * Returns the child variable node.
     * @return child variable node.
     */
    public VariableNode getLvalue() {
        return lvalue;
    }

    /**
     * Sets the child variable node.
     * @param lvalue The variable node to be set.
     */
    public void setLvalue(VariableNode lvalue) {
        this.lvalue = lvalue;
    }

    /**
     * Returns the child expression node.
     * @return child expression node.
     */
    public ExpressionNode getExpression() {
        return expression;
    }

    /**
     * Sets the child expression node.
     * @param expression expression to be set.
     */
    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Assignment\n";
        answer += this.lvalue.indentedToString(level + 1);
        answer += this.expression.indentedToString(level + 1);
        return answer;
    }
}
