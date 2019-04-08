package syntaxtree;

/**
 * Syntax tree node which represents a subprogram.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class SubProgramNode extends SyntaxTreeNode {

    /** Name of the subprogram node. */
    private String subProgramName;

    /**
     * Sets the name of this subprogram node.
     * @param name The name to be set.
     */
    public SubProgramNode(String name) {
        this.subProgramName = name;
    }

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
