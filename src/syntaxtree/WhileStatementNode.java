package syntaxtree;

/**
 * Syntax tree node which represents a while statement.
 * A while statement includes a boolean expression and a body of instructions.
 *
 * @author William Mork
 */
public class WhileStatementNode extends StatementNode {

    /** The expression node for the if statement test. */
    private ExpressionNode test;

    /** The expression node for the do statement. */
    private StatementNode doStatement;

    /**
     * Returns the child expression node containing the while statement test.
     * @return The child expression node.
     */
    public ExpressionNode getTest() {
        return test;
    }

    /**
     * Sets the child expression node for the while statement test.
     */
    public void setTest(ExpressionNode test) {
        this.test = test;
    }

    /**
     * Returns the child statement node for the do statement.
     * @return The child do statement statement node.
     */
    public StatementNode getDoStatement() {
        return doStatement;
    }

    /**
     * Sets the child statement node for the do statement.
     * @param doStatement The do statement to be set.
     */
    public void setDoStatement(StatementNode doStatement) {
        this.doStatement = doStatement;
    }

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "While:\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.doStatement.indentedToString(level + 1);
        return answer;
    }
}
