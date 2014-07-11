package com.wroblicky.andrew.joust.core.game;

import java.util.ListIterator;

import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.qualifiable.Scope;
import com.wroblicky.andrew.joust.core.ui.CommandLineDisplay;
import com.wroblicky.andrew.joust.core.ui.DialogBoxDisplay;
import com.wroblicky.andrew.joust.pgn.PGNGame;
import com.wroblicky.andrew.joust.pgn.PGNParser;

/**
 * Represents the PGN viewing application
 * 
 * @author Andrew Wroblicky
 *
 */
public final class PGNViewer {
	
	private GameManager gameManager;
	private ListIterator<String> moves;
	private MoveTokenAnalyzer moveTokenAnalyzer;
	
	
	public PGNViewer(PGNGame pgnGame) {
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupDefaultGame();
		this.moveTokenAnalyzer = new MoveTokenAnalyzer(gameManager);
	}
	
	public PGNViewer(PGNGame pgnGame, String initialConfig) {
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupSpecialLayout(initialConfig);
		this.moveTokenAnalyzer = new MoveTokenAnalyzer(gameManager);
	}
	
	public GameManager getGame() {
		return gameManager;
	}
	
	/**
	 * Returns the initial gameManager state object
	 */
	public Game initializeGame() {
		return gameManager.getGame();
	}
	
	/**
	 * Updates the gameManager state after another turn is played
	 */
	public Game playNextTurn() {
		if (moves.hasNext()) {
			moveTokenAnalyzer.analyzeMove(moves.next());
			return gameManager.getGame();
		} else {
			gameManager.handleGameOver();
			return gameManager.getGame();
		}
	}
	
	/**
	 * Brings back the gameManager state to what it was before the last turn was played
	 */
	public Game undoCurrentTurn() {
		if (moves.hasPrevious()) {
			moveTokenAnalyzer.analyzeMove(moves.previous());
			return gameManager.getGame();
		} else { // nothing to do
			return gameManager.getGame();
		}
	}
	
	// the PGN View program is self contained
	public static void main(String[] args) {
		if (args.length > 1) {
			String runType = args[0];
			String pgnGame = args[1];
			PGNViewer pgnViewer = new PGNViewer(PGNParser.getPGNGame(pgnGame));
			
			if (runType.equals("-n")) { // non-interactive mode
				runNonInteractiveMode(pgnViewer);
			} else if (runType.equals("-c")) {
				runCommandLineInteractiveMode(pgnViewer);
			} else if (runType.equals("-s")) {
				runSwingDisplay(pgnViewer);
			} else {
				System.err.print("An unknown command line option was specified: " + args[1]);
			}
		} else {
			System.err.print("An invalid number of arguments were specified");
		}
	}
	
	private static void runNonInteractiveMode(PGNViewer pgnViewer) {
		pgnViewer.initializeGame();
		while (pgnViewer.getGame().isInProgress()) {
			Game game = pgnViewer.playNextTurn();
			Util.print("Current Round: " + game.getRound() + "\n");
			game.getBoard().printBoard(game.getChessPieces(Scope.ACTIVE));
		}
	}
	
	private static void runCommandLineInteractiveMode(PGNViewer pgnViewer) {
		Game game = pgnViewer.initializeGame();
		CommandLineDisplay.start(game, pgnViewer);
	}
	
	private static void runSwingDisplay(PGNViewer pgnViewer) {
		Game game = pgnViewer.initializeGame();
		DialogBoxDisplay.start(game, pgnViewer);
	}
}