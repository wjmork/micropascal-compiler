package parser;

import scanner.Scanner;
import scanner.Token;
import scanner.TokenType;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * The parser recognizes whether an input string of tokens
 * is an expression.
 * To use a parser, create an instance pointing at a file,
 * and then call the top-level function, <code>exp()</code>.
 * If the functions returns without an error, the file
 * contains an acceptable expression.
 * @author Erik Steinmetz
 * @author William Mork
 */
public class Recognizer {
    
    ///////////////////////////////
    //    Instance Variables
    ///////////////////////////////
    
    private Token lookahead;
    
    private Scanner scanner;
    
    ///////////////////////////////
    //       Constructors
    ///////////////////////////////
    
    public Recognizer(String text, boolean isFilename) {
        if( isFilename) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("expressions/simplest.pas");
        } catch (FileNotFoundException ex) {
            error( "No file");
        }
        InputStreamReader isr = new InputStreamReader( fis);
        scanner = new Scanner( isr);
                 
        }
        else {
            scanner = new Scanner( new StringReader( text));
        }
        try {
            lookahead = scanner.nextToken();
        } catch (IOException ex) {
            error( "Scan error");
        }
        
    }
    
    ///////////////////////////////
    //       Methods
    ///////////////////////////////

    /**
     *
     */
    public void program() {
        if (this.lookahead.type == TokenType.PROGRAM) {
            match (TokenType.PROGRAM);
        }
        else error("PROGRAM: TokenType PROGRAM not matched.");

        if (this.lookahead.type == TokenType.ID) {
            match (TokenType.ID);
        }
        else error("PROGRAM: TokenType ID not matched.");

        if (this.lookahead.type == TokenType.SEMI) {
            match (TokenType.SEMI);
        }
        else error("PROGRAM: TokenType SEMI not matched.");

        declarations();
        subprogram_declarations();
        compound_statement();

        if (this.lookahead.type == TokenType.PERIOD) {
            match (TokenType.PERIOD);
        }
        else error("PROGRAM: TokenType PERIOD not matched.");
    }

    /**
     *
     */
    public void identifier_list() {
        if (this.lookahead.type == TokenType.ID) {
            match (TokenType.ID);
            if (this.lookahead.type == TokenType.COMMA)
            {
                match(TokenType.COMMA);
                identifier_list();
            }
            else error("IDENTIFIER_LIST: TokenType COMMA not matched.");
        }
        else error("IDENTIFIER_LIST: TokenType ID not matched.");
    }

    /**
     *
     */
    public void declarations() {
        if (this.lookahead.type == TokenType.VAR)
        {
            match(TokenType.VAR);
            identifier_list();
            if (this.lookahead.type == TokenType.COLON) {
                match(TokenType.COLON);
                type();
                if (this.lookahead.type == TokenType.SEMI) {
                    match(TokenType.SEMI);
                    declarations();
                }
                else error("DECLARATIONS: TokenType SEMI not matched.");
            }
            else error("DECLARATIONS: TokenType VAR not matched.");
        }
        else
        {
            // lambda option
        }
    }

    /**
     *
     */
    public void type() {

    }

    /**
     *
     */
    public void standard_type() {
        if (this.lookahead.type == TokenType.INTEGER)
        {
            match(TokenType.INTEGER);
        }
        else if (this.lookahead.type == TokenType.REAL)
        {
            match(TokenType.REAL);
        }
        else
        {
            error("STANDARD_TYPE: TokenType INTEGER or REAL not matched.");
        }
    }

    /**
     *
     */
    public void subprogram_declarations() {
        if (this.lookahead.type == TokenType.FUNCTION || this.lookahead.type == TokenType.PROCEDURE) {
            subprogram_declaration();
            if (this.lookahead.type == TokenType.SEMI) {
                match(TokenType.SEMI);
                subprogram_declarations();
            } else if (this.lookahead.type == TokenType.REAL) {
                match(TokenType.REAL);
            } else {
                error("STANDARD_TYPE: TokenType INTEGER or REAL not matched.");
            }
        }
    }

    /**
     *
     */
    public void subprogram_declaration() {

    }

    /**
     *
     */
    public void subprogram_head() {

    }

    /**
     *
     */
    public void arguments() {

    }

    /**
     *
     */
    public void parameter_list() {

    }

    /**
     *
     */
    public void compound_statement() {

    }

    /**
     *
     */
    public void optional_statements() {

    }

    /**
     *
     */
    public void statement_list() {

    }

    /**
     *
     */
    public void statement() {

    }

    /**
     *
     */
    public void variable() {

    }

    /*
    procedure statement will be ignored for module 2.
    public void procedure_statement() {

    }
    */

    /**
     *
     */
    public void expression_list() {

    }

    /**
     *
     */
    public void expression() {

    }

    /**
     *
     */
    public void simple_expression() {

    }

    /**
     *
     */
    public void simple_part() {

    }

    /**
     *
     */
    public void sign() {

    }

    /**
     * Executes the rule for the exp non-terminal symbol in
     * the expression grammar.
     */
    public void exp() {
        term();
        exp_prime();
    }
    
    /**
     * Executes the rule for the exp&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void exp_prime() {
        if( lookahead.getType() == TokenType.PLUS ||
                lookahead.getType() == TokenType.MINUS ) {
            addop();
            term();
            exp_prime();
        }
        else{
            // lambda option
        }
    }
    
    /**
     * Executes the rule for the addop non-terminal symbol in
     * the expression grammar.
     */
    public void addop() {
        if( lookahead.getType() == TokenType.PLUS) {
            match( TokenType.PLUS);
        }
        else if( lookahead.getType() == TokenType.MINUS) {
            match( TokenType.MINUS);
        }
        else {
            error( "Addop");
        }
    }
    
    /**
     * Executes the rule for the term non-terminal symbol in
     * the expression grammar.
     */
    public void term() {
        factor();
        term_prime();
    }
    
    /**
     * Executes the rule for the term&prime; non-terminal symbol in
     * the expression grammar.
     */
    public void term_prime() {
        if( isMulop( lookahead) ) {
            mulop();
            factor();
            term_prime();
        }
        else{
            // lambda option
        }
    }
    
    /**
     * Determines whether or not the given token is
     * a mulop token.
     * @param token The token to check.
     * @return true if the token is a mulop, false otherwise
     */
    private boolean isMulop(Token token) {
        boolean answer = false;
        if( token.getType() == TokenType.ASTERISK ||
                token.getType() == TokenType.FSLASH ) {
            answer = true;
        }
        return answer;
    }
    
    /**
     * Executes the rule for the mulop non-terminal symbol in
     * the expression grammar.
     */
    public void mulop() {
        if( lookahead.getType() == TokenType.ASTERISK) {
            match( TokenType.ASTERISK);
        }
        else if( lookahead.getType() == TokenType.FSLASH) {
            match( TokenType.FSLASH);
        }
        else {
            error( "Mulop");
        }
    }
    
    /**
     * Executes the rule for the factor non-terminal symbol in
     * the expression grammar.
     */
    public void factor() {
        // Executed this decision as a switch instead of an
        // if-else chain. Either way is acceptable.
        switch (lookahead.getType()) {
            case LPAREN:
                match( TokenType.LPAREN);
                exp();
                match( TokenType.RPAREN);
                break;
            case NUMBER:
                match( TokenType.NUMBER);
                break;
            default:
                error("Factor");
                break;
        }
    }
    
    /**
     * Matches the expected token.
     * If the current token in the input stream from the scanner
     * matches the token that is expected, the current token is
     * consumed and the scanner will move on to the next token
     * in the input.
     * The null at the end of the file returned by the
     * scanner is replaced with a fake token containing no
     * type.
     * @param expected The expected token type.
     */
    public void match( TokenType expected) {
        System.out.println("match( " + expected + ")");
        if( this.lookahead.getType() == expected) {
            try {
                this.lookahead = scanner.nextToken();
                if( this.lookahead == null) {
                    this.lookahead = new Token( "End of File", null);
                }
            } catch (IOException ex) {
                error( "Scanner exception");
            }
        }
        else {
            error("Match of " + expected + " found " + this.lookahead.getType() + " instead.");
        }
    }
    
    /**
     * Errors out of the parser.
     * Prints an error message and then exits the program.
     * @param message The error message to print.
     */
    public void error( String message) {
        System.out.println("Error.");
        // System.out.println( "Error " + message + " at line " + this.scanner.getLine() + " column " + this.scanner.getColumn());
        //System.exit( 1);
    }
}
