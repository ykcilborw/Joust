package com.wroblicky.andrew.joust.game;

import java.util.ListIterator;

import com.wroblicky.andrew.joust.display.CommandLineDisplay;
import com.wroblicky.andrew.joust.display.DialogBoxDisplay;
import com.wroblicky.andrew.joust.display.DisplayConstants;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.pgn.PGNGame;
import com.wroblicky.andrew.joust.pgn.PGNParser;

/**
 * Represents the PGN viewing application
 *
 * @author Andrew Wroblicky
 *
 */
public final class PGNViewer {

	private final String filename;
	private final GameManager gameManager;
	private final ListIterator<String> moves;
	private final MoveTokenAnalyzer moveTokenAnalyzer;

	public PGNViewer(PGNGame pgnGame) {
		this.filename = pgnGame.getFilename();
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupDefaultGame();
		this.moveTokenAnalyzer = new MoveTokenAnalyzer(gameManager);
	}

	public PGNViewer(PGNGame pgnGame, String initialConfig) {
		this.filename = pgnGame.getFilename();
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupSpecialLayout(initialConfig);
		this.moveTokenAnalyzer = new MoveTokenAnalyzer(gameManager);
	}

	public String getFilename() {
		return filename;
	}

	public Game getGame() {
		return gameManager.getGame();
	}

	/**
	 * Returns the initial gameManager state object
	 */
	public Game initializeGame() {
		return gameManager.getGame();
	}

	/**
	 * Fast forwards to the end of the game
	 */
	public Game fastForward() {
		while (moves.hasNext()) {
			Turn turn = moveTokenAnalyzer.analyzeMove(moves.next());
			gameManager.playTurn(turn);
		}
		return gameManager.getGame();
	}

	/**
	 * Updates the gameManager state after another turn is played
	 */
	public Game playNextTurn() {
		if (moves.hasNext()) {
			Turn turn = moveTokenAnalyzer.analyzeMove(moves.next());
			gameManager.playTurn(turn);
			return gameManager.getGame();
		} else {
			return gameManager.getGame();
		}
	}

	/**
	 * Fast forwards to the end of the game
	 */
	public Game rewind() {
		while (moves.hasPrevious()) {
			moves.previous();
			gameManager.undoTurn();
		}
		return gameManager.getGame();
	}

	/**
	 * Brings back the gameManager state to what it was before the last turn was
	 * played
	 */
	public Game undoCurrentTurn() {
		if (moves.hasPrevious()) {
			moves.previous(); // still need to adjust iterator
			gameManager.undoTurn();
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
			Game game = pgnViewer.initializeGame();

			if (runType.equals("-n")) { // non-interactive mode
				runNonInteractiveMode(pgnViewer, game);
			} else if (runType.equals("-c")) {
				runCommandLineInteractiveMode(pgnViewer, game);
			} else if (runType.equals("-s")) {
				runSwingDisplay(pgnViewer, game);
			} else {
				System.err
				.print("An unknown command line option was specified: "
						+ args[1]);
			}
		} else {
			System.err.print("An invalid number of arguments were specified");
		}
	}

	private static void runNonInteractiveMode(PGNViewer pgnViewer, Game game) {
		CommandLineDisplay.start(game, pgnViewer,
				DisplayConstants.NONINTERACTIVE_MODE);
	}

	private static void runCommandLineInteractiveMode(PGNViewer pgnViewer,
			Game game) {
		CommandLineDisplay.start(game, pgnViewer,
				DisplayConstants.INTERACTIVE_MODE);
	}

	private static void runSwingDisplay(PGNViewer pgnViewer, Game game) {
		DialogBoxDisplay.start(game, pgnViewer);
	}
}