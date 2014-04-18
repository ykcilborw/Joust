package fri.patterns.interpreter.parsergenerator.examples;

import fri.patterns.interpreter.parsergenerator.*;
import fri.patterns.interpreter.parsergenerator.semantics.*;
import fri.patterns.interpreter.parsergenerator.builder.SerializedParser;

/**
	Calculator for arithmetic expressions, showing the elegance of ReflectSemantic.
	<p>
	Syntax: java fri.patterns.interpreter.parsergenerator.examples.Calculator '(4+2.3) *(2 - -6) + 3*2'
	
	@author Fritz Ritzberger
*/

public class Calculator extends ReflectSemantic
{
	private static String [][] rules = {	// arithmetic sample
		{ "EXPRESSION",   "TERM" },
		{ "EXPRESSION",   "EXPRESSION", "'+'", "TERM" },
		{ "EXPRESSION",   "EXPRESSION", "'-'", "TERM" },
		{ "TERM",   "FACTOR", },
		{ "TERM",   "TERM", "'*'", "FACTOR" },
		{ "TERM",   "TERM", "'/'", "FACTOR" },
		{ "FACTOR",   "`number`", },
		{ "FACTOR",   "'-'", "FACTOR" },	// need LALRParserTables instead of SLRParserTables because of this rule
		{ "FACTOR",   "'('", "EXPRESSION", "')'" },
		{ Token.IGNORED,   "`whitespaces`" },
	};
	
	public Object EXPRESSION(Object TERM)	{
		return TERM;	// do not really need this method as ReflectSemantic.fallback() does this
	}
	public Object EXPRESSION(Object EXPRESSION, Object operator, Object TERM)	{
		if (operator.equals("+"))
			return new Double(((Double) EXPRESSION).doubleValue() + ((Double) TERM).doubleValue());
		return new Double(((Double) EXPRESSION).doubleValue() - ((Double) TERM).doubleValue());
	}
	public Object TERM(Object FACTOR)	{
		return FACTOR;	// do not really need this method as ReflectSemantic.fallback() does this
	}
	public Object TERM(Object TERM, Object operator, Object FACTOR)	{
		if (operator.equals("*"))
			return new Double(((Double) TERM).doubleValue() * ((Double) FACTOR).doubleValue());
		return new Double(((Double) TERM).doubleValue() / ((Double) FACTOR).doubleValue());
	}
	public Object FACTOR(Object number)	{
		return Double.valueOf((String) number);
	}
	public Object FACTOR(Object minus, Object FACTOR)	{
		return new Double( - ((Double) FACTOR).doubleValue() );
	}
	public Object FACTOR(Object leftParenthesis, Object EXPRESSION, Object rightParenthesis)	{
		return EXPRESSION;
	}


	/** SYNTAX: java fri.patterns.interpreter.parsergenerator.examples.Calculator '(4+2.3) *(2 - -6) + 3*2' ... 56.4. */
	public static void main(String [] args)
		throws Exception
	{
		if (args.length <= 0)	{
			System.err.println("SYNTAX: java "+Calculator.class.getName()+" \"(4+2.3) *(2 - -6) + 3*2\"");
			System.exit(1);
		}
		
		String input = args[0];
		for (int i = 1; i < args.length; i++)
			input = input+" "+args[i];

		System.err.println("Calculating input >"+input+"<");

		Parser parser = new SerializedParser().get(rules, "Calculator");
		boolean ok = parser.parse(input, new Calculator());
		System.err.println("Parse return "+ok+", result: "+parser.getResult());
		
		/* Variant without SerializedParser:
		SyntaxSeparation separation = new SyntaxSeparation(new Syntax(rules));	// takes away IGNORED
		LexerBuilder builder = new LexerBuilder(separation.getLexerSyntax(), separation.getIgnoredSymbols());
		Lexer lexer = builder.getLexer(input);
		ParserTables parserTables = new LALRParserTables(separation.getParserSyntax());
		Parser parser = new Parser(parserTables);
		boolean ok = parser.parse(lexer, new Calculator());
		System.err.println("Parse return "+ok+", result: "+parser.getResult());
		*/
	}

}
