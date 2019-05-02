package syntaxtree;

import java.util.ArrayList;

/**
 * Syntax tree node which represents a compound statement.
 * A compound statement is a block of zero or more
 * statements to be run sequentially.
 * @author William Mork
 * @author Erik Steinmetz
 */
public class CompoundStatementNode extends StatementNode {

    /** Array of child statement nodes. */
    public ArrayList<StatementNode> statements = new ArrayList<>();
    
    /**
     * Adds a child statement node to the array of statement nodes.
     * @param statement The statement node to add.
     */
    public void addStatement(StatementNode statement) {
        this.statements.add(statement);
    }

    /**
     * Adds an ArrayList of child statement nodes to the array of statement nodes.
     * @param statements The statement nodes to add.
     */
    public void addStatement(ArrayList<StatementNode> statements) {
        this.statements.addAll(statements);
    }

    /**
     * Returns the ArrayList of child statement nodes.
     * @return The ArrayList of child statement nodes.
     */
    public ArrayList<StatementNode> getStatements() {
        return statements;
    }
    
    /**
     * Creates a String representation of this compound statement node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Compound Statement\n";
        for(StatementNode statement : statements) {
            answer += statement.indentedToString(level + 1);
        }
        return answer;
    }
}
