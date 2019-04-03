package syntaxtree;

import java.util.ArrayList;

/**
 * Represents a set of declarations in a Pascal program.
 * @author Erik Steinmetz
 */
public class DeclarationsNode extends SyntaxTreeNode {
    
    private ArrayList<VariableNode> variables = new ArrayList<VariableNode>();
    
    /**
     * Adds a variable to this declaration.
     * @param variable The variable node to add to this declaration.
     */
    public void addVariable(VariableNode variable) {
        variables.add(variable);
    }

    /**
     * Adds declarations to this declaration.
     * @param declarations The declarations to add to this declaration.
     */
    public void addDeclarations(DeclarationsNode declarations) {
        variables.addAll(declarations.variables);
    }
    
    /**
     * Creates a String representation of this declarations node and its children.
     * @param level The tree level at which this node resides.
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
