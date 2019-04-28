package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class VariableNode extends ExpressionNode {
    
    /** The name of the variable associated with this variable node. */
    String name;
    
    /**
     * Creates a value node with the given name.
     * @param name The name of this value node.
     */
    public VariableNode(String name) {
        this.name = name;
    }

    /**
     * Creates a value node with the given name and token type.
     * @param name The name for this value node.
     * @param tokenType The name for this value node.
     */
    public VariableNode(String name, TokenType tokenType) {
        super(tokenType);
        this.name = name;
    }

    /**
     * Returns the name of the variable of this node.
     * @return The name of this VariableNode.
     */
    public String getName() {
        return(this.name);
    }


    /**
     * Returns the name of the variable as the description of this node.
     * @return The attribute String of this node.
     */
    @Override
    public String toString() {
        return(name);
    }
    
    /**
     * Creates a String representation of this variable node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Name: " + this.name + ", Type: " + this.tokenType + "\n";
        return answer;
    }

    @Override
    public boolean equals(Object o) {
        boolean answer = false;
        if(o instanceof VariableNode) {
            VariableNode other = (VariableNode)o;
            if(this.name.equals(other.name)) answer = true;
        }
        return answer;
    }
}
