/* William  Mork */
/* CSC 450 */
/* Scanner.flex */

package scanner;

import java.util.HashMap;

%%

/* Declarations */
%class  Scanner	        /* Names the produced java file */
%function nextToken 	/* Renames the yylex() function */
%type   Token			/* Defines the return type of the scanning function */
%unicode

%eofval{
  return null;
%eofval}

%{
    private HashMap<String, Type> tokenTypes;
%}

%init{
    tokenTypes = new HashMap<>();
    tokenTypes.put("and", Type.AND);
    tokenTypes.put("array", Type.ARRAY);
    tokenTypes.put("begin", Type.BEGIN);
    tokenTypes.put("div", Type.DIV);
    tokenTypes.put("do", Type.DO);
    tokenTypes.put("else", Type.ELSE);
    tokenTypes.put("end", Type.END);
    tokenTypes.put("function", Type.FUNCTION);
    tokenTypes.put("if", Type.IF);
    tokenTypes.put("integer", Type.INTEGER);
    tokenTypes.put("mod", Type.MOD);
    tokenTypes.put("not", Type.NOT);
    tokenTypes.put("of", Type.OF);
    tokenTypes.put("or", Type.OR);
    tokenTypes.put("procedure", Type.PROCEDURE);
    tokenTypes.put("program", Type.PROGRAM);
    tokenTypes.put("real", Type.REAL);
    tokenTypes.put("then", Type.THEN);
    tokenTypes.put("var", Type.VAR);
    tokenTypes.put("while", Type.WHILE);
    tokenTypes.put(";", Type.SEMI);
    tokenTypes.put(",", Type.COMMA);
    tokenTypes.put(".", Type.PERIOD);
    tokenTypes.put(":", Type.COLON);
    tokenTypes.put("[", Type.LBRACE);
    tokenTypes.put("]", Type.RBRACE);
    tokenTypes.put("(", Type.LPAREN);
    tokenTypes.put(")", Type.RPAREN);
    tokenTypes.put("+", Type.PLUS);
    tokenTypes.put("-", Type.MINUS);
    tokenTypes.put("=", Type.EQUAL);
    tokenTypes.put("<>", Type.NOTEQ);
    tokenTypes.put("<", Type.LTHAN);
    tokenTypes.put("<=", Type.LTHANEQ);
    tokenTypes.put(">", Type.GTHAN);
    tokenTypes.put(">=", Type.GTHANEQ);
    tokenTypes.put("*", Type.ASTERISK);
    tokenTypes.put("/", Type.FSLASH);
    tokenTypes.put(":=", Type.ASSIGN);
    tokenTypes.put("read", Type.READ);
    tokenTypes.put("write", Type.WRITE);
%init}

/* Patterns */
letter					= [A-Za-z]
digit					= [0-9]
digits					= {digit}{digit}*
fraction				= (\.{digits})?
exponent				= ((E[\+\-]?){digits})?
num						= {digits}{fraction}{exponent}
id						= {letter}({letter} | {digit})*
word					= {letter}+
symbol					= [=<>+\-*/;,.\[\]():]
whitespace				= [ \n\t]
other					= .

%%

/* Lexical Rules */
{word}
{
	/** Print out the word that was found. */
	//System.out.println("Found a word: " + yytext());
	return( new Token( yytext()));
}

{num}
{
	return(new Token(yytext(), TokenType.NUMBER));
}
            
{whitespace}
{
	/* Ignore Whitespace */ 
}

{other}
{ 
	System.out.println("Illegal char: '" + yytext() + "' found.");
}
           