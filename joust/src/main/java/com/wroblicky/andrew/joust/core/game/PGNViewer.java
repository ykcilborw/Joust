package com.wroblicky.andrew.joust.core.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.CastleMove;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.core.qualifiable.Scope;
import com.wroblicky.andrew.joust.pgn.PGNGame;
import com.wroblicky.andrew.joust.pgn.PGNParser;


public final class PGNViewer {
	
	private GameManager gameManager;
	private ListIterator<String> moves;
	private final Map<String, String> enumLookup = new HashMap<String, String>();
	
	
	public PGNViewer(PGNGame pgnGame) {
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupDefaultGame();
		initialize();
	}
	
	public PGNViewer(PGNGame pgnGame, String initialConfig) {
		this.moves = pgnGame.getMoves().listIterator();
		this.gameManager = GameSetup.setupSpecialLayout(initialConfig);
		initialize();
	}
	
	private void initialize() {
		enumLookup.put("p", "BLACK_PAWN");
		enumLookup.put("r", "BLACK_ROOK");
		enumLookup.put("n", "BLACK_KNIGHT");
		enumLookup.put("b", "BLACK_BISHOP");
		enumLookup.put("q", "BLACK_QUEEN");
		enumLookup.put("k", "BLACK_KING");
		enumLookup.put("P", "WHITE_PAWN");
		enumLookup.put("R", "WHITE_ROOK");
		enumLookup.put("N", "WHITE_KNIGHT");
		enumLookup.put("B", "WHITE_BISHOP");
		enumLookup.put("Q", "WHITE_QUEEN");
		enumLookup.put("K", "WHITE_KING");
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
			play(moves.next());
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
			undo(moves.previous());
			return gameManager.getGame();
		} else { // nothing to do
			return gameManager.getGame();
		}
	}
	
	private void play(String move) {
		analyzeMove(move);
	}
	
	private void undo(String move) {
		analyzeMove(move);
	}
		
	private void analyzeMove(String currentMove) {
		//System.out.println("curentMove: " + currentMove);
		if (currentMove.equals("1/2-1/2") || currentMove.equals("1-0") ||
				currentMove.equals("0-1")) {
			gameManager.handleGameOver();
		} else if (currentMove.length() == 2) {
			handleLengthTwoMove(currentMove);
		} else if(currentMove.length() == 3){
			handleLengthThreeMove(currentMove);
		} else if(currentMove.length() == 4){
			handleLengthFourMove(currentMove);
		} else if (currentMove.length() == 5) {
			handleLengthFiveMove(currentMove);
		} else {// not sure what this is
			throw new RuntimeException("Unknown PGN move: " + currentMove);
		}
	}
	
	private void handleLengthTwoMove(String move) {
		ChessPiece chessPiece = determineChessPiece(move);
		gameManager.updateBoard(move, chessPiece);
	}
	
	private void handleLengthThreeMove(String currentMove) {
		// could be a pawn check
		if (currentMove.substring(2, 3).equals("+") || currentMove.substring(2, 3).equals("#")) {
			handleLengthThreeCheck(currentMove);
		} else if (currentMove.equals("O-O")) {
			handleKingSideCastle();
		} else {
			String piece = currentMove.substring(0, 1);
			String move = currentMove.substring(1, 3);
			ChessPiece c = determineChessPiece(move, piece);
			gameManager.updateBoard(move, c);
		}
	}
	
	private void handleLengthFourMove(String currentMove) {
		if (currentMove.substring(1, 2).equals("x")) {
			handleLengthFourCapture(currentMove);
		} else if (currentMove.contains("+") || currentMove.contains("#")){
			handleLengthFourCheck(currentMove);
		} else {
			String piece = currentMove.substring(0, 1);
			String file = currentMove.substring(1, 2);
			String move = currentMove.substring(2, 4);
			ChessPiece c = determineFileChessPiece(file, piece, move);
			gameManager.updateBoard(move, c);
		}
		// TODO Implement pawn promotion
	}
	
	private void handleLengthFiveMove(String currentMove) {
		if (currentMove.contains("x")) {
			handleLengthFiveCapture(currentMove);
		} else if (currentMove.equals("O-O-O")) { 
			handleQueenSideCastle();
		} else {
			if (currentMove.substring(4, 5).equals("+") || currentMove.substring(4, 5).equals("#")) {
				handleLengthFiveCheck(currentMove);
			} else {
				// TODO
				// could be generic move specifying moving piece, its file, its rank, and where went
				// also could be pawn promotion with check/checkmate
				throw new RuntimeException("Unknown length five move: " + currentMove);
			}
		}
	}
	
	private ChessPiece determineChessPiece(String move) {
		// must be a pawn
		return determineChessPiece(move, gameManager.isWhiteTurn() ? "P" : "p");
	}
	
	private ChessPiece determineChessPiece(String move, String piece) {
		Location destination = gameManager.getBoard().getLocation(move);
		return evaluateSuspects(piece, destination);
	}
	
	private ChessPiece evaluateSuspects(String piece, Location destination) {
		// get suspects
		piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
		Set<ChessPiece> suspects = gameManager.getChessPieces(
				convert(piece)); 
		
		// evaluate
		for (ChessPiece suspect : suspects) {
			if (suspect.canReach(destination)) {
				return suspect;
			}
		}
		
		// eventually will want to catch and display message to end user
		throw new RuntimeException("Improperly formatted PGN file. " +
				"Could not find a piece that could've legally moved to " 
				+ destination);
	}
	
	private ChessPieceAllegianceType convert(String s) {
		String lookup = enumLookup.get(s);
		return ChessPieceAllegianceType.valueOf(lookup);
	}
	
	private ChessPiece determineFileChessPiece(String file, String piece, String move) {
		ChessPiece icanReach = null;
		Location l = gameManager.getBoard().getLocation(move);
		Character c = file.charAt(0);
		if (Character.isDigit(c)) {
			icanReach = determineRankChessPiece(file, piece, move);
		} else {
			piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
			Set<ChessPiece> suspects = gameManager.getChessPieces(
					ChessPieceAllegianceType.valueOf(enumLookup.get(piece)));
			for (ChessPiece suspect : suspects) {
				if (suspect.getFile().equals(file) && suspect.canReach(l)) {
					icanReach = suspect;
					break;
				}
			}
			if (icanReach == null) {
				throw new RuntimeException("Improperly formatted PGN file. " +
						"Could not find a piece that could've legally moved to " + move);
			}
		}
		return icanReach;
	}
	
	private ChessPiece determineRankChessPiece(String rank, String piece,
			String algebraicDestination) {
		ChessPiece icanReach = null;
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
		// white's turn
		Set<ChessPiece> suspects = gameManager.getChessPieces(
				ChessPieceAllegianceType.valueOf(enumLookup.get(piece)));
		for (ChessPiece suspect : suspects) {
			if (suspect.getRank().equals(rank) && suspect.canReach(destination)) {
				icanReach = suspect;
				break;
			}
		}
		if (icanReach == null) {
			throw new RuntimeException("Improperly formatted PGN file. " +
					"Could not find a piece that could've legally moved to " + algebraicDestination);
		}
		return icanReach;
	}
	
	private void handleKingSideCastle() {
		if (gameManager.isWhiteTurn()) {
			// white's turn
			Set<ChessPiece> kings = gameManager.getChessPieces(
					ChessPieceAllegianceType.WHITE_KING);
			ChessPiece king = (ChessPiece) kings.toArray()[0];
			Location l = gameManager.getBoard().getLocation("g1");
			king.move(l);
			Set<ChessPiece> rooks = gameManager.getChessPieces(
					ChessPieceAllegianceType.WHITE_ROOK);
			ChessPiece r = null;
			for (ChessPiece rook : rooks) {
				if (rook.getLocation().getAlgebraicLocation().equals("h1")) {
					r = rook;
				}
			}
			gameManager.updateBoard("f1", r);
		} else {
			Set<ChessPiece> kings = gameManager.getChessPieces(ChessPieceAllegianceType.BLACK_KING);
			ChessPiece k = (ChessPiece) kings.toArray()[0];
			Location l = new Location("g8");
			k.move(l);
			Set<ChessPiece> rooks = gameManager.getChessPieces(ChessPieceAllegianceType.BLACK_ROOK);
			ChessPiece r = null;
			for (ChessPiece rook : rooks) {
				if (rook.getLocation().getAlgebraicLocation().equals("h8")) {
					r = rook;
				}
			}
			gameManager.updateBoard("f8", r);
		}
	}
	
	private void handleQueenSideCastle() {
		if (gameManager.isWhiteTurn()) {
			// white's turn
			Set<ChessPiece> kings = gameManager.getChessPieces(ChessPieceAllegianceType.WHITE_KING);
			ChessPiece k = (ChessPiece) kings.toArray()[0];
			Location l = new Location("c1");
			k.move(l);
			Set<ChessPiece> rooks = gameManager.getChessPieces(ChessPieceAllegianceType.WHITE_ROOK);
			ChessPiece r = null;
			for (ChessPiece rook : rooks) {
				if (rook.getLocation().equals(new Location("a1"))) {
					r = rook;
				}
			}
			gameManager.updateBoard("d1", r);
		} else {
			Set<ChessPiece> kings = gameManager.getChessPieces(ChessPieceAllegianceType.BLACK_KING);
			ChessPiece k = (ChessPiece) kings.toArray()[0];
			Location l = new Location("c8");
			k.move(l);
			Set<ChessPiece> rooks = gameManager.getChessPieces(ChessPieceAllegianceType.BLACK_ROOK);
			ChessPiece r = null;
			for (ChessPiece rook : rooks) {
				if (rook.getLocation().equals(new Location("a8"))) {
					r = rook;
				}
			}
			gameManager.updateBoard("d8", r);
		}
	}
	
	private void handleLengthFourCapture(String currentMove) {
		String type = currentMove.substring(0, 1);
		String algebraicDestination = currentMove.substring(2, 4);
		ChessPiece chessPiece = null;
		if (Character.isLowerCase(type.charAt(0))) {
			// a pawn moved
			String file = type;
			if (gameManager.isWhiteTurn()) {
				chessPiece = determineFileChessPiece(file, "P", algebraicDestination);
			} else {
				chessPiece = determineFileChessPiece(file, "p", algebraicDestination);
			}
		} else {
			// a normal piece moved
			chessPiece = determineChessPiece(algebraicDestination, type);
		}
		gameManager.handleCapture(algebraicDestination, chessPiece);
	}
	
	// Captures with 5 chars!
	private void handleLengthFiveCapture(String currentMove) {
		if (currentMove.substring(4, 5).equals("+") || currentMove.substring(4, 5).equals("#")) {
			String checkorMate = currentMove.substring(4, 5);
			String capture = currentMove.substring(0,4);
			handleLengthFourCapture(capture);
			if (checkorMate.equals("+")) {
				gameManager.getGame().setCheck(true);
			} else {
				gameManager.getGame().setCheckmate(true);
			}
		} else {
			// parse relevant pieces
			String type = currentMove.substring(0, 1);
			String file = currentMove.substring(1, 2);
			String algebraicDestination = currentMove.substring(3, 5);
			
			// a normal piece moved (pawns not possible!)
			ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
			gameManager.handleCapture(algebraicDestination, chessPiece);
		}
	}
	
	private void handleLengthThreeCheck(String currentMove) {
		// parse relevant pieces
		String algebraicDestination = currentMove.substring(0, 2);
		String checkType = currentMove.substring(2, 3);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(algebraicDestination);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFourCheck(String currentMove) {
		// parse relevant pieces
		String type = currentMove.substring(0, 1);
		String algebraicDestination = currentMove.substring(1, 3);
		String checkType = currentMove.substring(3, 4);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(algebraicDestination, type);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFiveCheck(String currentMove) {
		// parse relevant pieces
		String type = currentMove.substring(0, 1);
		String file = currentMove.substring(1, 2);
		String algebraicDestination = currentMove.substring(2, 4);
		String checkType = currentMove.substring(4, 5);
		
		// handle
		ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	// the PGN View program is self contained
	public static void main(String[] args) {
		if (args.length > 0) {
			String pgnGame = args[0];
			PGNViewer pgnViewer = new PGNViewer(PGNParser.getPGNGame(pgnGame));
			pgnViewer.initializeGame();
			while (pgnViewer.getGame().isInProgress()) {
				Game game = pgnViewer.playNextTurn();
				Util.print("Current Round: " + game.getRound() + "\n");
				game.getBoard().printBoard(game.getChessPieces(Scope.ACTIVE));
			}
		}
	}
}