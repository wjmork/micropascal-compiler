package analysis;

import scanner.TokenType;
import syntaxtree.*;
import symboltable.*;

import java.util.ArrayList;

public class SemanticAnalyzer {

    /** Root node of the tree to analyze. */
    private ProgramNode root;
    public SymbolTable symbolTable;
    boolean isValidType = true;

    /**
     * Creates a SemanticAnalyzer with the given expression.
     * @param rootNode Root program node
     */
    public SemanticAnalyzer(ProgramNode rootNode, SymbolTable symbolTable) {
        root = rootNode;
        symbolTable = symbolTable;

        DeclarationsNode rootDeclarations = root.getDeclarations();

        SubProgramDeclarationsNode rootSubprogramDeclarations = root.getSubProgramDeclarations();

        testSemantics(root.getCompoundStatement());
    }

    public void testVariableDeclarations(DeclarationsNode rootDeclarations) {

    }

    public void testSemantics(CompoundStatementNode rootCompoundStatement)
    {
        boolean validAssignment = true;
        boolean validDeclarations = true;
        for (StatementNode statement : rootCompoundStatement.statements) {

            if (statement instanceof AssignmentStatementNode)
            {
                TokenType leftVarType = null;
                TokenType expressionType = null;

                AssignmentStatementNode thisAssignmentStatement = ((AssignmentStatementNode) statement);
                leftVarType = thisAssignmentStatement.getLvalue().getType();

                ExpressionNode thisExpression = ((AssignmentStatementNode) statement).getExpression();
                validAssignment = traverseOperation(thisExpression, leftVarType);
                }
            }

        if (!validAssignment){
            System.out.println("Compiler Error: Variable types not consistent across assignments");
        }
    }

    public boolean traverseOperation(ExpressionNode expressionNode, TokenType expectedType) {
        expectedType = expectedType;

        if (expressionNode instanceof OperationNode) {
            OperationNode thisOperation = ((OperationNode) expressionNode);
            ExpressionNode leftExpression = thisOperation.getLeft();
            traverseOperation(leftExpression, expectedType);
            ExpressionNode rightExpression = thisOperation.getRight();
            traverseOperation(rightExpression, expectedType);
        } else if (expressionNode.getType() != expectedType) {

            if (expressionNode instanceof VariableNode) {
                VariableNode thisVariable = ((VariableNode) expressionNode);
                if (symbolTable.contains(thisVariable.getName()))
                {

                }
            }

            isValidType = false;
        }

        return isValidType;
    }
}
