package com.wroblicky.andrew.joust.game;

import static com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType.BLACK_KING;
import static com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType.BLACK_ROOK;
import static com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType.WHITE_KING;
import static com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType.WHITE_ROOK;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.move.Termination;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;

/**
 * Manages the parsing of a move token and the subsequent logic of then updating the game
 * appropriately
 * 
 * @author Andrew Wroblicky
 *
 */
public final class MoveTokenAnalyzer {
	
	private GameManager gameManager;
	private final Map<String, String> enumLookup = new HashMap<String, String>();
	
	public MoveTokenAnalyzer(GameManager gameManager) {
		this.gameManager = gameManager;
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
	
	public void analyzeMove(String moveToken) {
		if (moveToken.equals("1/2-1/2") || moveToken.equals("1-0") ||
				moveToken.equals("0-1")) {
			handleGameOver(moveToken);
		} else if (moveToken.length() == 2) {
			evaluateLengthTwoMove(moveToken);
		} else if(moveToken.length() == 3){
			evaluateLengthThreeMove(moveToken);
		} else if(moveToken.length() == 4){
			evaluateLengthFourMove(moveToken);
		} else if (moveToken.length() == 5) {
			evaluateLengthFiveMove(moveToken);
		} else {// not sure what this is
			throw new RuntimeException("Unknown PGN move: " + moveToken);
		}
	}
	
	//**********************************************
	// Evaluation methods
	//**********************************************
	private void evaluateLengthTwoMove(String moveToken) {
		handleLengthTwoMove(moveToken);
	}
	
	private void evaluateLengthThreeMove(String moveToken) {
		// could be a pawn check
		if (moveToken.substring(2, 3).equals("+") || moveToken.substring(2, 3).equals("#")) {
			handleLengthThreeCheck(moveToken);
		} else if (moveToken.equals("O-O")) {
			handleKingSideCastle();
		} else {
			handleLengthThreeMove(moveToken);
		}
	}
	
	private void evaluateLengthFourMove(String moveToken) {
		if (moveToken.substring(1, 2).equals("x")) {
			handleLengthFourCapture(moveToken);
		} else if (moveToken.contains("+") || moveToken.contains("#")){
			handleLengthFourCheck(moveToken);
		} else {
			handleLengthFourMove(moveToken);
		}
		// TODO Implement pawn promotion
	}
	
	private void evaluateLengthFiveMove(String moveToken) {
		if (moveToken.contains("x")) {
			handleLengthFiveCapture(moveToken);
		} else if (moveToken.equals("O-O-O")) { 
			handleQueenSideCastle();
		} else {
			if (moveToken.substring(4, 5).equals("+") || moveToken.substring(4, 5).equals("#")) {
				handleLengthFiveCheck(moveToken);
			} else {
				// TODO
				// could be generic move specifying moving piece, its file, its rank, and where went
				// also could be pawn promotion with check/checkmate
				throw new RuntimeException("Unknown length five move: " + moveToken);
			}
		}
	}
	
	//**********************************************
	// Move Handler Methods
	//**********************************************
	private void handleLengthTwoMove(String algebraicLocation) {
		ChessPiece chessPiece = determineChessPiece(algebraicLocation);
		Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				gameManager.getBoard().getLocation(algebraicLocation)));
		gameManager.playTurn(turn);
	}
	
	private void handleLengthThreeMove(String moveToken) {
		String piece = moveToken.substring(0, 1);
		String move = moveToken.substring(1, 3);
		ChessPiece chessPiece = determineChessPiece(piece, move);
		Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				gameManager.getBoard().getLocation(move)));
		gameManager.playTurn(turn);
	}
	
	private void handleLengthFourMove(String moveToken) {
		String piece = moveToken.substring(0, 1);
		String file = moveToken.substring(1, 2);
		String move = moveToken.substring(2, 4);
		ChessPiece chessPiece = determineChessPiece(file, piece, move);
		Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				gameManager.getBoard().getLocation(move)));
		gameManager.playTurn(turn);
	}
	
	//*************************************************************
	//* Castle Handler Methods
	//*************************************************************
	private void handleKingSideCastle() {
		if (gameManager.isWhiteTurn()) {
			handleCastle(WHITE_KING, WHITE_ROOK, "e1", "g1", "h1", "f1");
		} else {
			handleCastle(BLACK_KING, BLACK_ROOK, "e8", "g8", "h8", "f8");
		}
	}
	
	private void handleQueenSideCastle() {
		if (gameManager.isWhiteTurn()) {
			handleCastle(WHITE_KING, WHITE_ROOK, "e1", "c1", "a1", "d1");
		} else {
			handleCastle(BLACK_KING, BLACK_ROOK, "e8", "c8", "a8", "d8");
		}
	}
	
	private void handleCastle(ChessPieceAllegianceType kingAllegianceType, 
			ChessPieceAllegianceType rookAllegianceType, String kingInitialPosition, 
			String kingAlgebraicDestination, String rookInitialPosition, 
			String rookAlgebraicDestination) {
		
		// handle king
		ChessPiece king = fetchChessPiece(kingAllegianceType, kingInitialPosition);
		Location kingDestination = gameManager.getBoard().getLocation(kingAlgebraicDestination);
		Turn turn = new Turn(new Move(king, king.getLocation(), kingDestination));
		
		// handle rook
		ChessPiece rook = fetchChessPiece(rookAllegianceType, rookInitialPosition);
		Location rookDestination = gameManager.getBoard().getLocation(rookAlgebraicDestination);
		turn.addMove(new Move(rook, rook.getLocation(), rookDestination));
		gameManager.playTurn(turn);
	}
	
	
	//*************************************************************
	//* Capture Handler Methods
	//*************************************************************
	private void handleLengthFourCapture(String moveToken) {
		String type = moveToken.substring(0, 1);
		String algebraicDestination = moveToken.substring(2, 4);
		ChessPiece chessPiece = null;
		if (Character.isLowerCase(type.charAt(0))) {
			// a pawn moved
			String file = type;
			if (gameManager.isWhiteTurn()) {
				chessPiece = determineChessPiece(file, "P", algebraicDestination);
			} else {
				chessPiece = determineChessPiece(file, "p", algebraicDestination);
			}
		} else {
			// a normal piece moved
			chessPiece = determineChessPiece(type, algebraicDestination);
		}
		handleCapture(algebraicDestination, chessPiece);
	}
	
	// Captures with 5 chars!
	private void handleLengthFiveCapture(String moveToken) {
		if (moveToken.substring(4, 5).equals("+") || moveToken.substring(4, 5).equals("#")) {
			String checkorMate = moveToken.substring(4, 5);
			String capture = moveToken.substring(0,4);
			handleLengthFourCapture(capture);
			if (checkorMate.equals("+")) {
				gameManager.getGame().setCheck(true);
			} else {
				gameManager.getGame().setCheckmate(true);
			}
		} else {
			// parse relevant pieces
			String type = moveToken.substring(0, 1);
			String file = moveToken.substring(1, 2);
			String algebraicDestination = moveToken.substring(3, 5);
			
			// a normal piece moved (pawns not possible!)
			ChessPiece chessPiece = determineChessPiece(file, type, algebraicDestination);
			handleCapture(algebraicDestination, chessPiece);
		}
	}
	
	private void handleCapture(String algebraicDestination, ChessPiece capturer) {
		// remove old piece
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		ChessPiece captured = gameManager.getBoard().getChessPieceByLocation(destination);
		// null destination means piece was captured
		Turn turn = new Turn(new Move(captured, captured.getLocation(), null)); 
		
		// update metadata
		turn.setCaptured(captured);
		turn.setCapturer(capturer);
		turn.addMove(new Move(capturer, capturer.getLocation(), 
				gameManager.getBoard().getLocation(algebraicDestination)));
		gameManager.playTurn(turn);
	}
	
	//*************************************************************
	//* Check Handler Methods
	//*************************************************************
	private void handleLengthThreeCheck(String moveToken) {
		// parse relevant pieces
		String algebraicDestination = moveToken.substring(0, 2);
		String checkType = moveToken.substring(2, 3);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(algebraicDestination);
		handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFourCheck(String moveToken) {
		// parse relevant pieces
		String type = moveToken.substring(0, 1);
		String algebraicDestination = moveToken.substring(1, 3);
		String checkType = moveToken.substring(3, 4);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(type, algebraicDestination);
		handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFiveCheck(String moveToken) {
		// parse relevant pieces
		String type = moveToken.substring(0, 1);
		String file = moveToken.substring(1, 2);
		String algebraicDestination = moveToken.substring(2, 4);
		String checkType = moveToken.substring(4, 5);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(file, type, algebraicDestination);
		handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleCheck(String algebraicDestination, String checkType,
			ChessPiece chessPiece) {
		boolean check = false;
		boolean checkMate = false;
		if (checkType.equals("+")) {
			check = true;
		} else {
			checkMate = true;
		}
		gameManager.playTurn(new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				gameManager.getBoard().getLocation(algebraicDestination)), check, checkMate));
	}
	
	//*************************************************************
	//* Game Over Handler Methods
	//*************************************************************
	private void handleGameOver(String moveToken) {
		Turn turn = new Turn(new Termination());
		gameManager.playTurn(turn);
	}
	
	//*************************************************************
	//* Chess Piece Determination Methods 
	//*************************************************************
	/**
	 * Determines the chess piece on the board given an algebraic destination
	 */
	private ChessPiece determineChessPiece(String algebraicDestination) {
		// must be a pawn
		return determineChessPiece("P", algebraicDestination);
	}
	
	/**
	 * Determines the chess piece on the board given an algebraic destination
	 * and the chess piece unit
	 */
	private ChessPiece determineChessPiece(String piece, String algebraicDestination) {
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		ChessPieceAllegianceType chessPieceAllegianceType = convert(
				gameManager.isWhiteTurn() ? piece : piece.toLowerCase());
		
		return evaluateSuspects(chessPieceAllegianceType, destination);
	}
	
	/**
	 * Finds a chess piece given a ChessPieceAllegianceType and a destination
	 * position
	 */
	private ChessPiece evaluateSuspects(ChessPieceAllegianceType chessPieceAllegianceType,
			Location destination) {
		// get suspects
		Set<ChessPiece> suspects = gameManager.getChessPieces(chessPieceAllegianceType); 
		
		// evaluate
		for (ChessPiece suspect : suspects) {
			if (suspect.canReach(destination)) {
				return suspect;
			}
		}
		
		throw new RuntimeException("Improperly formatted PGN file. " +
				"Could not find a piece that could've legally moved to " 
				+ destination);
	}
	
	/**
	 * Finds a chess piece given a ChessPieceAllegianceType and an initial position
	 */
	private ChessPiece fetchChessPiece(ChessPieceAllegianceType chessPieceAllegianceType,
			String initialPosition) {
		
		ChessBoard chessBoard = gameManager.getBoard();
		
		ChessPiece toReturn = chessBoard.getChessPieceByLocation(new Location(initialPosition));
		
		if (toReturn != null && toReturn.getChessPieceAllegianceType() == chessPieceAllegianceType) {
			return toReturn;
		} else {
			throw new RuntimeException("fetchChessPiece could not find the " +
					"corresponding chess piece " + chessPieceAllegianceType);
		}
	}
	
	/** 
	 *  Converts a character representing a chess piece 
	 *  to the appropriate ChessPieceAllegianceType enum 
	 */
	private ChessPieceAllegianceType convert(String chessPieceSymbol) {
		String enumRepresentation = enumLookup.get(chessPieceSymbol);
		return ChessPieceAllegianceType.valueOf(enumRepresentation);
	}
	
	private ChessPiece determineChessPiece(String file, String piece, 
			String algebraicDestination) {
		// setup
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
		Set<ChessPiece> suspects = gameManager.getChessPieces(convert(piece));
		
		Character c = file.charAt(0);
		if (Character.isDigit(c)) {
			return determineRankChessPiece(file, suspects, destination);
		} else {
			return determineFileChessPiece(file, suspects, destination);
		}
	}
	
	private ChessPiece determineFileChessPiece(String file, Set<ChessPiece> suspects, 
			Location destination) {
		// loop over suspects
		for (ChessPiece suspect : suspects) {
			if (suspect.getFile().equals(file) && suspect.canReach(destination)) {
				return suspect;
			}
		}
		
		// throw error if nothing found
		throw new RuntimeException("Improperly formatted PGN file. " +
				"Could not find a piece that could've legally moved to " 
				+ destination.getAlgebraicLocation());
	}
	
	private ChessPiece determineRankChessPiece(String rank, Set<ChessPiece> suspects, 
			Location destination) {
		// loop over suspects
		for (ChessPiece suspect : suspects) {
			if (suspect.getRank().equals(rank) && suspect.canReach(destination)) {
				return suspect;
			}
		}
		
		// throw error if nothing found
		throw new RuntimeException("Improperly formatted PGN file. " +
				"Could not find a piece that could've legally moved to "
				+ destination.getAlgebraicLocation());
	}
}