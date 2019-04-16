package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.*;
import syntaxtree.*;

import java.io.*;
import java.util.ArrayList;

/**
 * The parser class uses an instance of the scanner class to
 * check a mini-pascal file or input stream against the
 * known production rules of the pascal grammar. Each valid identifier
 * is added to a symbol table.
 * <p>
 * To use a parser, create an instance pointing at a file or
 * input stream and call the top-level function, <code>program()</code>.
 * If the function returns without an error, the file
 * contains an acceptable expression.
 * </p>
 *
 * @author William Mork
 */
public class Parser {

    /** The next token in the input stream. */
    private Token lookahead;

    /** Scanner, reads input stream. */
    private Scanner inputStreamScanner;

    /** Symbol table, stores unique identifiers and related information. */
    private SymbolTable symbolTable;

    /**
     * Creates a Parser.
     * @param input The input stream (String or file path) to be parsed.
     * @param importFile If true, input should be the path of a file. If false, a String should be provided.
     */
    public Parser(String input, boolean importFile) {
        // Create input stream reader in the case of a String
        InputStreamReader inputStreamReader;

        // Create symbol table
        symbolTable = new SymbolTable();

        if (importFile) {
            FileInputStream inputStream = null;
            symbolTable.fileName = input;
            try {
                inputStream = new FileInputStream(input);
            } catch (FileNotFoundException ex) {
                error("ERROR: File " + input + " failed to import. Please confirm file name and directory.");
            }
            inputStreamReader = new InputStreamReader(inputStream);
            inputStreamScanner = new Scanner(inputStreamReader);
        } else {
            inputStreamScanner = new Scanner(new StringReader(input));
        }
        try {
            lookahead = inputStreamScanner.nextToken();
        } catch (IOException ex) {
            error("ERROR: Start token not recognized in input stream.");
        }
    }

    /**
     * Executes the rule for program in the expression grammar; adds the
     * program identifier to the symbol table and creates a ProgramNode within the syntax tree.
     *
     * Production Rules:
     * RULE a.a:    program → program ID
     * RULE a.b:    program → declarations
     * RULE a.c:    program → subprogram_declarations
     * RULE a.d:    program → compound_statement
     * @return The main, top-level ProgramNode containing all other nodes.
     */
    public ProgramNode program() {
        match(TokenType.PROGRAM);
        String identifier = lookahead.getLexeme();
        ProgramNode program = new ProgramNode(identifier);
        if (symbolTable.isProgram(identifier)) {
            error("PROGRAM with lexeme " + identifier + " already exists in symbol table.");
        }
        symbolTable.addProgram(identifier);
        match(TokenType.ID);
        match(TokenType.SEMI);
        program.setVariables(declarations());
        program.setFunctions(subprogram_declarations());
        program.setMain(compound_statement());
        match(TokenType.PERIOD);
        return program;
    }

    /**
     * Executes the rule for identifier_list in the expression grammar.
     * Creates an ArrayList of identifiers to be used by a parent function,
     * then adds it to the symbol table.
     *
     * Production Rules:
     * RULE b.a:    identifier_list → ID
     * RULE b.b:    identifier_list → ID , identifier_list
     * @return An ArrayList containing declared identifiers.
     */
    public ArrayList<String> identifier_list() {
        ArrayList<String> identifierList = new ArrayList<>();
        identifierList.add(this.lookahead.getLexeme());
        match(TokenType.ID);
        if (this.lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            identifierList.addAll(identifier_list());
        }
        return identifierList;
    }

    /**
     * Executes the rule for declarations in the expression grammar; adds the
     * declarations identifier to the symbol table and creates a DeclarationsNode within the syntax tree.
     *
     * Production Rules:
     * RULE c.a:    declarations → VAR identifier_list : type ; declarations
     * RULE c.b:    declarations → λ
     * @return A DeclarationsNode containing declared variables.
     */
    public DeclarationsNode declarations() {
        DeclarationsNode declarations = new DeclarationsNode();
        if (this.lookahead.getType() == TokenType.VAR) {
            match(TokenType.VAR);
            ArrayList<String> identifierList = identifier_list();
            for (String identifier : identifierList) {
                declarations.addVariable(new VariableNode(identifier));
            }
            match(TokenType.COLON);
            type(identifierList);
            match(TokenType.SEMI);
            declarations.addDeclarations(declarations());
        }
        // lambda case
        return declarations;
    }

