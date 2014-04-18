package com.wroblicky.andrew.joust.core.general;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Converts the moves described in a pgn text file into a
 * list of string tokens
 * 
 * @author Andrew Wroblicky
 *
 */
public class PGNParser {
	
	
	public static ArrayList<String> getGameMoves(String pgnTextFile) {
		ArrayList<String> moves = new ArrayList<String>();
		String next = null;
		
		try {
			BufferedReader in = new BufferedReader(new FileReader(pgnTextFile));
			while ((next = in.readLine()) != null) {
				if (next.length() > 0 && !(next.substring(0, 1).equals("["))) {
					String next2 = next.replaceAll("\\{[^\\}]*\\}", "");
					StringTokenizer st = new StringTokenizer(next2, ".");
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						if (Character.isDigit(token.charAt(0)) != true && (!token.substring(0,1).equals("{")) && (!token.substring(0,1).equals("}")) && !(token.matches("\\s"))) {
							StringTokenizer st2 = new StringTokenizer(token);
							while (st2.hasMoreTokens()) {
								String token2 = st2.nextToken();
								Pattern p = Pattern.compile( "([0-9]*)" );
								Matcher m = p.matcher(token2);
								if (!(m.matches())) {
									moves.add(token2);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.err.println("An error occurred trying to read the pgnTextFile");
			System.err.println("Error: " + e.getMessage());
			System.exit(-1);
		}
		return moves;
	}
}