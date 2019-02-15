package scanner;

/**
 * This scanner recognizes integers along with parentheses,
 * plus, minus, multiply and divide symbols.
 * @author Erik Steinmetz
 */
%%

/////////// JFlex Directives  ////////////////

%public                     // make output class public
%class ExpScanner           // name of output class
%function nextToken         // name of the token function
%type ExpToken              // type of return value from token function
%line                       // turn on line counting
%column                     // turn on column counting

%eofval{                    // return on end of file
  return null;
%eofval}


////////////// Extra code for the scanner ///////////

%{
  /**
   * Gets the line number of the most recent lexeme.
   * @return The current line number.
   */
  public int getLine() { return yyline;}

  /**
   * Gets the column number of the most recent lexeme.
   * This is the number of chars since the most recent newline char.
   * @return The current column number.
   */
  public int getColumn() { return yycolumn;}

  /** The lookup table of token types for symbols. */
  private LookupTable table = new LookupTable();
%}

//////////////  Patterns   ///////////////////

digit      = [0-9]
number     = {digit}+
symbol     = "+" | "-" | "*" | "/" | "(" | ")"
whitespace = [ \n\t]

%%

/**  Lexical Rules  */

{number}        {
                    // Found a number
                    ExpToken t = new ExpToken( yytext(), ExpTokenType.NUMBER);
                    return t;
                }

{whitespace}    { /* Ignore Whitespace */ }

{symbol}        {
                    // Found a symbol
                    String lexeme = yytext();
                    ExpTokenType ett = table.get( lexeme);
                    ExpToken t = new ExpToken( yytext(),  ett);
                    return t;
                }

.               {
					System.out.println("Illegal character, '" +
                            yytext() + "' found.");
                }
