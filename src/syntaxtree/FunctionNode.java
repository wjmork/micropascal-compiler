package syntaxtree;

import java.util.ArrayList;

/**
 * Syntax tree node which represents a function.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class FunctionNode extends VariableNode {

    /** Arraylist of Expression nodes which act as function arguments */
    private ArrayList<ExpressionNode> arguments;

    /**
     * Creates a FunctionNode as a child of a variable node.
     * @param name The name of this function node.
     */
    public FunctionNode(String name) {
        super(name);
        arguments = new ArrayList<>();
    }

    /**
     * Sets the ArrayList of function arguments.
     * @param arguments An ArrayList of arguments in the form of ExpressionNodes.
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
        answer += "Name: " + this.name + ", Type: " + this.tokenType + "\n";
        answer += this.indentation(level);
        answer += "Arguments: \n";
        for (ExpressionNode argument : arguments) {
            answer += (argument.indentedToString(level + 1));
        }
        return answer.toString();
    }
}