    /**
     * Executes the rule for type in the expression grammar.
     *
     * Production Rules:
     * RULE d.a:    type → standard_type
     * RULE d.b:    type → ARRAY [ NUM : NUM ] of standard_type
     * @param identifierList An ArrayList of identifiers which will be
     * added to the symbol table.
     * @return An instance of standard_type.
     */
    public TokenType type(ArrayList<String> identifierList) {
        TokenType tokenType = null;
        int startIndex, stopIndex;
        if (this.lookahead.getType() == TokenType.ARRAY) {
            match(TokenType.ARRAY);
            match(TokenType.LBRACE);
            startIndex = Integer.parseInt(lookahead.getLexeme());
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            stopIndex = Integer.parseInt(lookahead.getLexeme());
            match(TokenType.NUMBER);
            match(TokenType.RBRACE);
            match(TokenType.OF);
            tokenType = standard_type();
            for (String identifier : identifierList) {
                if (symbolTable.isArray(identifier)){
                    error("ARRAY with lexeme " + identifier + " already exists in symbol table.");
                } else {
                    symbolTable.addArray(identifier, tokenType, startIndex, stopIndex);
                }
            }
        } else if (this.lookahead.getType() == TokenType.INTEGER || this.lookahead.getType() == TokenType.REAL) {
            tokenType = standard_type();
            for (String identifier : identifierList) {
                if (symbolTable.isVariable(identifier)){
                    error("VARIABLE with lexeme " + identifier + " already exists in symbol table.");
                } else {
                    symbolTable.addVariable(identifier, tokenType);
                }
            }
        } else {
            error("TYPE: TokenType ARRAY not matched or STANDARD_TYPE not called.");
        }
        return tokenType;
    }

    /**
     * Executes the rule for standard_type in the expression grammar.
     *
     * Production Rules:
     * RULE e.a:    standard_type → INTEGER
     * RULE e.b:    standard_type → REAL
     * @return A token type, either INTEGER or REAL.
     */
    public TokenType standard_type() {
        if (this.lookahead.getType() == TokenType.INTEGER) {
            match(TokenType.INTEGER);
            return TokenType.INTEGER;
        } else if (this.lookahead.getType() == TokenType.REAL) {
            match(TokenType.REAL);
            return TokenType.REAL;
        } else {
            error("STANDARD_TYPE: TokenType INTEGER or REAL not matched.");
            return null;
        }
    }

    /**
     * Executes the rule for subprogram_declarations in the expression grammar; adds
     * the subprogram_declarations identifier to the symbol table and creates a
     * DeclarationsNode within the syntax tree.
     *
     * Production Rules:
     * RULE f.a:    subprogram_declarations → subprogram_declaration ; subprogram_declarations
     * RULE f.b:    subprogram_declarations → λ
     * @return A SubProgramDeclarationsNode containing declared functions and procedures.
     */
    public SubProgramDeclarationsNode subprogram_declarations() {
        SubProgramDeclarationsNode subProgramDeclarationsNode = new SubProgramDeclarationsNode();
        if (this.lookahead.getType() == TokenType.FUNCTION || this.lookahead.getType() == TokenType.PROCEDURE) {
            subProgramDeclarationsNode.addSubProgramDeclaration(subprogram_declaration());
            if (this.lookahead.getType() == TokenType.SEMI) {
                match(TokenType.SEMI);
                subProgramDeclarationsNode.addSubProgramDeclarations(subprogram_declarations().getProcs());
            }
            // lambda case
        }
        return subProgramDeclarationsNode;
    }

    /**
     * Executes the rule for subprogram_declaration in the
     * expression grammar.
     *
     * Production Rules:
     * RULE g.a:    subprogram_declaration → subprogram_head declarations compound_statement
     * @return A SubProgramNode for a declared function or procedure.
     */
    public SubProgramNode subprogram_declaration() {
        SubProgramNode subProgramNode = subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
        return subProgramNode;
    }

