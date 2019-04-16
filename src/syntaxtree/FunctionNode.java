package syntaxtree;

import java.util.ArrayList;

/**
 * Syntax tree node which represents a function.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class FunctionNode extends VariableNode {

    // Function arguments
    private ArrayList<ExpressionNode> arguments;

    /**
     * Creates a FunctionNode and the parent variableNode with the given name.
     *
     * @param name The attribute for this value node.
     */
    public FunctionNode(String name) {
        super(name);
        arguments = new ArrayList<>();
    }

    /**
     * Sets the ArrayList of function arguments.
     *
     * @param arguments an ArrayList of arguments in the form of ExpressionNodes.
     */
    public void setArgs(ArrayList<ExpressionNode> arguments) {
        this.arguments = arguments;
    }

    /**
     * Creates a String representation of this function node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += ("Name: ");
        answer += (this.indentation(level));
        answer += ("Arguments: \n");
        for (ExpressionNode argument : arguments) {
            answer += (argument.indentedToString(level + 1));
        }
        return answer.toString();
    }
}