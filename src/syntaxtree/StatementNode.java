package syntaxtree;

/**
 * Represents a single statement in Pascal.
 * @author Erik Steinmetz
 */
public abstract class StatementNode extends SyntaxTreeNode {

    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        return answer;
    }
}