    /**
     * Executes the rule for subprogram_head in the expression
     * grammar.
     *
     * Production Rules:
     * RULE h.a:    subprogram_head → function ID arguments : standard_type ;
     * RULE h.b:    subprogram_head → procedure ID arguments ;
     * @return A SubProgramNode for a declared function or procedure.
     */
    public SubProgramNode subprogram_head() {
        SubProgramNode subProgramNode = null;
        if (this.lookahead.getType() == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            String functionIdentifier = this.lookahead.getLexeme();
            subProgramNode = new SubProgramNode(functionIdentifier);
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            TokenType tokenType = standard_type();
            symbolTable.addFunction(functionIdentifier, tokenType);
            match(TokenType.SEMI);
        } else if (this.lookahead.getType() == TokenType.PROCEDURE) {
            match(TokenType.PROCEDURE);
            String procedureIdentifier = this.lookahead.getLexeme();
            subProgramNode = new SubProgramNode(procedureIdentifier);
            match(TokenType.ID);
            arguments();
            symbolTable.addProcedure(procedureIdentifier);
            match(TokenType.SEMI);
        } else {
            error("SUBPROGRAM_HEAD: TokenType FUNCTION or PROCEDURE not matched.");
        }
        return subProgramNode;
    }

    /**
     * Executes the rule for arguments in the expression grammar.
     *
     * Production Rules:
     * RULE i.a:    arguments → ( parameter_list )
     * RULE i.b:    arguments → λ
     * @return An ArrayList of arguments declared in a VariableNode.
     */
    public ArrayList<VariableNode> arguments() {
        ArrayList<VariableNode> args = new ArrayList<>();
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            parameter_list();
            match(TokenType.RPAREN);
        }
        // lambda case
        return args;
    }

    /**
     * Executes the rule for parameter_list in the expression grammar.
     *
     * Production Rules:
     * RULE j.a:    parameter_list → identifier_list : type
     * RULE j.b:    parameter_list → identifier_list : type ; parameter_list
     * @return The arguments declared in a parameter list.
     */
    public ArrayList<VariableNode> parameter_list() {
        ArrayList<String> identifierList = identifier_list();
        ArrayList<VariableNode> args = new ArrayList<>();
        if (this.lookahead.getType() == TokenType.COLON) {
            match(TokenType.COLON);
            TokenType tokenType = type(identifierList);
            for (String identifier : identifierList) {
                args.add(new VariableNode(identifier, tokenType));
            }
            if (this.lookahead.getType() == TokenType.SEMI) {
                match(TokenType.SEMI);
                args.addAll(parameter_list());
            }
        }
        return args;
    }

    /**
     * Executes the rule for compound_statement in the expression
     * grammar.
     *
     * Production Rules:
     * RULE k.a:    compound_statement → BEGIN optional_statements END
     * @return A CompoundStatementNode.
     */
    public CompoundStatementNode compound_statement() {
        CompoundStatementNode compoundStatementNode = new CompoundStatementNode();
        match(TokenType.BEGIN);
        compoundStatementNode = optional_statements();
        match(TokenType.END);
        return compoundStatementNode;
    }

    /**
     * Executes the rule for optional_statement in the expression grammar.
     *
     * Production Rules:
     * RULE l.a:    optional_statements → statement_list
     * RULE l.b:    optional_statements → λ
     * @return A CompoundStatementNode.
     */
    public CompoundStatementNode optional_statements() {
        CompoundStatementNode compoundStatementNode = new CompoundStatementNode();
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.BEGIN ||
                this.lookahead.getType() == TokenType.IF ||
                this.lookahead.getType() == TokenType.WHILE) {
            compoundStatementNode.addStatement(statement_list());
        }
        // lambda case
        return compoundStatementNode;
    }

    /**
     * Executes the rule for statement_list in the expression grammar.
     *
     * Production Rules:
     * RULE m.a:    statement_list → statement
     * RULE m.b:    statement_list → statement ; statement_list
     * @return An ArrayList of StatementNodes.
     */
    public ArrayList<StatementNode> statement_list() {
        ArrayList<StatementNode> statementNodeList = new ArrayList<>();
        statementNodeList.add(statement());
        if (this.lookahead.getType() == TokenType.SEMI) {
            match(TokenType.SEMI);
            statementNodeList.addAll(statement_list());
        }
        // lambda case
        return statementNodeList;
    }

    /**
     * Executes the rule for statement in the expression grammar.
     *
     * Production Rules:
     * RULE n.a:    statement → variable assignop expression
     * RULE n.b:    statement → procedure_statement
     * RULE n.c:    statement → compound_statement
     * RULE n.d:    statement → IF expression THEN statement ELSE statement
     * RULE n.e:    statement → WHILE expression DO statement
     * RULE n.f:    statement → READ ( ID )
     * RULE n.g:    statement → WRITE ( expression )
     * RULE n.h:    statement → RETURN expression
     * @return A StatementNode.
     */
    public StatementNode statement() {
        StatementNode statementNode = null;
        if (this.lookahead.getType() == TokenType.ID) {
            if (symbolTable.isVariable(this.lookahead.getLexeme())) {
                AssignmentStatementNode assignmentStatementNode = new AssignmentStatementNode();
                assignmentStatementNode.setLvalue(variable());
                match(TokenType.ASSIGN);
                assignmentStatementNode.setExpression(expression());
                return assignmentStatementNode;
            } else if (symbolTable.isProcedure(this.lookahead.getLexeme())) {
                procedure_statement();
            } else {
                error("STATEMENT: Variable or Procedure identifier does not exist in symbol table.");
            }
        } else if (this.lookahead.getType() == TokenType.BEGIN) {
            statementNode = compound_statement();
        } else if (this.lookahead.getType() == TokenType.IF) {
            IfStatementNode ifStatementNode = new IfStatementNode();
            match(TokenType.IF);
            ifStatementNode.setTest(expression());
            match(TokenType.THEN);
            ifStatementNode.setThenStatement(statement());
            match(TokenType.ELSE);
            ifStatementNode.setElseStatement(statement());
            return ifStatementNode;
        // Needs while statement node.
        } else if (this.lookahead.getType() == TokenType.WHILE) {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        } else if (this.lookahead.getType() == TokenType.READ) {
            match(TokenType.READ);
            match(TokenType.LPAREN);
            match(TokenType.ID);
            match(TokenType.RPAREN);
        } else if (this.lookahead.getType() == TokenType.WRITE) {
            match(TokenType.WRITE);
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        } else if (this.lookahead.getType() == TokenType.RETURN) {
            match(TokenType.RETURN);
            expression();
        } else {
            error("STATEMENT: Error recognizing statement.");
        }
        return statementNode;
    }

    /**
     * Executes the rule for the variable non-terminal symbol in
     * the expression grammar.
     *
     * Production Rules:
     * RULE o.a:    variable → ID
     * RULE o.b:    variable → ID [ expression ]
     * @return A VariableNode.
     */
    public VariableNode variable() {
        String variableName = this.lookahead.getLexeme();
        if (!symbolTable.isVariable(variableName)) {
            error("Variable with ID " + variableName + " was not properly declared.");
        }
        if (!symbolTable.isArray(variableName)) {
            VariableNode variableNode = new VariableNode(variableName);
            match(TokenType.ID);
            return variableNode;
        } else {
            VariableNode variableNode = new VariableNode(variableName);
            if (this.lookahead.getType() == TokenType.LBRACE) {
                match(TokenType.LBRACE);
                expression();
                match(TokenType.RBRACE);
            }
            return variableNode;
        }
    }

    /**
     * Executes the rule for procedure_statement in the expression
     * grammar.
     *
     * Production Rules:
     * RULE p.a:    procedure_statement → ID
     * RULE p.b:    procedure_statement → ID ( expression_list )
     * @return A ProcedureStatementNode.
     */
    public ProcedureStatementNode procedure_statement() {
        ProcedureStatementNode procedureStatementNode = null;
        match(TokenType.ID);
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            expression_list();
            match(TokenType.RPAREN);
        }
        return procedureStatementNode;
    }

    /**
     * Executes the rule for expression_list in the expression grammar.
     *
     * Production Rules:
     * RULE q.a:    expression_list → expression
     * RULE q.b:    expression_list → expression , expression_list
     * @return An ExpressionNode.
     */
    public ArrayList<ExpressionNode> expression_list() {
        ArrayList<ExpressionNode> expressionNodeList = new ArrayList<>();
        expressionNodeList.add(expression());
        if (this.lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expressionNodeList.addAll(expression_list());
        }
        return expressionNodeList;
    }

    /**
     * Executes the rule for expression in the expression grammar.
     *
     * Production Rules:
     * RULE r.a:    expression → simple_expression
     * RULE r.b:    expression → simple_expression relop simple_expression
     * @return An ExpressionNode.
     */
    public ExpressionNode expression() {
        ExpressionNode leftExpressionNode = simple_expression();
        if (isRelOp(this.lookahead.getType())) {
            OperationNode operationNode = new OperationNode(this.lookahead.getType());
            operationNode.setLeft(leftExpressionNode);
            match(this.lookahead.getType());
            operationNode.setLeft(simple_expression());
        }
        return leftExpressionNode;
    }

    /**
     * Executes the rule for simple_expression in the expression
     * grammar.
     *
     * Production Rules:
     * RULE s.a:    simple_expression → term simple_prime
     * RULE s.b:    simple_expression → sign term simple_prime
     * @return An ExpressionNode.
     */
    public ExpressionNode simple_expression() {
        ExpressionNode expressionNode = null;
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT) {
            expressionNode = term();
            expressionNode = simple_prime(expressionNode);
        } else if (sign()) {
            expressionNode = term();
            expressionNode = simple_prime(expressionNode);
        } else {
            error("SIMPLE_EXPRESSION: TERM or SIGN can not be called.");
        }
        return expressionNode;
    }

    /**
     * Executes the rule for simple_prime in the expression grammar.
     *
     * Production Rules:
     * RULE t.a:    simple_prime → addop term simple_prime
     * RULE t.b:    simple_prime → λ
     * @return An ExpressionNode.
     */
    public ExpressionNode simple_prime(ExpressionNode leftExpressionNode) {
        if (isAddOp(this.lookahead.getType())) {
            OperationNode operationNode = new OperationNode(this.lookahead.getType());
            match(lookahead.getType());
            ExpressionNode rightExpressionNode = term();
            operationNode.setLeft(leftExpressionNode);
            operationNode.setRight(rightExpressionNode);
            return simple_prime(operationNode);
        }
        // lambda case
        return leftExpressionNode;
    }

    /**
     * Executes the rule for term in the expression grammar.
     *
     * Production Rules:
     * RULE u.a:    term → factor term_prime
     * @return An ExpressionNode.
     */
    public ExpressionNode term() {
        ExpressionNode leftNode = factor();
        return term_prime(leftNode);
    }

    /**
     * Executes the rule for term_prime in the expression grammar.
     *
     * Production Rules:
     * RULE v.a:    term_prime → mulop factor term_prime
     * RULE v.b:    term_prime → λ
     */
    public ExpressionNode term_prime(ExpressionNode expressionNode) {
        if (isMulOp(this.lookahead.getType())) {
            OperationNode operationNode = new OperationNode(this.lookahead.getType());
            match(this.lookahead.getType());
            ExpressionNode rightNode = factor();
            operationNode.setLeft(expressionNode);
            operationNode.setRight(term_prime(rightNode));
            return operationNode;
        }
        // lambda case
        return expressionNode;
    }

    /**
     * Executes the rule for sign in the expression grammar.
     * Because sign() is only called by the parent function
     * simple_expression, the lookahead token can both be checked
     * and matched in the same function.
     *
     * Production Rules:
     * RULE w.a:    sign → +
     * RULE w.b:    sign → -
     * @return true if lookahead token is a sign and is properly matched.
     */
    // Unary operator node?
    public boolean sign() {
        if (this.lookahead.getType() == TokenType.PLUS) {
            match(TokenType.PLUS);
            return true;
        } else if (this.lookahead.getType() == TokenType.MINUS) {
            match(TokenType.MINUS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Executes the rule for factor in the expression grammar.
     *
     * Production Rules:
     * RULE x.a:    factor → ID
     * RULE x.b:    factor → ID [ expression ]
     * RULE x.c:    factor → ID ( expression_list )
     * RULE x.d:    factor → num
     * RULE x.e:    factor → ( expression )
     * RULE x.f:    factor → not factor
     * @return An ExpressionNode.
     */
    public ExpressionNode factor() {
        ExpressionNode expressionNode = null;
        if (this.lookahead.getType() == TokenType.ID) {
            expressionNode = new VariableNode(this.lookahead.getLexeme());
            match(TokenType.ID);
            if (this.lookahead.getType() == TokenType.LBRACE) {
                match(TokenType.LBRACE);
                expression();
                match(TokenType.RBRACE);
            } else if (this.lookahead.getType() == TokenType.LPAREN) {
                match(TokenType.LPAREN);
                expression_list();
                match(TokenType.RPAREN);
            }
        } else if (this.lookahead.getType() == TokenType.NUMBER) {
            expressionNode = new ValueNode(this.lookahead.getLexeme());
            match(TokenType.NUMBER);
        } else if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            expressionNode = expression();
            match(TokenType.RPAREN);
        } else if (this.lookahead.getType() == TokenType.NOT) {
            match(TokenType.NOT);
            factor();
        } else {
            error("FACTOR: TokenType ID, NUMBER, LPAREN or NOT not matched.");
        }
        return expressionNode;
    }

    /**
     * Matches the expected token.
     * <p>
     * If the current token in the input stream from the scanner
     * matches the token that is expected, the current token is
     * consumed and the scanner will move on to the next token
     * in the input. The null pointer at the end of the scanned
     * file is replaced with a placeholder token containing no type.
     * </p>
     * @param expected The expected token type.
     */
    private void match(TokenType expected) {
        if (this.lookahead.getType() == expected) {
            System.out.println("expected: " + expected + ". look-ahead: " + this.lookahead.getType() + ".");
            try {
                this.lookahead = inputStreamScanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new Token("End of file.", null);
                }
            } catch (IOException ex) {
                error("scanner exception.");
            }
        } else {
            error("expected: " + expected + ". look-ahead: " + this.lookahead.getType() + ".");
        }
    }

    /**
     * Checks if a given token is a multiplication operator as defined in the grammar.
     * @param tokenType The type of the mulop token.
     * @return True if token is a multiplication operator.
     */
    private static boolean isMulOp(TokenType tokenType) {
        return (tokenType == TokenType.MULTIPLY ||
                tokenType == TokenType.DIVIDE ||
                tokenType == TokenType.DIV ||
                tokenType == TokenType.MOD ||
                tokenType == TokenType.AND);
    }

    /**
     * Checks if a given token is an addition operator as defined in the grammar.
     * @param tokenType The type of the addop token.
     * @return True if token is a addition operator.
     */
    private static boolean isAddOp(TokenType tokenType) {
        return (tokenType == TokenType.PLUS ||
                tokenType == TokenType.MINUS ||
                tokenType == TokenType.OR);
    }

    /**
     * Checks if a given token is an relational operator as defined in the grammar.
     * @param tokenType The type of the relop token.
     * @return True if token is a relational operator.
     */
    private static boolean isRelOp(TokenType tokenType) {
        return (tokenType == TokenType.EQUAL ||
                tokenType == TokenType.NOTEQ ||
                tokenType == TokenType.LTHAN ||
                tokenType == TokenType.LTHANEQ ||
                tokenType == TokenType.GTHANEQ ||
                tokenType == TokenType.GTHAN);
    }

    /**
     * Returns the symbol table constructed by the parser.
     * @return The symbol table.
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     * @param message The error message to print.
     */
    private void error(String message) {
        System.out.println("ERROR: " + message);
        System.exit(1);
    }
}