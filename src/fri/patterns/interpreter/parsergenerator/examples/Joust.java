package fri.patterns.interpreter.parsergenerator.examples;
import java.util.*;

import fri.patterns.interpreter.parsergenerator.*;
import fri.patterns.interpreter.parsergenerator.semantics.*;
//import fri.patterns.interpreter.parsergenerator.builder.SerializedParser;
import fri.patterns.interpreter.parsergenerator.parsertables.LALRParserTables;
import fri.patterns.interpreter.parsergenerator.lexer.LexerBuilder;
import fri.patterns.interpreter.parsergenerator.syntax.builder.SyntaxSeparation;
import fri.patterns.interpreter.parsergenerator.syntax.Syntax;

/**
	Calculator for arithmetic expressions, showing the elegance of ReflectSemantic.
	<p>
	Syntax: java fri.patterns.interpreter.parsergenerator.examples.Calculator '(4+2.3) *(2 - -6) + 3*2'
	
*/

public class Joust extends ReflectSemantic
{
	private static String [][] rules = {	// arithmetic sample
		{ "PROGRAM", "STATEMENT"},
		{ "PROGRAM", "PROGRAM", "PROGRAMDELIMITER", "STATEMENT" },
		{ "PROGRAM", "STATEMENT2"},
		{ "PROGRAM", "PROGRAM", "PROGRAMDELIMITER", "STATEMENT2" },
		{ "STATEMENT2", "STATEMENT", "'*'"},
		{ "STATEMENT", "BOOLEXP" },
		{ "BOOLEXP", "EXPRESSION" },
		{ "BOOLEXP", "NOT", "'('", "BOOLEXP", "')'" },
		{ "BOOLEXP", "STATEMENT", "OPERATOR", "BOOLEXP" },
		{ "EXPRESSION", "'occupies'", "'('", "CHESSPIECE",  "','", "COORDINATE", "')'" },
		{ "EXPRESSION", "'captured'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'sameFile'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'higherFile'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'lowerFile'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'higherRank'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'lowerRank'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'betweenFile'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'betweenRank'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'file'", "'('", "CHESSPIECE",  "','", "X", "')'" },
		{ "EXPRESSION", "'file'", "'('", "CHESSPIECE",  "COMPARATOR", "X", "')'" },
		{ "EXPRESSION", "'sameRank'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'rank'", "'('", "CHESSPIECE",  "','", "`number`", "')'" },
		{ "EXPRESSION", "'rank'", "'('", "CHESSPIECE",  "COMPARATOR", "`number`", "')'" },
		{ "EXPRESSION", "'relRank'", "'('", "CHESSPIECE",  "','", "`number`", "')'" },
		{ "EXPRESSION", "'relRank'", "'('", "CHESSPIECE",  "COMPARATOR", "`number`", "')'" },
		{ "EXPRESSION", "'canAttack'", "'('", "CHESSPIECE",  "','", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'isDefended'", "'('", "CHESSPIECE", "')'" },
		{ "EXPRESSION", "'check'", "'('", "KING", "')'" },
		{ "EXPRESSION", "'checkmate'", "'('", "KING", "')'" },
		{ "EXPRESSION", "'start'" }, 
		{ "EXPRESSION", "'end'" }, // games don't necessarily end in a checkmate
		{ "EXPRESSION", "'only'", "'('", "PARAMETERS", "')'" },
		{ "EXPRESSION", "'exists'", "'('", "PARAMETERS", "')'" },
		{ "EXPRESSION", "'sameColor'", "'('", "PARAMETERS", "')'" },
		{ "EXPRESSION", "'different'", "'('", "VARPARAMETERS", "')'" },
		{ "EXPRESSION", "'%'"},
		{ "EXPRESSION", "'wildcard'"},
		{ "PARAMETERS", "CHESSPIECE"},
		{ "PARAMETERS", "PARAMETERS", "','", "CHESSPIECE"},
		{ "VARPARAMETERS", "VARCHESSPIECE"},
		{ "VARPARAMETERS", "VARCHESSPIECE", "','", "VARIABLE"},
		
		{ "COORDINATE", "X", "FACTOR"},
		
	//	{ "CHESSPIECE", "TYPE", "FACTOR"},
		{ "CHESSPIECE", "VARCHESSPIECE"},
		{ "CHESSPIECE", "TYPECHESSPIECE"},
		{ "VARCHESSPIECE", "TYPE", "VARIABLE"},
		{ "TYPECHESSPIECE", "TYPE"},
		
		{ "TYPE", "'p'"},
		{ "TYPE", "'k'"},
		{ "TYPE", "'q'"},
		{ "TYPE", "'n'"},
		{ "TYPE", "'r'"},
		{ "TYPE", "'b'"},
		{ "TYPE", "'P'"},
		{ "TYPE", "'K'"},
		{ "TYPE", "'Q'"},
		{ "TYPE", "'N'"},
		{ "TYPE", "'R'"},
		{ "TYPE", "'B'"},
		{ "TYPE", "'G'"},
		{ "TYPE", "'g'"},
		{ "TYPE", "'L'"},
		{ "TYPE", "'l'"},
		{ "TYPE", "'D'"},
		{ "TYPE", "'d'"},
		
		// generic pieces
		{ "TYPE", "'p''"},
		{ "TYPE", "'k''"},
		{ "TYPE", "'q''"},
		{ "TYPE", "'n''"},
		{ "TYPE", "'r''"},
		{ "TYPE", "'b''"},
		{ "TYPE", "'P''"},
		{ "TYPE", "'K''"},
		{ "TYPE", "'Q''"},
		{ "TYPE", "'N''"},
		{ "TYPE", "'R''"},
		{ "TYPE", "'B''"},
		
		{ "X", "'a'"},
		{ "X", "'b'"},
		{ "X", "'c'"},
		{ "X", "'d'"},
		{ "X", "'e'"},
		{ "X", "'f'"},
		{ "X", "'g'"},
		{ "X", "'h'"},
		
		{ "OPERATOR", "'&&'" },
		{ "OPERATOR", "'||'" },
		{ "OPERATOR", "'and'" },
		{ "OPERATOR", "'or'" },
		
		{ "COMPARATOR", "'<'"},
		{ "COMPARATOR", "'='"},
		{ "COMPARATOR", "'>'"},
		{ "COMPARATOR", "'>='"},
		{ "COMPARATOR", "'<='"},
		
		{ "NOT", "'not'" },
		{ "NOT", "'occurs'"},
		
		{ "KING", "'K'"},
		{ "KING", "'k'"},
		
		{ "FACTOR",   "`number`", },
		{ "VARIABLE",   "`letter`", },
		{ "VARIABLE",   "VARIABLE", "`letter`", },
		//{ "PROGRAMDELIMITER", "','" },
		{ "PROGRAMDELIMITER", "'through'"},
		{ "PROGRAMDELIMITER", "'until'"},
		
		
		{ Token.IGNORED,   "`whitespaces`" },
	};
	
	public Object PROGRAM (Object STATEMENT) {
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(STATEMENT);
		return toReturn;
	}
	
	public Object PROGRAM (Object PROGRAM, Object delimiter, Object STATEMENT) {
		String s = (String) delimiter;
		ArrayList<Object> toReturn = new ArrayList<Object>();
		if (s.equals("through") || s.equals("until")) {
			ArrayList<Object> program = (ArrayList<Object>) PROGRAM;
			//System.out.println("orig prog: " + program);
			int size = program.size();
			Object last = program.get(size - 1);
			program.remove(size - 1);
			ArrayList<Object> toReturn2 = new ArrayList<Object>();
			toReturn2.add(delimiter);
			toReturn2.add(last);
			toReturn2.add(STATEMENT);
			program.add(toReturn2);
			//System.out.println("prog: " + program);
			toReturn = program;
		} else {
			ArrayList<Object> program = (ArrayList<Object>) PROGRAM;
			program.add(STATEMENT);
			toReturn = program;
		}
		return toReturn;
	}
	
	public Object STATEMENT(Object EXPRESSION) {
		//System.out.println("STAT EXPRESSION: " + EXPRESSION);
		return EXPRESSION;
	}

	
	public Object STATEMENT(Object STATEMENT, Object OPERATOR, Object EXPRESSION) {
		//return "[" + OPERATOR + ", " + STATEMENT + ", " + EXPRESSION + "]";
		//return "[" + OPERATOR + " " + STATEMENT + " " + EXPRESSION + "]";
		//System.out.println("STATEMENT: " + STATEMENT);
		//System.out.println("OPERATOR: " + OPERATOR);
		//System.out.println("EXPRESSION: " + EXPRESSION);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(OPERATOR);
		toReturn.add(STATEMENT);
		toReturn.add(EXPRESSION);
		return toReturn;
	}
	
	public Object STATEMENT(Object NOT, Object leftParen, Object STATEMENT, Object rightParen) {
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(NOT);
		toReturn.add(STATEMENT);
		return toReturn;
	}
	
	public Object BOOLEXP(Object EXPRESSION) {
		//System.out.println("STAT EXPRESSION: " + EXPRESSION);
		return EXPRESSION;
	}

	
	public Object BOOLEXP(Object STATEMENT, Object OPERATOR, Object EXPRESSION) {
		//return "[" + OPERATOR + ", " + STATEMENT + ", " + EXPRESSION + "]";
		//return "[" + OPERATOR + " " + STATEMENT + " " + EXPRESSION + "]";
		//System.out.println("STATEMENT: " + STATEMENT);
		//System.out.println("OPERATOR: " + OPERATOR);
		//System.out.println("EXPRESSION: " + EXPRESSION);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(OPERATOR);
		toReturn.add(STATEMENT);
		toReturn.add(EXPRESSION);
		return toReturn;
	}
	
	public Object BOOLEXP(Object NOT, Object leftParen, Object STATEMENT, Object rightParen) {
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(NOT);
		toReturn.add(STATEMENT);
		return toReturn;
	}
	
	public Object STATEMENT2(Object STATEMENT, Object star) {
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(star);
		toReturn.add(STATEMENT);
		return toReturn;
	}
	
	public Object EXPRESSION(Object name, Object leftParen, Object ARG1, Object comma, Object ARG2, Object comma2, Object ARG3, Object rightParen) {
		//return "[" + name + ", " + ARG1 + ", " + ARG2 + "]";
		//return "[" + name + " " + ARG1 + " " + ARG2 + "]";
		//System.out.println("EXPR ARG1: " + ARG1);
		//System.out.println("EXPR ARG2: " + ARG2);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(name);
		toReturn.add(ARG1);
		toReturn.add(ARG2);
		toReturn.add(ARG3);
		return toReturn;
	}
	
	public Object EXPRESSION(Object name, Object leftParen, Object ARG1, Object comma, Object ARG2, Object rightParen) {
		//return "[" + name + ", " + ARG1 + ", " + ARG2 + "]";
		//return "[" + name + " " + ARG1 + " " + ARG2 + "]";
		//System.out.println("EXPR ARG1: " + ARG1);
		//System.out.println("EXPR ARG2: " + ARG2);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(name);
		toReturn.add(ARG1);
		String s = (String) comma;
		if (s.equals(",") == false) {
			toReturn.add(comma);
		}
		toReturn.add(ARG2);
		return toReturn;
	}
	
	public Object EXPRESSION(Object name, Object leftParen, Object ARG1, Object rightParen) {
		//return "[" + name + ", " + ARG1 + "]";
		//return "[" + name + " " + ARG1 + "]";
		//System.out.println("EXPR name: " + name);
		//System.out.println("EXPR ARG1: " + ARG1);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(name);
		toReturn.add(ARG1);
		return toReturn;
	}
	
	public Object EXPRESSION(Object star) {
		//return "[" + name + ", " + ARG1 + "]";
		//return "[" + name + " " + ARG1 + "]";
		//System.out.println("EXPR name: " + name);
		//System.out.println("EXPR ARG1: " + ARG1);
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(star);
		return toReturn;
	}
	
	public Object PARAMETERS(Object CHESSPIECE) {
		//return "[" + CHESSPIECE + "]";
		ArrayList<Object> toReturn = new ArrayList<Object>();
		//System.out.println("PARAM1 CHESSPIECE: " + CHESSPIECE);
		toReturn.add(CHESSPIECE);
		return toReturn;
	}
	
	public Object PARAMETERS(Object PARAMETERS, Object COMMA, Object CHESSPIECE) {
		//System.out.println("PARAM2 CHESSPIECE: " + CHESSPIECE);
		//System.out.println("PARAM2 PARAMETERS: " + PARAMETERS);
		ArrayList<Object> p = (ArrayList<Object>) PARAMETERS;
		p.add(CHESSPIECE);
		return p;
	}
	
	public Object VARPARAMETERS(Object VARCHESSPIECE) {
		ArrayList<Object> toReturn = new ArrayList<Object>();
		toReturn.add(VARCHESSPIECE);
		System.out.println("varparams toReturn: " + toReturn);
		return toReturn;
	}
	
	public Object VARPARAMETERS(Object VARPARAMETERS, Object COMMA, Object VARCHESSPIECE) {
		System.out.println("varparams VARPARAMETERS: " + VARPARAMETERS);
		ArrayList<Object> p = (ArrayList<Object>) VARPARAMETERS;
		p.add(VARCHESSPIECE);
		return p;
	}
	
	public Object CHESSPIECE(Object TYPE) {
		return TYPE;
	}
	
	public Object VARCHESSPIECE(Object TYPE, Object FACTOR) {
		String s = (String) TYPE + (String) FACTOR;
		System.out.println("VARCHESSPIECE: " + s);
		return s;
	}
	
	public Object COORDINATE(Object X, Object FACTOR) {
		String s = (String) X + (String) FACTOR;
		//System.out.println("s: " + s);
		return s;
	}
	
	public Object VARIABLE(Object AVARIABLE, Object letter) {
		String s = (String) AVARIABLE;
		s += (String) letter;
		return s;
	}
	
	public Object TYPE(Object piece) {
		String s = (String) piece;
		if (s.length() > 1) {
			String temp = s.substring(0, 1);
			if (temp.equals("p") || temp.equals("P")) {
				s = "1";
			} else if (temp.equals("r") || temp.equals("R")) {
				s = "2";
			} else if (temp.equals("n") || temp.equals("N")) {
				s = "3";
			} else if (temp.equals("b") || temp.equals("B")) {
				s = "4";
			} else if (temp.equals("q") || temp.equals("Q")) {
				s = "5";
			} else if (temp.equals("k") || temp.equals("K")) {
				s = "6";
			}
		} else { // it's only one char
			if (piece.equals("G")) {
				s = "g";
			} else if (piece.equals("D")) {
				s = "d";
			} else if (piece.equals("L")) {
				s = "l";
			}
		}
		return s;
	}	

	/** SYNTAX: java fri.patterns.interpreter.parsergenerator.examples.Calculator '(4+2.3) *(2 - -6) + 3*2' ... 56.4. */
	public static void main(String [] args)
		throws Exception
	{
		if (args.length <= 0)	{
			System.err.println("SYNTAX: java "+Joust.class.getName()+" \"(4+2.3) *(2 - -6) + 3*2\"");
			System.exit(1);
		}
		
		String input = args[0];
		for (int i = 1; i < args.length; i++)
			input = input+" "+args[i];

		System.err.println("Calculating input >"+input+"<");

		/*
		 * 
		Parser parser = new SerializedParser().get(rules2, "Joust");
		boolean ok = parser.parse(input, new Joust());
		System.err.println("Parse return "+ok+", result: "+parser.getResult());
		*/
		
		/* Variant without SerializedParser: */
		SyntaxSeparation separation = new SyntaxSeparation(new Syntax(rules));	// takes away IGNORED
		LexerBuilder builder = new LexerBuilder(separation.getLexerSyntax(), separation.getIgnoredSymbols());
		Lexer lexer = builder.getLexer(input);
		ParserTables parserTables = new LALRParserTables(separation.getParserSyntax());
		Parser parser = new Parser(parserTables);
		boolean ok = parser.parse(lexer, new Joust());
		System.err.println("Parse return "+ok+", result: "+parser.getResult());
		
	}
	
	
	public static ArrayList<Object> getAST(String program) throws Exception {
		//System.err.println("Calculating input >"+program+"<");
		ArrayList<Object> result = null;
		boolean ok = false;
		/* Variant without SerializedParser: */
		try {
			SyntaxSeparation separation = new SyntaxSeparation(new Syntax(rules));	// takes away IGNORED
			LexerBuilder builder = new LexerBuilder(separation.getLexerSyntax(), separation.getIgnoredSymbols());
			Lexer lexer = builder.getLexer(program);
			ParserTables parserTables = new LALRParserTables(separation.getParserSyntax());
			Parser parser = new Parser(parserTables);
			ok = parser.parse(lexer, new Joust());
			result = (ArrayList<Object>) parser.getResult();
			//System.err.println("Parse return "+ok+", result: "+result);
		} catch (Exception e) {
			//e.printStackTrace();
		}
		if (ok == false) {
			throw new Exception();
		}
		return result;
}

}
