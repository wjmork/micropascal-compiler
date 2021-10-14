package syntaxtree;

import java.util.ArrayList;

/**
 * Syntax tree node which represents a collection of subprogram declarations.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class SubProgramDeclarationsNode extends SyntaxTreeNode {

    /** Array of child procedure subprogram nodes. */
    private ArrayList<SubProgramNode> procedures = new ArrayList<SubProgramNode>();

    /**
     * Adds a subprogram node to the array of subprogram nodes containing procedures.
     * @param subProgram The subprogram node to add.
     */
    public void addSubProgramDeclaration(SubProgramNode subProgram) {
        procedures.add(subProgram);
    }

    /**
     * Adds an array of subprogram nodes to the array of subprogram nodes containing procedures.
     * @param subProgram The array of subprogram nodes to add.
     */
    public void addSubProgramDeclarations(ArrayList<SubProgramNode> subProgram) {
        procedures.addAll(subProgram);
    }

    /**
     * Returns the array of child subprogram nodes
     * @return The array of procedures.
     */
    public ArrayList<SubProgramNode> getProcs() {
        return procedures;
    }
    
    /**
     * Creates a String representation of this node and its children.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "SubProgramDeclarations\n";
        for(SubProgramNode subProg : procedures) {
            answer += subProg.indentedToString(level + 1);
        }
        return answer;
    }
    
}
