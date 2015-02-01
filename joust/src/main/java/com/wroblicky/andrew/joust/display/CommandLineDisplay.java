package com.wroblicky.andrew.joust.display;

import java.util.Scanner;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.Game;
import com.wroblicky.andrew.joust.game.PGNViewer;
import com.wroblicky.andrew.joust.game.board.ChessBoardIterator;
import com.wroblicky.andrew.joust.game.board.Location;

/**
 * Handles displaying the program to a terminal
 *
 * @author Andrew Wroblicky
 *
 */
public final class CommandLineDisplay {

	private Game game;
	private final PGNViewer pgnViewer;

	private static final String NEXT = "next";
	private static final String PREVIOUS = "previous";
	private static final String EXIT = "exit";

	public CommandLineDisplay(Game game, PGNViewer pgnViewer, String mode) {
		this.game = game;
		this.pgnViewer = pgnViewer;
		if (mode.equals(DisplayConstants.INTERACTIVE_MODE)) {
			runInteractiveMode();
		} else {
			runNonInteractiveMode();
		}
	}

	/**
	 * Runs the program with user input from the terminal
	 */
	private void runInteractiveMode() {
		Util.println("Welcome to Joust");
		Util.println("Type 'next' to advance the game by one turn,"
				+ " 'previous' to undo a turn," + " and 'exit' to leave\n");

		// run main display loop
		String nextInstruction = "";
		try (Scanner scanner = new Scanner(System.in)) {
			while (!nextInstruction.equalsIgnoreCase(EXIT)) {
				Util.println("Round: " + game.getRound());
				displayBoard();
				nextInstruction = scanner.nextLine();
				game = evaluateNextInstruction(nextInstruction);
			}
		}
	}

	/**
	 * Immediately traverses the entire chess game without waiting for user
	 * input
	 */
	private void runNonInteractiveMode() {
		// run main display loop
		while (game.isInProgress()) {
			Util.println("Round: " + game.getRound());
			displayBoard();
			game = pgnViewer.playNextTurn();
		}
	}

	/**
	 * Renders the current state of the chess game to the terminal
	 */
	private void displayBoard() {
		// TODO make this more efficient

		// initialize print board to all unoccupied
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; // denotes unoccupied
			}
		}

		// add chess pieces
		ChessBoardIterator chessBoardIterator = game.getBoard().iterator();
		while (chessBoardIterator.hasNext()) {
			Location current = chessBoardIterator.next();
			if (current.getChessPiece() != null) {
				board[current.getXCoordinate()][current.getYCoordinate()] = current
						.getChessPiece().getChessPieceAllegianceType()
						.getChessPieceAllegianceType();
			}
		}

		// print out board to console
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				Util.print(board[i][j] + " ");
			}
			Util.println("");
		}
		Util.println("");
	}

	/**
	 * Evaluates the user's input and updates the game state
	 */
	private Game evaluateNextInstruction(String nextCommand) {
		if (nextCommand.equalsIgnoreCase(NEXT)) {
			return pgnViewer.playNextTurn();
		} else if (nextCommand.equalsIgnoreCase(PREVIOUS)) {
			return pgnViewer.undoCurrentTurn();
		} else if (!nextCommand.equalsIgnoreCase(EXIT)) {
			Util.print("Unknown command: " + nextCommand);
			return this.game;
		} else {
			return this.game;
		}
	}

	public static void start(Game game, PGNViewer pgnViewer, String mode) {
		new CommandLineDisplay(game, pgnViewer, mode);
	}
}