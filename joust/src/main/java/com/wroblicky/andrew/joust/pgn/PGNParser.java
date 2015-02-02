package com.wroblicky.andrew.joust.pgn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Converts the moves described in a PGN text file into a list of string tokens
 *
 * @author Andrew Wroblicky
 *
 */
public final class PGNParser {

	public static PGNGame getPGNGame(String pgnTextFile) {
		PGNGame pgnGame = new PGNGame();
		pgnGame.setFilename(new File(pgnTextFile).getName());
		List<String> moves = new ArrayList<String>();
		String next = null;

		try {
			BufferedReader in = new BufferedReader(new FileReader(pgnTextFile));
			while ((next = in.readLine()) != null) {
				if (next.length() > 0 && !(next.substring(0, 1).equals("["))) {
					String next2 = next.replaceAll("\\{[^\\}]*\\}", "");
					StringTokenizer st = new StringTokenizer(next2, ".");
					while (st.hasMoreTokens()) {
						String token = st.nextToken();
						if (Character.isDigit(token.charAt(0)) != true
								&& (!token.substring(0, 1).equals("{"))
								&& (!token.substring(0, 1).equals("}"))
								&& !(token.matches("\\s"))) {
							StringTokenizer st2 = new StringTokenizer(token);
							while (st2.hasMoreTokens()) {
								String token2 = st2.nextToken();
								Pattern p = Pattern.compile("([0-9]*)");
								Matcher m = p.matcher(token2);
								if (!(m.matches())) {
									moves.add(token2);
								}
							}
						}
					}
				} else if (next.length() > 0
						&& next.substring(0, 1).equals("[")) {
					String next2 = next.replaceAll("\\{[^\\}]*\\}", ""); // remove
					// brackets
					String[] tokens = next2.split(" ");
					String key = tokens[0].substring(1);
					String value = "";
					for (int i = 1; i < tokens.length - 1; i++) {
						value += tokens[i] + " ";
					}
					String last = tokens[tokens.length - 1];
					value += last.substring(0, last.length() - 1);
					value = stripQuotes(value);
					if (key.equalsIgnoreCase("Event")) {
						pgnGame.setEvent(value);
					} else if (key.equalsIgnoreCase("Site")) {
						pgnGame.setSite(value);
					} else if (key.equalsIgnoreCase("Date")) {
						pgnGame.setDate(value);
					} else if (key.equalsIgnoreCase("Round")) {
						pgnGame.setRound(value);
					} else if (key.equalsIgnoreCase("White")) {
						pgnGame.setWhite(value);
					} else if (key.equalsIgnoreCase("Black")) {
						pgnGame.setBlack(value);
					} else if (key.equalsIgnoreCase("Result")) {
						// not sure why this is necessary to remove dangling "
						// character
						// at end
						value = value.substring(0, value.length());
						pgnGame.setResult(value);
					}
				}
			}
		} catch (IOException e) {
			System.err
			.println("An error occurred trying to read the pgnTextFile");
			e.printStackTrace();
			System.exit(-1);
		}
		pgnGame.setMoves(moves);
		return pgnGame;
	}

	private static String stripQuotes(String s) {
		return s.trim().replaceAll("^\"|\"$", "").replaceAll("^'|'$", "");
	}
}