package com.wroblicky.andrew.joust.core.ui;

import java.util.Scanner;

import com.wroblicky.andrew.joust.core.board.ChessBoardIterator;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.game.Game;
import com.wroblicky.andrew.joust.core.game.PGNViewer;
import com.wroblicky.andrew.joust.core.general.Util;

public class CommandLineDisplay {
	
	private Game game;
	private PGNViewer pgnViewer;
	
	private static String NEXT = "next";
	private static String PREVIOUS = "previous";
	private static String EXIT = "exit";
	
	public CommandLineDisplay(Game game, PGNViewer pgnViewer) {
		this.game = game;
		this.pgnViewer = pgnViewer;
		run();
	}
	
	private void run() {
		Util.print("Welcome to Joust");
		Util.print("Type 'next' to advance the game by one turn, 'previous' to undo a turn," +
				" and 'exit' to leave");
		Util.print("\n");
		
		// run main display loop
		String nextInstruction = "";
		while (!nextInstruction.equalsIgnoreCase(EXIT)) {
			Util.print("Round: " + game.getRound());
			displayBoard();
			Scanner in = new Scanner(System.in);
			nextInstruction = in.nextLine();
			this.game = evaluateNextInstruction(nextInstruction);
		}
	}
	
	private void displayBoard() {
		// initialize print board to all unoccupied
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; //denotes unoccupied
			}
		}
		
		// add chess pieces
		ChessBoardIterator chessBoardIterator = game.getBoard().iterator();
		while (chessBoardIterator.hasNext()) {
			Location current = chessBoardIterator.next();
			if (current.getChessPiece() != null) {
				board[current.getXCoordinate()][current.getYCoordinate()] =
						current.getChessPiece().getMySymbol().getChessPieceAllegianceType();
			}
		}
		
		// print out board to console
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
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
	
	public static void start(Game game, PGNViewer pgnViewer) {
		new CommandLineDisplay(game, pgnViewer);
	}
}