package syntaxtree;

/**
 * Represents a value or number in an expression.
 *
 * @author William Mork
 * @author Erik Steinmetz
 */
public class ValueNode extends ExpressionNode {
    
    /** The attribute associated with this value node. */
    String attribute;
    
    /**
     * Creates a value node. with the given attribute.
     * @param attr The attribute for this value node.
     */
    public ValueNode(String attr) {
        this.attribute = attr;
    }
    
    /** 
     * Returns the attribute of this value node.
     * @return The attribute of this value node.
     */
    public String getAttribute() {
        return(this.attribute);
    }
    
    /**
     * Returns the attribute as the description of this value node.
     * @return The attribute String of this value node.
     */
    @Override
    public String toString() {
        return(attribute);
    }
    
    /**
     * Creates a String representation of this value node.
     * @param level The tree level at which this node resides.
     * @return A String representing this node.
     */
    @Override
    public String indentedToString(int level) {
        String answer = this.indentation(level);
        answer += "Value: " + this.attribute + ", Type: " + this.tokenType + "\n";
        return answer;
    }
}
