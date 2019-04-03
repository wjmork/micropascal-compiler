package syntaxtree;

public class SubProgramNode extends SyntaxTreeNode {

    private String subProgramName;

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
