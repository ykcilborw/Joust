package com.wroblicky.andrew.joust.core.game;

import static com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType.BLACK_KING;
import static com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType.BLACK_ROOK;
import static com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType.WHITE_KING;
import static com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType.WHITE_ROOK;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.move.Move;
import com.wroblicky.andrew.joust.core.move.Turn;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;

/**
 * Manages the parsing of a move token and the subsequent logic of then updating the game
 * appropriately
 * 
 * @author Andrew Wroblicky
 *
 */
public class MoveTokenAnalyzer {
	
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
		System.out.println("Next move: " + moveToken);
		if (moveToken.equals("1/2-1/2") || moveToken.equals("1-0") ||
				moveToken.equals("0-1")) {
			gameManager.handleGameOver();
		} else if (moveToken.length() == 2) {
			handleLengthTwoMove(moveToken);
		} else if(moveToken.length() == 3){
			handleLengthThreeMove(moveToken);
		} else if(moveToken.length() == 4){
			handleLengthFourMove(moveToken);
		} else if (moveToken.length() == 5) {
			handleLengthFiveMove(moveToken);
		} else {// not sure what this is
			throw new RuntimeException("Unknown PGN move: " + moveToken);
		}
	}
	
	private void handleLengthTwoMove(String moveToken) {
		ChessPiece chessPiece = determineChessPiece(moveToken);
		Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				gameManager.getBoard().getLocation(moveToken)));
		gameManager.updateBoard(turn);
	}
	
	private void handleLengthThreeMove(String moveToken) {
		// could be a pawn check
		if (moveToken.substring(2, 3).equals("+") || moveToken.substring(2, 3).equals("#")) {
			handleLengthThreeCheck(moveToken);
		} else if (moveToken.equals("O-O")) {
			handleKingSideCastle();
		} else {
			String piece = moveToken.substring(0, 1);
			String move = moveToken.substring(1, 3);
			ChessPiece chessPiece = determineChessPiece(move, piece);
			Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
					gameManager.getBoard().getLocation(move)));
			gameManager.updateBoard(turn);
		}
	}
	
	private void handleLengthFourMove(String moveToken) {
		if (moveToken.substring(1, 2).equals("x")) {
			handleLengthFourCapture(moveToken);
		} else if (moveToken.contains("+") || moveToken.contains("#")){
			handleLengthFourCheck(moveToken);
		} else {
			String piece = moveToken.substring(0, 1);
			String file = moveToken.substring(1, 2);
			String move = moveToken.substring(2, 4);
			ChessPiece chessPiece = determineFileChessPiece(file, piece, move);
			Turn turn = new Turn(new Move(chessPiece, chessPiece.getLocation(), 
					gameManager.getBoard().getLocation(move)));
			gameManager.updateBoard(turn);
		}
		// TODO Implement pawn promotion
	}
	
	private void handleLengthFiveMove(String moveToken) {
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
	
	private ChessPiece determineChessPiece(String algebraicDestination) {
		// must be a pawn
		return determineChessPiece(algebraicDestination, gameManager.isWhiteTurn() ? "P" : "p");
	}
	
	private ChessPiece determineChessPiece(String algebraicDestination, String piece) {
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
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
	
	// helper method that converts a character representing a chess piece 
	// to the appropriate ChessPieceAllegianceType enum
	private ChessPieceAllegianceType convert(String chessPieceSymbol) {
		String enumRepresentation = enumLookup.get(chessPieceSymbol);
		return ChessPieceAllegianceType.valueOf(enumRepresentation);
	}
	
	private ChessPiece determineFileChessPiece(String file, String piece, 
			String algebraicDestination) {
		Character c = file.charAt(0);
		if (Character.isDigit(c)) {
			return determineRankChessPiece(file, piece, algebraicDestination);
		} else {
			return determineFileChessPieceHelper(file, piece, algebraicDestination);
		}
	}
	
	private ChessPiece determineFileChessPieceHelper(String file, String piece, 
			String algebraicDestination) {
		// setup
		ChessPiece reachablePiece = null;
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
		
		// loop over suspects
		Set<ChessPiece> suspects = gameManager.getChessPieces(convert(piece));
		for (ChessPiece suspect : suspects) {
			if (suspect.getFile().equals(file) && suspect.canReach(destination)) {
				reachablePiece = suspect;
				break;
			}
		}
		
		// throw error if nothing found
		if (reachablePiece == null) {
			throw new RuntimeException("Improperly formatted PGN file. " +
					"Could not find a piece that could've legally moved to " 
					+ algebraicDestination);
		}
		
		// return result
		return reachablePiece;
	}
	
	private ChessPiece determineRankChessPiece(String rank, String piece,
			String algebraicDestination) {
		ChessPiece icanReach = null;
		Location destination = gameManager.getBoard().getLocation(algebraicDestination);
		piece = gameManager.isWhiteTurn() ? piece : piece.toLowerCase();
		// white's turn
		Set<ChessPiece> suspects = gameManager.getChessPieces(convert(piece));
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
		gameManager.updateBoard(turn);
	}
	
	// utility method to find a piece used in castling
	private ChessPiece fetchChessPiece(ChessPieceAllegianceType chessPieceAllegianceType,
			String initialPosition) {
		ChessPiece toReturn = null;
		Set<ChessPiece> kings = gameManager.getChessPieces(chessPieceAllegianceType);
		for (ChessPiece chessPiece : kings) {
			if (chessPiece.getLocation().getAlgebraicLocation().equals(initialPosition)) {
				toReturn = chessPiece;
			}
		}
		if (toReturn == null) {
			throw new RuntimeException("fetchChessPiece could not find the corresponding chess piece");
		}
		return toReturn;
	}
	
	private void handleLengthFourCapture(String moveToken) {
		String type = moveToken.substring(0, 1);
		String algebraicDestination = moveToken.substring(2, 4);
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
			ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
			gameManager.handleCapture(algebraicDestination, chessPiece);
		}
	}
	
	private void handleLengthThreeCheck(String moveToken) {
		// parse relevant pieces
		String algebraicDestination = moveToken.substring(0, 2);
		String checkType = moveToken.substring(2, 3);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(algebraicDestination);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFourCheck(String moveToken) {
		// parse relevant pieces
		String type = moveToken.substring(0, 1);
		String algebraicDestination = moveToken.substring(1, 3);
		String checkType = moveToken.substring(3, 4);
		
		// handle
		ChessPiece chessPiece = determineChessPiece(algebraicDestination, type);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
	
	private void handleLengthFiveCheck(String moveToken) {
		// parse relevant pieces
		String type = moveToken.substring(0, 1);
		String file = moveToken.substring(1, 2);
		String algebraicDestination = moveToken.substring(2, 4);
		String checkType = moveToken.substring(4, 5);
		
		// handle
		ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
		gameManager.handleCheck(algebraicDestination, checkType, chessPiece);
	}
}