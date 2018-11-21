/*
 * JFlex specification for the lexical analyzer for a simple demo language
 * Change this into the scanner for your implementation of MiniJava.
 */


package Scanner;

import java_cup.runtime.*;
import Parser.sym;
import Throwables.*;

%%

%public
%final
%class scanner
%yylexthrow CompilerException
%unicode
%cup
%line
%column

%{
  // note that these Symbol constructors are abusing the Symbol
  // interface to use Symbol's left and right fields as line and column
  // fields instead
  private Symbol symbol(int type) {
    return new Symbol(type, yyline+1, yycolumn+1);
  }
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline+1, yycolumn+1, value);
  }
  
  // print out a symbol (aka token) nicely
  public String symbolToString(Symbol s) {
    switch (s.sym) {
		case sym.CLASS: return "CLASS";
		case sym.PUBLIC: return "PUBLIC";
		case sym.STATIC: return "STATIC";
		case sym.VOID: return "VOID";
		case sym.MAIN: return "MAIN";
		case sym.STRING: return "STRING";
		case sym.EXTENDS: return "EXTENDS";
		case sym.RETURN: return "RETURN";
		case sym.INT: return "INT";
		case sym.BOOLEAN: return "BOOLEAN";
		case sym.IF: return "IF";
		case sym.ELSE: return "ELSE";
		case sym.WHILE: return "WHILE"; 
		case sym.SYSTEMOUTPRINTLN: return "SYSTEMOUTPRINTLN";
		case sym.LENGTH: return "LENGTH";
		case sym.THIS: return "THIS";
		case sym.NEW: return "NEW";
		case sym.LPAREN: return "LPAREN";
		case sym.RPAREN: return "RPAREN";
		case sym.LBRACE: return "LBRACE";
		case sym.RBRACE: return "RBRACE";
		case sym.LBRACK: return "LBRACK";
		case sym.RBRACK: return "RBRACK";
		case sym.SEMICOLON: return "SEMICOLON";
		case sym.COMMA: return "COMMA";
		case sym.DOT: return "DOT";
		case sym.EQ: return "EQ";
		case sym.LT: return "LT";
		case sym.NOT: return "NOT";
		case sym.ANDAND: return "ANDAND";
		case sym.PLUS: return "PLUS";
		case sym.MINUS: return "MINUS";
		case sym.MULT: return "MULT";
		case sym.BOOLEAN_LITERAL: return "BOOLEAN("+ s.value.toString() + ")";
		case sym.IDENTIFIER: return "ID(" + (String)s.value + ")";
		case sym.INTEGER_LITERAL: return "INTEGER(" + String.valueOf(s.value) +")";
		case sym.EOF: return "<EOF>";
		case sym.error: return "<ERROR>";
		default: return "<UNEXPECTED TOKEN " + s.toString() + ">";

    }
  }
%}

/* comments */
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
Comment = {TraditionalComment} | {EndOfLineComment} | 
          {DocumentationComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
EndOfLineComment = "//" {InputCharacter}* {LineTerminator}?
DocumentationComment = "/*" "*"+ [^/*] ~"*/"

/* Helper definitions */
eol = [\r\n]
white = {eol}|[ \t]

/* identifiers */
Identifier = [:jletter:][:jletterdigit:]*

/* integer literals */
DecIntegerLiteral = 0 | [1-9][0-9]*

%%

/* Token definitions */

<YYINITIAL> {

  /* keywords */
	"class"                        { return symbol(sym.CLASS); } 
	"public"                       { return symbol(sym.PUBLIC); } 
	"static"                       { return symbol(sym.STATIC); }
	"void"                         { return symbol(sym.VOID); }
	"main"						   { return symbol(sym.MAIN); }
	"String"        		       { return symbol(sym.STRING); }
	"extends"                      { return symbol(sym.EXTENDS); } 
	"return"                       { return symbol(sym.RETURN); }
	"int"                          { return symbol(sym.INT); } 
	"boolean"                      { return symbol(sym.BOOLEAN); } 
	"if"                           { return symbol(sym.IF); } 
	"else"                         { return symbol(sym.ELSE); } 
	"while"                        { return symbol(sym.WHILE); } 
	"System.out.println"           { return symbol(sym.SYSTEMOUTPRINTLN); } 
	"length"          			   { return symbol(sym.LENGTH); } 
	"this"                         { return symbol(sym.THIS); }
	"new"                          { return symbol(sym.NEW); } 

	
  /* boolean literals */
  "true"                         { return symbol(sym.BOOLEAN_LITERAL, new Boolean(true)); }
  "false"                        { return symbol(sym.BOOLEAN_LITERAL, new Boolean(false)); }
  
 
  
  /* separators */
  "("                            { return symbol(sym.LPAREN); }
  ")"                            { return symbol(sym.RPAREN); }
  "{"                            { return symbol(sym.LBRACE); }
  "}"                            { return symbol(sym.RBRACE); }
  "["                            { return symbol(sym.LBRACK); } 
  "]"                            { return symbol(sym.RBRACK); } 
  ";"                            { return symbol(sym.SEMICOLON); }
  ","                            { return symbol(sym.COMMA); }
  "."                            { return symbol(sym.DOT); }
  
  /* operators */
  "="                            { return symbol(sym.EQ); }

  "<"                            { return symbol(sym.LT); } 
  "!"                            { return symbol(sym.NOT); } 

  "&&"                           { return symbol(sym.ANDAND); } 

  "+"                            { return symbol(sym.PLUS); } 
  "-"                            { return symbol(sym.MINUS); } 
  "*"                            { return symbol(sym.MULT); } 

  
  
  
  /* identifiers */ 
  {Identifier}                   { return symbol(sym.IDENTIFIER, yytext()); }  
  
  /* numeric literals */
  {DecIntegerLiteral}            { return symbol(sym.INTEGER_LITERAL, new Integer(yytext())); }

   {Comment}                      { /* ignore */ }
/* whitespace */
{white}+ { /* ignore whitespace */ }
}



/* lexical errors (put last so other matches take precedence) */
. { throw new LexicalCompilerException(
	"unexpected character in input: '" + yytext() + "'", 
	yyline+1, yycolumn+1);
  }
