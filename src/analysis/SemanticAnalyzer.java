package analysis;

import scanner.TokenType;
import syntaxtree.*;

public class SemanticAnalyzer {

    /** Root node of the tree to analyze. */
    private ProgramNode root;

    /**
     * Creates a SemanticAnalyzer with the given expression.
     * @param tree A program tree to analyze.
     */
    public SemanticAnalyzer( ProgramNode tree) {
        this.root = tree;
    }

    /**
     * Folds code for the given node.
     * We only fold if both children are value nodes, and the node itself
     * is a PLUS node.
     * @param node The node to check for possible efficiency improvements.
     * @return The folded node or the original node if nothing
     */
    public ExpressionNode codeFolding( OperationNode node) {
        if( node.getLeft() instanceof OperationNode) {
            node.setLeft( codeFolding( (OperationNode)node.getLeft()));
        }
        if( node.getRight() instanceof OperationNode) {
            node.setRight( codeFolding( (OperationNode)node.getRight()));
        }
        if( node.getLeft() instanceof ValueNode &&
                node.getRight() instanceof ValueNode &&
                node.getOperation() == ExpTokenType.PLUS) {
            int leftValue = Integer.parseInt(((ValueNode)node.getLeft()).getAttribute());
            int rightValue = Integer.parseInt(((ValueNode)node.getRight()).getAttribute());
            ValueNode vn = new ValueNode( "" + (leftValue + rightValue));
            return vn;
        }
        else {
            return node;
        }
    }
}
