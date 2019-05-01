package syntaxtree;

import scanner.TokenType;

/**
 * Represents a variable in the syntax tree.
 *
 * @author William Mork
 */
public class ArrayNode extends VariableNode {

    public ArrayNode(String name) {
        super(name);
        ExpressionNode expressionNode = null;
    }

}