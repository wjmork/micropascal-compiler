package syntaxtree;

/**
 * Syntax tree node which represents an if statement.
 *
 * @author Erik Steinmetz
 * @author William Mork
 */
public class IfStatementNode extends StatementNode {

    /** The expression node for the if statement test. */
    private ExpressionNode test;

    /** The expression node for a then statement. */
    private StatementNode thenStatement;

    /** The expression node for an else statement. */
    private StatementNode elseStatement;

    /**
     * Returns the child expression node containing the if statement test.
     * @return The child expression node.
     */
    public ExpressionNode getTest() {
        return test;
    }

    /**
     * Sets the child expression node for the if statement test.
     * @param test The child expression node containing the if statement test.
     */
    public void setTest(ExpressionNode test) {
        this.test = test;
    }

    /**
     * Returns the child statement node for the then statement.
     * @return The child then statement statement node.
     */
    public StatementNode getThenStatement() {
        return thenStatement;
    }

    /**
     * Sets the child statement node for the else statement.
     * @param thenStatement The then statement to be set.
     */
    public void setThenStatement(StatementNode thenStatement) {
        this.thenStatement = thenStatement;
    }

    /**
     * Returns the child else statement.
     * @return The child else statement.
     */
    public StatementNode getElseStatement() {
        return elseStatement;
    }

    /**
     * Sets the child else statement.
     * @param elseStatement The else statement to be set.
     */
    public void setElseStatement(StatementNode elseStatement) {
        this.elseStatement = elseStatement;
    }
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "If:\n";
        answer += this.test.indentedToString(level + 1);
        answer += this.thenStatement.indentedToString(level + 1);
        answer += this.elseStatement.indentedToString(level + 1);
        return answer;
    }
}
