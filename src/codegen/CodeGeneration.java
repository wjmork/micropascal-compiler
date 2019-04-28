package codegen;

import scanner.TokenType;
import symboltable.SymbolTable;
import syntaxtree.*;

public class CodeGeneration {

    private ProgramNode programNode;
    private SymbolTable symbolTable;

    public CodeGeneration(ProgramNode programNode, SymbolTable symbolTable){
        programNode = programNode;
        symbolTable = symbolTable;
    }

    public String codeWriter(){
        StringBuilder codeString = new StringBuilder();

        return codeString.toString();
    }

    public String operationWriter(OperationNode operationNode, String resultReg) {
        StringBuilder operationString = new StringBuilder();

        return operationString.toString();
    }

    public String valueWriter(ValueNode valueNode, String resultReg)
    {
        StringBuilder valueString = new StringBuilder();

        return valueString.toString();
    }

    public String expressionWriter(ExpressionNode expressionNode, String resultReg) {
        StringBuilder expressionString = new StringBuilder();

        return expressionString.toString();
    }

    public String statementWriter(StatementNode statementNode, String resultReg) {
        StringBuilder statementString = new StringBuilder();

        return statementString.toString();
    }

    private String writeAssignment(AssignmentStatementNode assignmentStatementNode, String resultReg) {
        StringBuilder assignmentString = new StringBuilder();
        return assignmentString.toString();
    }

    private String pushToStack() {
        StringBuilder pushString = new StringBuilder();

        for () {
        }

        return pushString.toString();
    }

    private String popFromStack() {
        StringBuilder popString = new StringBuilder();

        for () {
        }

        return popString.toString();
    }

}
