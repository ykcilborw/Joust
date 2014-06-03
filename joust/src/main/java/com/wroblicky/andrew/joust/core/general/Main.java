package com.wroblicky.andrew.joust.core.general;

/**
 * A simple wrapper that provides different options for interacting with the
 * Joust library.
 * 
 * Different available options:
 * 
 * [pgn file] -- iterates over the entire game
 * 
 * [pgn file] [joust search string] -- searches for matching boards given the Joust search params
 * 
 * [pgn file] [initial chessboard config] [joust search string] -- uses a custom default initial setting
 * 
 * 
 * @author Andrew Wroblicky
 *
 */
public class Main {
	
	public static void main(String[] args) {
		if (args.length > 3 || args.length <= 0) {
			System.out.println("Invalid number of args");
			System.exit(1);
		}
		if (args.length == 1) {
			String chessGame = args[0];
			ChessBoardMatcher j = new ChessBoardMatcher(chessGame);
			boolean foundMatch = j.find("*"); // match everything
			System.out.println("foundMatch: " + foundMatch);
		} else if (args.length == 2) {
			String chessGame = args[0];  //a pgn file
			String joustProgram = args[1];
			ChessBoardMatcher j = new ChessBoardMatcher(chessGame);
			boolean foundMatch = j.find(joustProgram);
			System.out.println("foundMatch: " + foundMatch);
		} else { // == 3
			String chessGame = args[0];  //a pgn file
			String initialConfig = args[1];
			String joustProgram = args[2];
			ChessBoardMatcher j = new ChessBoardMatcher(chessGame, initialConfig, false);
			boolean foundMatch = j.find(joustProgram);
			System.out.println("foundMatch: " + foundMatch);
		}
	}
	
	// for debugging purposes
	public static boolean run(String pgnFile) {
		ChessBoardMatcher j = new ChessBoardMatcher(pgnFile);
		return j.find("*");
	}
	
	// for debugging purposes
	public static boolean run(String pgnFile, String joustProgram) {
		ChessBoardMatcher j = new ChessBoardMatcher(pgnFile);
		return j.find(joustProgram);
	}
	
	// for debugging purposes
	public static boolean run(String pgnFile, String initialConfig, String joustProgram) {
		ChessBoardMatcher j = new ChessBoardMatcher(pgnFile, initialConfig);
		return j.find(joustProgram);
	}
}