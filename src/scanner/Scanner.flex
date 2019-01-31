/* William  Mork */
/* CSC 451 */
/* Scanner.flex */

package scanner;

import java.util.HashMap;

%%

/* Declarations */
%class  Scanner	        /* Names the produced java file */
%function nextToken 	/* Renames the yylex() function */
%public                 /* Defines the class as public */
%type   Token			/* Defines the return type of the scanning function */
%unicode

%eofval{
    return null;
%eofval}

%{
    private HashMap<String, TokenType> tokenTypes;
%}

%{
    public Scanner(String input)
    {

    }
%}

%init{
    tokenTypes = new HashMap<>();
    tokenTypes.put("and", TokenType.AND);
    tokenTypes.put("array", TokenType.ARRAY);
    tokenTypes.put("begin", TokenType.BEGIN);
    tokenTypes.put("div", TokenType.DIV);
    tokenTypes.put("do", TokenType.DO);
    tokenTypes.put("else", TokenType.ELSE);
    tokenTypes.put("end", TokenType.END);
    tokenTypes.put("function", TokenType.FUNCTION);
    tokenTypes.put("if", TokenType.IF);
    tokenTypes.put("integer", TokenType.INTEGER);
    tokenTypes.put("mod", TokenType.MOD);
    tokenTypes.put("not", TokenType.NOT);
    tokenTypes.put("of", TokenType.OF);
    tokenTypes.put("or", TokenType.OR);
    tokenTypes.put("procedure", TokenType.PROCEDURE);
    tokenTypes.put("program", TokenType.PROGRAM);
    tokenTypes.put("real", TokenType.REAL);
    tokenTypes.put("then", TokenType.THEN);
    tokenTypes.put("var", TokenType.VAR);
    tokenTypes.put("while", TokenType.WHILE);
    tokenTypes.put(";", TokenType.SEMI);
    tokenTypes.put(",", TokenType.COMMA);
    tokenTypes.put(".", TokenType.PERIOD);
    tokenTypes.put(":", TokenType.COLON);
    tokenTypes.put("[", TokenType.LBRACE);
    tokenTypes.put("]", TokenType.RBRACE);
    tokenTypes.put("(", TokenType.LPAREN);
    tokenTypes.put(")", TokenType.RPAREN);
    tokenTypes.put("+", TokenType.PLUS);
    tokenTypes.put("-", TokenType.MINUS);
    tokenTypes.put("=", TokenType.EQUAL);
    tokenTypes.put("<>", TokenType.NOTEQ);
    tokenTypes.put("<", TokenType.LTHAN);
    tokenTypes.put("<=", TokenType.LTHANEQ);
    tokenTypes.put(">", TokenType.GTHAN);
    tokenTypes.put(">=", TokenType.GTHANEQ);
    tokenTypes.put("*", TokenType.ASTERISK);
    tokenTypes.put("/", TokenType.FSLASH);
    tokenTypes.put(":=", TokenType.ASSIGN);
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
symbols                 = {symbol}|:=|<=|>=|<>
commentContent          = [^\{\}]
comment                 = \{{commentContent}*\}
whitespace				= [ \n\t\r\f]|{comment}
other					= .

%%

/* Lexical Rules */
{id}
{
    String lexeme = yytext();
    TokenType type = tokenTypes.get(lexeme);

    if (type != null)
    {
        return (new Token(lexeme, type));
    }
    else
    {
        return (new Token(lexeme, TokenType.ID));
    }

}

{symbol}
{
    return(new Token(yytext(), TokenType.ID));
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
    System.out.println("Invalid syntax.");
	System.exit(1);
}