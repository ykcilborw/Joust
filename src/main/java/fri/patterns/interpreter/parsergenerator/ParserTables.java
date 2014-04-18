package fri.patterns.interpreter.parsergenerator;

import java.util.List;
import java.util.Map;
import java.io.PrintStream;
import fri.patterns.interpreter.parsergenerator.syntax.Syntax;

/**
	Responsibilities of (bottom-up) parser tables, that must provide:
	<ul>
		<li>all terminals, without EPSILON (for the Lexer)</li>
		<li>the syntax</li>
		<li>the follow-state from the GOTO-table</li>
		<li>the follow action from the PARSE-ACTION-table</li>
		<li>a list of expected terminals</li>
	</ul>

	@author (c) 2000, Fritz Ritzberger
*/

public interface ParserTables
{
	/** Special symbol occuring in a parser table, 0, means: syntax was correct when coming to that cell. */
	public static final Integer ACCEPT = new Integer(0);
	
	/** Special symbol occuring in a parser table, -1, means: error when coming to that cell. */
	public static final Integer ERROR = new Integer(-1);

	/** Special symbol occuring in a parser table, -2, means: read next token. */
	public static final Integer SHIFT = new Integer(-2);
	

	/**
		Returns the next state from the GOTO-table, for a given state and a received terminal or nonterminal.
		@param currentState the current parser state
		@param symbol recently received terminal or nonterminal
		@return new parser state
	*/
	public Integer getGotoState(Integer currentState, String symbol);

	/**
		Returns the action from the PARSE-ACTION-table, for a given state and received terminal.
		fuer einen gegebenen Zustand und ein Terminal.
		@param currentState the current parser state
		@param terminal recently received terminal
		@return new action to proceed, SHIFT, ACCEPT, ERROR, or anything above zero, meaning REDUCE.
	*/
	public Integer getParseAction(Integer currentState, String terminal);

	/**
		Returns the List of treminals, without EPSILON.
	*/
	public List getTerminals();
	
	/**
		Returns the input syntax.
	*/
	public Syntax getSyntax();

	/**
		Dumps rules, GOTO-table, PARSE-ACTION-table, FIRST and FOLLOW sets, ...
	*/
	public void dump(PrintStream out);
	
	/**
		The keySet of returned Map contains all expected terminals for the current state.
		These can be used as Lexer hints.
	*/
	public Map getExpected(Integer state);

}
