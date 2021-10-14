package syntaxtree;

import java.util.ArrayList;

/**
 * Syntax tree node which represents a set of declarations.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {

    /** Array of child variable nodes. */
    private ArrayList<VariableNode> variables = new ArrayList<VariableNode>();
    
    /**
     * Adds a variable node to the array of variable nodes.
     * @param variable The variable node to add.
     */
    public void addVariable(VariableNode variable) {
        variables.add(variable);
    }

    /**
     * Adds an array of variables within declarations node to the array of variable nodes.
     * @param declarations The declarations node containing the variables to add.
     */
    public void addDeclarations(DeclarationsNode declarations) {
        variables.addAll(declarations.variables);
    }

    /**
     * Returns
     * @return
     */
    public ArrayList<VariableNode> getVariables() {
        return variables;
    }

    /**
     * Creates a String representation of this declarations node and its children.
     * @param level The level at which this node resides within the syntax tree.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Declarations\n";
        for(VariableNode variable : variables) {
            answer += variable.indentedToString(level + 1);
        }
        return answer;
    }
}
