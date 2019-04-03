package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;
import symboltable.SymbolTable;
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
        declarations();
        subprogram_declarations();
        compound_statement();
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
     */
    public ArrayList<String> identifier_list() {
        ArrayList<String> identifierList = new ArrayList();
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
            declarations();
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
     */
    public void type(ArrayList<String> identifierList) {
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
            TokenType tokenType = standard_type();
            for (String identifier : identifierList) {
                if (symbolTable.isArray(identifier)){
                    error("ARRAY with lexeme " + identifier + " already exists in symbol table.");
                } else {
                    symbolTable.addArray(identifier, tokenType, startIndex, stopIndex);
                }
            }
        } else if (this.lookahead.getType() == TokenType.INTEGER || this.lookahead.getType() == TokenType.REAL) {
            TokenType tokenType = standard_type();
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
    }

    /**
     * Executes the rule for standard_type in the expression grammar.
     *
     * Production Rules:
     * RULE e.a:    standard_type → INTEGER
     * RULE e.b:    standard_type → REAL
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
     */
    public SubProgramDeclarationsNode subprogram_declarations() {
        SubProgramDeclarationsNode subProgramDeclarationsNode = new SubProgramDeclarationsNode();
        if (this.lookahead.getType() == TokenType.FUNCTION || this.lookahead.getType() == TokenType.PROCEDURE) {
            subprogram_declaration();
            if (this.lookahead.getType() == TokenType.SEMI) {
                match(TokenType.SEMI);
                subprogram_declarations();
            }
            // lambda case
        }
        return subProgramDeclarationsNode;
    }

    /**
     * Executes the rule for subprogram_declaration in the
     * expression grammar.
     *
     * Production Rules::   subprogram_declaration → subprogram_head declarations compound_statement
     */
    public void subprogram_declaration() {
        subprogram_head();
        declarations();
        subprogram_declarations();
        compound_statement();
    }

    /**
     * Executes the rule for subprogram_head in the expression
     * grammar.
     *
     * Production Rules:
     * RULE g.a:    subprogram_head → function ID arguments : standard_type ;
     * RULE g.b:    subprogram_head → procedure ID arguments ;
     */
    public void subprogram_head() {
        if (this.lookahead.getType() == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            String functionIdentifier = this.lookahead.getLexeme();
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            TokenType tokenType = standard_type();
            symbolTable.addFunction(functionIdentifier, tokenType);
            match(TokenType.SEMI);
        } else if (this.lookahead.getType() == TokenType.PROCEDURE) {
            match(TokenType.PROCEDURE);
            String procedureIdentifier = this.lookahead.getLexeme();
            match(TokenType.ID);
            arguments();
            symbolTable.addProcedure(procedureIdentifier);
            match(TokenType.SEMI);
        } else {
            error("SUBPROGRAM_HEAD: TokenType FUNCTION or PROCEDURE not matched.");
        }
    }

    /**
     * Executes the rule for arguments in the expression grammar.
     *
     * Production Rules:
     * RULE h.a:    arguments → ( parameter_list )
     * RULE h.b:    arguments → λ
     */
    public void arguments() {
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            parameter_list();
            match(TokenType.RPAREN);
        }
        // lambda case
    }

    /**
     * Executes the rule for parameter_list in the expression grammar.
     *
     * Production Rules:
     * RULE i.a:    parameter_list → identifier_list : type
     * RULE i.b:    parameter_list → identifier_list : type ; parameter_list
     */
    public void parameter_list() {
        ArrayList<String> identifierList = identifier_list();
        if (this.lookahead.getType() == TokenType.COLON) {
            match(TokenType.COLON);
            type(identifierList);
            if (this.lookahead.getType() == TokenType.SEMI) {
                match(TokenType.SEMI);
                parameter_list();
            }
        }
    }

    /**
     * Executes the rule for compound_statement in the expression
     * grammar.
     *
     * Production Rules:
     * RULE j.a:    compound_statement → BEGIN optional_statements END
     */
    public CompoundStatementNode compound_statement() {
        CompoundStatementNode compoundStatementNode = new CompoundStatementNode();
        match(TokenType.BEGIN);
        match(TokenType.END);
        return compoundStatementNode;
    }

    /**
     * Executes the rule for optional_statement in the expression grammar.
     *
     * Production Rules:
     * RULE k.a:    optional_statements → statement_list
     * RULE k.b:    optional_statements → λ
     */
    public void optional_statements() {
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.BEGIN ||
                this.lookahead.getType() == TokenType.IF ||
                this.lookahead.getType() == TokenType.WHILE) {
            statement_list();
        }
        // lambda case
    }

    /**
     * Executes the rule for statement_list in the expression grammar.
     *
     * Production Rules:
     * RULE l.a:    statement_list → statement
     * RULE l.b:    statement_list → statement ; statement_list
     */
    public void statement_list() {
        statement();
        if (this.lookahead.getType() == TokenType.SEMI) {
            match(TokenType.SEMI);
            statement_list();
        }
        // lambda case
    }

    /**
     * Executes the rule for statement in the expression grammar.
     *
     * Production Rules:
     * RULE m.a:    statement → variable assignop expression
     * RULE m.b:    statement → procedure_statement
     * RULE m.c:    statement → compound_statement
     * RULE m.d:    statement → IF expression THEN statement ELSE statement
     * RULE m.e:    statement → WHILE expression DO statement
     * RULE m.f:    statement → READ ( ID )
     * RULE m.g:    statement → WRITE ( expression )
     * RULE m.h:    statement → RETURN expression
     */
    public void statement() {
        if (this.lookahead.getType() == TokenType.ID) {
            if (symbolTable.isVariable(this.lookahead.getLexeme())) {
                variable();
                match(TokenType.ASSIGN);
                expression();
            } else if (symbolTable.isProcedure(lookahead.getLexeme())) {
                procedure_statement();
            } else {
                error("STATEMENT: Variable or Procedure identifier does not exist in symbol table.");
            }
        } else if (this.lookahead.getType() == TokenType.BEGIN) {
            compound_statement();
        } else if (this.lookahead.getType() == TokenType.IF) {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
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
    }

    /**
     * Executes the rule for the variable non-terminal symbol in
     * the expression grammar.
     *
     * Production Rules:
     * RULE n.a:    variable → ID
     * RULE n.b:    variable → ID [ expression ]
     */
    public void variable() {
        match(TokenType.ID);
        if (this.lookahead.getType() == TokenType.LBRACE) {
            match(TokenType.LBRACE);
            expression();
            match(TokenType.RBRACE);
        }
    }

    /**
     * Executes the rule for procedure_statement in the expression
     * grammar.
     *
     * Production Rules:
     * RULE o.a:    procedure_statement → ID
     * RULE o.b:    procedure_statement → ID ( expression_list )
     */
    public void procedure_statement() {
        match(TokenType.ID);
        if (this.lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            expression_list();
            match(TokenType.RPAREN);
        }
    }

    /**
     * Executes the rule for expression_list in the expression grammar.
     *
     * Production Rules:
     * RULE p.a:    expression_list → expression
     * RULE p.b:    expression_list → expression , expression_list
     */
    public void expression_list() {
        expression();
        if (this.lookahead.getType() == TokenType.COMMA) {
            match(TokenType.COMMA);
            expression_list();
        }
    }

    /**
     * Executes the rule for expression in the expression grammar.
     *
     * Production Rules:
     * RULE q.a:    expression → simple_expression
     * RULE q.b:    expression → simple_expression relop simple_expression
     */
    public void expression() {
        simple_expression();
        if (isRelOp(this.lookahead.getType())) {
            match(this.lookahead.getType());
            simple_expression();
        }
    }

    /**
     * Executes the rule for simple_expression in the expression
     * grammar.
     *
     * Production Rules:
     * RULE r.a:    simple_expression → term simple_part
     * RULE r.b:    simple_expression → sign term simple_part
     */
    public void simple_expression() {
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT) {
            term();
            simple_part();
        } else if (sign()) {
            term();
            simple_part();
        } else {
            error("SIMPLE_EXPRESSION: TERM or SIGN can not be called.");
        }
    }

    /**
     * Executes the rule for simple_part in the expression grammar.
     *
     * Production Rules:
     * RULE s.a:    simple_part → addop term simple_part
     * RULE s.b:    simple_part → λ
     */
    public void simple_part() {
        if (isAddOp(this.lookahead.getType())) {
            match(lookahead.getType());
            term();
            simple_part();
        }
        // lambda case
    }

    /**
     * Executes the rule for sign in the expression grammar.
     * Because sign() is only called by the parent function
     * simple_expression, the lookahead token can both be checked
     * and matched in the same function.
     *
     * Production Rules:
     * RULE t.a:    sign → +
     * RULE t.b:    sign → -
     * @return true if lookahead token is a sign and is properly matched.
     */
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
     * Executes the rule for term in the expression grammar.
     *
     * Production Rules:
     * RULE u.a:    term → factor term_prime
     */
    public void term() {
        factor();
        term_prime();
    }

    /**
     * Executes the rule for term_prime in the expression grammar.
     *
     * Production Rules:
     * RULE v.a:    term_prime → mulop factor term_prime
     * RULE v.b:    term_prime → λ
     */
    public void term_prime() {
        if (isMulOp(this.lookahead.getType())) {
            match(this.lookahead.getType());
            factor();
            term_prime();
        }
        // lambda case
    }

    /**
     * Executes the rule for factor in the expression grammar.
     *
     * Production Rules:
     * RULE w.a:    factor → ID
     * RULE w.b:    factor → ID [ expression ]
     * RULE w.c:    factor → ID ( expression_list )
     * RULE w.d:    factor → num
     * RULE w.e:    factor → ( expression )
     * RULE w.f:    factor → not factor
     */
    public void factor() {
        if (lookahead.getType() == TokenType.ID) {
            match(TokenType.ID);
            if (lookahead.getType() == TokenType.LBRACE) {
                match(TokenType.LBRACE);
                expression();
                match(TokenType.RBRACE);
            } else if (lookahead.getType() == TokenType.LPAREN) {
                match(TokenType.LPAREN);
                expression_list();
                match(TokenType.RPAREN);
            }
        } else if (lookahead.getType() == TokenType.NUMBER) {
            match(TokenType.NUMBER);
        } else if (lookahead.getType() == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        } else if (lookahead.getType() == TokenType.NOT) {
            match(TokenType.NOT);
            factor();
        } else {
            error("FACTOR: TokenType ID, NUMBER, LPAREN or NOT not matched.");
        }
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
     *
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
     *
     * @return true if token is a multiplication operator.
     */
    private static boolean isMulOp(TokenType tokenType) {
        return (tokenType == TokenType.ASTERISK ||
                tokenType == TokenType.FSLASH ||
                tokenType == TokenType.DIV ||
                tokenType == TokenType.MOD ||
                tokenType == TokenType.AND);
    }

    /**
     * Checks if a given token is an addition operator as defined in the grammar.
     *
     * @return true if token is a addition operator.
     */
    private static boolean isAddOp(TokenType tokenType) {
        return (tokenType == TokenType.PLUS ||
                tokenType == TokenType.MINUS ||
                tokenType == TokenType.OR);
    }

    /**
     * Checks if a given token is an relational operator as defined in the grammar.
     *
     * @return true if token is a relational operator.
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
     *
     * @return symbol table
     */
    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    /**
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     *
     * @param message The error message to print.
     */
    private void error(String message) {
        System.out.println("ERROR: " + message);
        System.exit(1);
    }
}