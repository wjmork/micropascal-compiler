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
    private DeclarationsNode declarations;

    /** The child subprogram declarations node of this program node. */
    private SubProgramDeclarationsNode subProgramDeclarations;

    /** The child compound statement node of this program node. */
    private CompoundStatementNode compoundStatement;

    /**
     * Sets the name of this program node.
     * @param name The name to be set.
     */
    public ProgramNode(String name) {
        this.nodeName = name;
    }

    /**
     * Returns the child declarations node which contains program declarations.
     * @return The child declarations node.
     */
    public DeclarationsNode getDeclarations() {
        return declarations;
    }

    /**
     * Sets the declarations node containing program declarations.
     * @param declarations The declarations node to be set.
     */
    public void setDeclarations(DeclarationsNode declarations) {
        this.declarations = declarations;
    }

    /**
     * Returns the child subprogram declarations node which contains program subprogram declarations.
     * @return The child subprogram declarations node.
     */
    public SubProgramDeclarationsNode getSubProgramDeclarations() {
        return subProgramDeclarations;
    }

    /**
     * Sets the subprogram declarations node containing program subprogram declarations.
     * @param subProgramDeclarations The subprogram declarations node to be set.
     */
    public void setSubprogramDeclarations(SubProgramDeclarationsNode subProgramDeclarations) {
        this.subProgramDeclarations = subProgramDeclarations;
    }

    /**
     * Returns the child compound statement node which contains the program compound statement.
     * @return The child compound statement node.
     */
    public CompoundStatementNode getCompoundStatement() {
        return compoundStatement;
    }

    /**
     * Sets the compound statement node containing the program compound statement.
     * @param compoundStatement The compound statement node to be set.
     */
    public void setCompoundStatement(CompoundStatementNode compoundStatement) {
        this.compoundStatement = compoundStatement;
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
        answer += declarations.indentedToString(level + 1);
        answer += subProgramDeclarations.indentedToString(level + 1);
        answer += compoundStatement.indentedToString(level + 1);
        return answer;
    }
}
