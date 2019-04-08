package syntaxtree;

/**
 * Syntax tree node which represents a Micro-Pascal program.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class ProgramNode extends SyntaxTreeNode {

    /** Name of the program node. */
    private String nodeName;

    /** The child declarations node of this program node. */
    private DeclarationsNode variables;

    /** The child subprogram declarations node of this program node. */
    private SubProgramDeclarationsNode functions;

    /** The child compound statement node of this program node. */
    private CompoundStatementNode main;

    /**
     * Sets the name of this program node.
     * @param name The name to be set.
     */
    public ProgramNode(String name) {
        this.nodeName = name;
    }

    /**
     * Returns the child declarations node which contains program variables.
     * @return The child declarations node.
     */
    public DeclarationsNode getVariables() {
        return variables;
    }

    /**
     * Sets the declarations node containing program variables.
     * @param variables The declarations node to be set.
     */
    public void setVariables(DeclarationsNode variables) {
        this.variables = variables;
    }

    /**
     * Returns the child subprogram declarations node which contains program functions.
     * @return The child subprogram declarations node.
     */
    public SubProgramDeclarationsNode getFunctions() {
        return functions;
    }

    /**
     * Sets the subprogram declarations node containing program functions.
     * @param functions The subprogram declarations node to be set.
     */
    public void setFunctions(SubProgramDeclarationsNode functions) {
        this.functions = functions;
    }

    /**
     * Returns the child compound statement node which contains the program main.
     * @return The child compound statement node.
     */
    public CompoundStatementNode getMain() {
        return main;
    }

    /**
     * Sets the compound statement node containing the program main.
     * @param main The compound statement node to be set.
     */
    public void setMain(CompoundStatementNode main) {
        this.main = main;
    }
    
    /**
     * Creates a String representation of this program node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Program: " + nodeName + "\n";
        answer += variables.indentedToString(level + 1);
        answer += functions.indentedToString(level + 1);
        answer += main.indentedToString(level + 1);
        return answer;
    }
}
