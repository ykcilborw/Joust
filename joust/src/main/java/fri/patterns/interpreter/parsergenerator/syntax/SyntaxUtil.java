package fri.patterns.interpreter.parsergenerator.syntax;

import java.util.*;

/**
	Utilities in conjunction with Syntax and its input structures.

	@author (c) 2002 Fritz Ritzberger
*/

public abstract class SyntaxUtil
{
	/** Converts syntax String array of arrays to List of Lists. */
	public static List ruleArrayToList(String [][] array)	{
		return appendToSyntax(array, new ArrayList(array.length));
	}

	/** Converts rule String array to List. */
	public static List ruleToList(String [] rule)	{
		return appendToRule(rule, new ArrayList(rule.length));
	}

	/** Appends the syntax array to receiving List. Does not check for uniqueness. Receiver list must not be null. */
	public static List appendToSyntax(String [][] arrays, List receiver)	{
		for (int i = 0; i < arrays.length; i++)
			receiver.add(appendToRule(arrays[i], new ArrayList(arrays[i].length)));
		return receiver;
	}

	/** Appends the rule array to receiving List. Receiver list must not be null. */
	public static List appendToRule(String [] array, List receiver)	{
		for (int i = 0; i < array.length; i++)
			receiver.add(array[i]);
		return receiver;
	}

	/**
		Build together some syntaxes to one List of Lists. Does not check for uniqueness.
	*/
	public static List catenizeRules(String [][][] arrays)	{
		return catenizeRules(arrays, false);
	}
	
	/**
		Build together some syntaxes to one List of Lists. Checks for uniqueness
		(this is not really needed as Syntax checks for uniqueness on construction or when adding rules).
	*/
	public static final List catenizeRulesUnique(String [][][] arrays)	{
		return catenizeRules(arrays, true);
	}
	
	private static final List catenizeRules(String [][][] arrays, boolean checkUnique)	{
		int len = 0;
		for (int i = 0; i < arrays.length; i++)	// count global length
			len += arrays[i].length;
			
		List grammar = new ArrayList(len);
		for (int i = 0; i < arrays.length; i++)	{
			for (int j = 0; j < arrays[i].length; j++)	{
				List rule = new ArrayList(arrays[i][j].length);
				for (int k = 0; k < arrays[i][j].length; k++)
					rule.add(arrays[i][j][k]);
				
				if (checkUnique == false || grammar.indexOf(rule) < 0)
					grammar.add(rule);
			}
		}
		return grammar;
	}


	/** Mask backslash and double quote by backslash. */
	public static String maskQuoteAndBackslash(String s)	{
		StringBuffer sb = new StringBuffer(s.length());
		for (int i = 0; i < s.length(); i++)	{
			char c = s.charAt(i);
			switch (c)	{
				case '"':
				case '\\':
					sb.append('\\');
					break;
			}
			sb.append(c);
		}
		return sb.toString();
	}


	private SyntaxUtil()	{}

}