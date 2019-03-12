package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

import java.io.*;

/**
 * The recognizer class recognizes whether an input string of tokens
 * is an expression.
 * <p>
 * To use a recognizer, create an instance pointing at a file,
 * and then call the top-level function, <code>exp()</code>.
 * If the functions returns without an error, the file
 * contains an acceptable expression.
 * </p>
 *
 * @author William Mork
 */
public class Recognizer {

    //  Instance Variables
    private Token lookahead;
    private Scanner inputStreamScanner;

    //  Constructors
    /**
     * Creates a Recognizer.
     * @param input The input stream (String or file path) to be parsed.
     * @param importFile If true, input should be the path of a file. If false, a String should be provided.
     */
    public Recognizer(String input, boolean importFile) {
        InputStreamReader inputStreamReader;

        if (importFile) {
            FileInputStream inputStream = null;
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

    //  Methods
    /**
     * Executes the rule for the program non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   program → program ID ; | declarations | subprogram_declarations | compound_statement | .
     */
    public void program() {
        if (this.lookahead.type == TokenType.PROGRAM) {
            match(TokenType.PROGRAM);
        } else error("PROGRAM: TokenType PROGRAM not matched.");

        if (this.lookahead.type == TokenType.ID) {
            match(TokenType.ID);
        } else error("PROGRAM: TokenType ID not matched.");

        if (this.lookahead.type == TokenType.SEMI) {
            match(TokenType.SEMI);
        } else error("PROGRAM: TokenType SEMI not matched.");

        declarations();
        subprogram_declarations();
        compound_statement();

        if (this.lookahead.type == TokenType.PERIOD) {
            match(TokenType.PERIOD);
        } else error("PROGRAM: TokenType PERIOD not matched.");
    }

    /**
     * Executes the rule for the identifier_list non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   identifier_list → ID | ID, identifier_list
     */
    public void identifier_list() {
        if (this.lookahead.type == TokenType.ID) {
            match(TokenType.ID);
            if (this.lookahead.type == TokenType.COMMA) {
                match(TokenType.COMMA);
                identifier_list();
            }
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the declarations non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   declarations → VAR identifier_list : type ; declarations | λ
     */
    public void declarations() {
        if (this.lookahead.type == TokenType.VAR) {
            match(TokenType.VAR);
            identifier_list();
            match(TokenType.COLON);
            type();
            match(TokenType.SEMI);
            declarations();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the type non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   type → standard_type | ARRAY [ NUM : NUM ] of standard_type
     */
    public void type() {
        if (this.lookahead.type == TokenType.ARRAY) {
            match(TokenType.ARRAY);
            match(TokenType.LBRACE);
            match(TokenType.NUMBER);
            match(TokenType.COLON);
            match(TokenType.NUMBER);
            match(TokenType.LBRACE);
            match(TokenType.OF);
        } else if (this.lookahead.type == TokenType.INTEGER || this.lookahead.type == TokenType.REAL) {
            standard_type();
        } else {
            error("TYPE: TokenType ARRAY not matched or STANDARD_TYPE not matched.");
        }
    }

    /**
     * Executes the rule for the standard_type non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   standard_type → INTEGER | REAL
     */
    public void standard_type() {
        if (this.lookahead.type == TokenType.INTEGER) {
            match(TokenType.INTEGER);
        } else if (this.lookahead.type == TokenType.REAL) {
            match(TokenType.REAL);
        } else {
            error("STANDARD_TYPE: TokenType INTEGER or REAL not matched.");
        }
    }

    /**
     * Executes the rule for the subprogram_declarations non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   subprogram_declarations → subprogram_declaration ; subprogram_declarations | λ
     */
    public void subprogram_declarations() {
        if (this.lookahead.type == TokenType.FUNCTION || this.lookahead.type == TokenType.PROCEDURE) {
            subprogram_declaration();
            if (this.lookahead.type == TokenType.SEMI) {
                match(TokenType.SEMI);
                subprogram_declarations();
            } else {
                // lambda option
            }
        }
    }

    /**
     * Executes the rule for the subprogram_declaration non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   subprogram_declaration → subprogram_head declarations compound_statement
     */
    public void subprogram_declaration() {
        subprogram_head();
        declarations();
        compound_statement();
    }

    /**
     * Executes the rule for the subprogram_head non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   subprogram_head → function ID arguments : standard_type ; | procedure ID arguments ;
     */
    public void subprogram_head() {
        if (this.lookahead.type == TokenType.FUNCTION) {
            match(TokenType.FUNCTION);
            match(TokenType.ID);
            arguments();
            match(TokenType.COLON);
            standard_type();
            match(TokenType.SEMI);
        } else if (this.lookahead.type == TokenType.PROCEDURE) {
            match(TokenType.PROCEDURE);
            match(TokenType.ID);
            arguments();
            match(TokenType.SEMI);
        } else {
            error("SUBPROGRAM_HEAD: TokenType FUNCTION or PROCEDURE not matched.");
        }
    }

    /**
     * Executes the rule for the arguments non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   arguments → ( parameter_list ) | λ
     */
    public void arguments() {
        if (this.lookahead.type == TokenType.LPAREN) {
            match(TokenType.LPAREN);
            parameter_list();
            if (this.lookahead.type == TokenType.RPAREN) {
                match(TokenType.RPAREN);
            } else {
                error("ARGUMENTS: TokenType RBRACE not matched.");
            }
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the parameter_list non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   parameter_list → identifier_list : type | identifier_list : type ; parameter_list
     */
    public void parameter_list() {
        identifier_list();
        if (this.lookahead.type == TokenType.COLON) {
            match(TokenType.COLON);
            type();
            if (this.lookahead.type == TokenType.SEMI) {
                match(TokenType.SEMI);
                parameter_list();
            }
        }
    }

    /**
     * Executes the rule for the compound_statement non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   compound_statement → BEGIN optional_statements END
     */
    public void compound_statement() {
        if (this.lookahead.type == TokenType.BEGIN) {
            match(TokenType.BEGIN);
        }
        optional_statements();
        if (this.lookahead.type == TokenType.END) {
            match(TokenType.END);
        }
    }

    /**
     * Executes the rule for the optional_statements non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   optional_statements → statement_list | λ
     */
    public void optional_statements() {
        if (this.lookahead.type == TokenType.ID ||
                this.lookahead.type == TokenType.BEGIN ||
                this.lookahead.type == TokenType.IF ||
                this.lookahead.type == TokenType.READ ||
                this.lookahead.type == TokenType.WRITE ||
                this.lookahead.type == TokenType.RETURN) {
            statement_list();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the statement_list non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   statement_list → statement | statement ; statement_list
     */
    public void statement_list() {
        statement();
        if (this.lookahead.type == TokenType.SEMI) {
            match(TokenType.SEMI);
            statement_list();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the statement non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   statement → variable assignop expression | procedure_statement | compound_statement | IF expression THEN statement ELSE statement | WHILE expression DO statement | READ ( ID ) | WRITE ( expression ) | RETURN expression
     */
    public void statement() {
        if (this.lookahead.type == TokenType.ID) {
            variable();
            match(TokenType.ASSIGN);
            expression();
        } else if (this.lookahead.type == TokenType.BEGIN) {
            // procedure_statement();
        } else if (this.lookahead.type == TokenType.BEGIN) {
            compound_statement();
        } else if (this.lookahead.type == TokenType.IF) {
            match(TokenType.IF);
            expression();
            match(TokenType.THEN);
            statement();
            match(TokenType.ELSE);
            statement();
        } else if (this.lookahead.type == TokenType.WHILE) {
            match(TokenType.WHILE);
            expression();
            match(TokenType.DO);
            statement();
        } else if (this.lookahead.type == TokenType.READ) {
            match(TokenType.READ);
            match(TokenType.LPAREN);
            match(TokenType.ID);
            match(TokenType.RPAREN);
        } else if (this.lookahead.type == TokenType.WRITE) {
            match(TokenType.WRITE);
            match(TokenType.LPAREN);
            expression();
            match(TokenType.RPAREN);
        } else if (this.lookahead.type == TokenType.RETURN) {
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
     * Structure:   variable → ID | ID [ expression ]
     */
    public void variable() {
        if (this.lookahead.type == TokenType.ID) {
            match(TokenType.ID);
            if (this.lookahead.type == TokenType.LBRACE) {
                match(TokenType.LBRACE);
                expression();
                if (this.lookahead.type == TokenType.RBRACE) {
                    match(TokenType.RBRACE);
                } else {
                    error("VARIABLE: TokenType RBRACE not matched.");
                }
            }
        } else {
            error("VARIABLE: TokenType ID not matched.");
        }
    }

    //procedure statement will be ignored for module 2.
    public void procedure_statement() {
    }


    /**
     * Executes the rule for the expression_list non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   expression_list → expression | expression , expression_list
     */
    public void expression_list() {
        expression();
        if (this.lookahead.type == TokenType.COMMA) {
            match(TokenType.COMMA);
            expression_list();
        }
    }

    /**
     * Executes the rule for the expression non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   expression → expression_list | expression_list relop expression_list
     */
    public void expression() {
        simple_expression();
        if (this.lookahead.type == TokenType.EQUAL ||
                this.lookahead.type == TokenType.NOTEQ ||
                this.lookahead.type == TokenType.LTHAN ||
                this.lookahead.type == TokenType.LTHANEQ ||
                this.lookahead.type == TokenType.GTHANEQ ||
                this.lookahead.type == TokenType.GTHAN) {
            match(this.lookahead.type);
            simple_expression();
        }
    }

    /**
     * Executes the rule for the simple_expression non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   simple_expression → term simple_part | sign term simple_part
     */
    public void simple_expression() {
        if (this.lookahead.getType() == TokenType.ID ||
                this.lookahead.getType() == TokenType.NUMBER ||
                this.lookahead.getType() == TokenType.LPAREN ||
                this.lookahead.getType() == TokenType.NOT) {
            term();
            simple_part();
        } else if (this.lookahead.getType() == TokenType.PLUS ||
                this.lookahead.getType() == TokenType.MINUS) {
            sign();
            term();
            simple_part();
        } else {
            error("SIMPLE_EXPRESSION: TokenType ID, NUMBER, LPAREN, NOT, PLUS, or MINUS not matched.");
        }
    }

    /**
     * Executes the rule for the simple_part non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   simple_part → addop term simple_part | λ
     */
    public void simple_part() {
        if (this.lookahead.type == TokenType.PLUS ||
                this.lookahead.type == TokenType.MINUS ||
                this.lookahead.type == TokenType.OR) {
            sign();
            term();
            simple_part();
        } else {
            // lambda option
        }
    }

    /**
     * Executes the rule for the sign non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   sign → addop term simple_part | λ
     */
    public void sign() {
        if (this.lookahead.type == TokenType.PLUS) {
            match(TokenType.PLUS);
        } else if (this.lookahead.type == TokenType.MINUS) {
            match(TokenType.MINUS);
        } else {
            error("SIGN: TokenType PLUS or MINUS not matched.");
        }
    }

    /**
     * Executes the rule for the exp non-terminal symbol in
     * the expression grammar.
     */
    public void exp() {
        term();
        simple_part();
    }

    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   term → factor term_prime
     */
    public void term() {
        factor();
        term_prime();
    }

    /**
     * Executes the rule for the term_prime non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   term_prime → mulop factor term_prime | λ
     */
    public void term_prime() {
        if (isMulop(this.lookahead)) {
            mulop();
            factor();
            term_prime();
        } else {
            // lambda option
        }
    }

    /**
     * Determines whether or not the given token is
     * a mulop token.
     *
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    private boolean isMulop(Token token) {
        boolean answer = false;
        if (token.type == TokenType.ASTERISK || token.type == TokenType.FSLASH) {
            answer = true;
        }
        return answer;
    }

    /**
     * Executes the rule for the mulop non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   mulop → * | /
     */
    public void mulop() {
        if (this.lookahead.type == TokenType.ASTERISK) {
            match(TokenType.ASTERISK);
        } else if (this.lookahead.type == TokenType.FSLASH) {
            match(TokenType.FSLASH);
        } else {
            error("MULOP: TokenType ASTERISK or FSLASH not matched.");
        }
    }

    /**
     * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     *
     * Structure:   factor → ID | ID [ expression ] | ID ( expression_list ) | num | ( expression ) | not factor
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
        System.out.println("MATCH| Expected: " + expected + ". Look-ahead: " + this.lookahead.getType() + ".");
        if (this.lookahead.getType() == expected) {
            try {
                this.lookahead = inputStreamScanner.nextToken();
                if (this.lookahead == null) {
                    this.lookahead = new Token("End of File", null);
                }
            } catch (IOException ex) {
                error("Scanner exception");
            }
        } else {
            error("MATCH| " + expected + " found " + this.lookahead.getType() + " instead.");
        }
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
