package com.wroblicky.andrew.joust.core.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.CastleMove;
import com.wroblicky.andrew.joust.core.general.ChessPieceLookup;
import com.wroblicky.andrew.joust.core.general.ChessPieceSubsetManager;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.core.qualifiable.Qualifiable;
import com.wroblicky.andrew.joust.core.qualifiable.Scope;

public class GameManagerImpl {
	private Game game;
	private List<String[][]> visitedBoards; // debugging ONLY!!!
	private List<CastleMove> castleMoves; // for ChessDemoVisualizations ONLY!!
	private ChessPiece capturer;
	private ChessPiece captured;
	private ChessPieceLookup chessPieceLookup;
	private ChessPieceSubsetManager chessPieceSubsetManager;
	
	
	public GameManagerImpl(Game game, ChessPieceSubsetManager chessPieceSubsetManager) {
		this.game = game;
		visitedBoards = new ArrayList<String[][]>();
		castleMoves = new ArrayList<CastleMove>();
		chessPieceLookup = new ChessPieceLookup(chessPieceSubsetManager);
		this.chessPieceSubsetManager = chessPieceSubsetManager;
	}
	
	public int getRound() {
		return game.getRound();
	}
	
	public Set<ChessPiece> getActivePieces() {
		return chessPieceSubsetManager.getChessPieces(Scope.ACTIVE);
	}
	
	public Set<ChessPiece> getBlackPieces() {
		return chessPieceSubsetManager.getChessPieces(Scope.BLACK_ACTIVE);
	}
	
	public Set<ChessPiece> getWhitePieces() {
		return chessPieceSubsetManager.getChessPieces(Scope.WHITE_ACTIVE);
	}
	
	public Set<ChessPiece> getChessPieces(Qualifiable qualification) {
		return chessPieceSubsetManager.getChessPieces(qualification);
	}
	
	public ChessPieceLookup getChessPieceLookup() {
		return chessPieceLookup;
	}
	
	@Deprecated
	public void setRound(int round) {
		this.game.setRound(round);
	}
	
	public void update(List<String> myMoves) {
		capturer = null;
		captured = null;
		game.setCheck(false);
		game.setCheckmate(false);
		PGNMoveInterpreter pgnMoveInterpreter = new PGNMoveInterpreter(myMoves, game.getBoard());
		pgnMoveInterpreter.update();
	}
	
	public boolean getBlackCastle() {
		return game.isBlackCastleMoveAllowed();
	}
	
	public boolean getWhiteCastle() {
		return game.isWhiteCastleMoveAllowed();
	}
	
	public boolean isInProgress() {
		return game.isInProgress();
	}
	
	public ChessPiece getCapturer() {
		return capturer;
	}
	
	public ChessPiece getCaptured() {
		return captured;
	}
	
	public List<CastleMove> getCastleMoves() {
		return castleMoves;
	}
	
	public boolean getCheckOn() {
		return game.isCheck();
	}
	
	public boolean getCheckMateOn() {
		return game.isCheckmate();
	}
	
	public boolean isWhiteTurn() {
		return game.getRound() % 2 == 0;
	}
	
	public void printVisitedBoards() {
		FileWriter fstream = null;
		try {
			fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < visitedBoards.size(); i++) {
				int round = i;
				String[][] board = visitedBoards.get(i);
				out.write("Current Round: " + round + "\n");
					for (int j = 7; j > -1; j--) {
						for (int k = 0; k < 8; k++) {
							out.write(board[k][j] + " ");
						}
						out.write("\n");
					}
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Could not find file to print boards to");
			System.exit(-1);
		}
	}

	public void printBoard2(int round) {
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; //denotes unoccupied
			}
		}
		Set<ChessPiece> chessPieces = getActivePieces();
		for (ChessPiece chessPiece : chessPieces) {
			ChessPiece current = chessPiece;
			int currX = current.getLocation().getXCoordinate();
			int currY = current.getLocation().getYCoordinate();
			board[currX - 1][currY - 1] = current.getMySymbol().toString(); 
		}
		visitedBoards.add(board);
		
		System.out.print("Current Round: " + round + "\n");
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	public void removePiece(ChessPiece piece) {
		chessPieceSubsetManager.removeChessPiece(piece);
	}
	
	public class PGNMoveInterpreter {
		
		private List<String> moves;
		private ChessBoard chessBoard;
		
		public PGNMoveInterpreter(List<String> moves, ChessBoard chessBoard) {
			this.moves = moves;
			this.chessBoard = chessBoard;
		}
		
		public void update() {
			if (game.getRound() < moves.size()) {
				analyzeMove(moves.get(game.getRound()));
			} else {
				Util.print("Game Over!");
				System.exit(-1);
			}
		}
			
		private void analyzeMove(String currentMove) {
			//System.out.println("curentMove: " + currentMove);
			if (currentMove.equals("1/2-1/2") || currentMove.equals("1-0") ||
					currentMove.equals("0-1")) {
				handleGameOver();
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
			updateBoard(move, chessPiece);
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
				updateBoard(move, c);
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
				updateBoard(move, c);
			}
			// TODO Implement pawn promotion
		}
		
		private void handleLengthFiveMove(String currentMove) {
			if (currentMove.contains("x")) {
				handleLongCapture(currentMove);
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
		
		private void updateBoard(String algebraicDestination, ChessPiece chessPiece) {
			Location destination = chessBoard.getLocation(algebraicDestination);
			chessPiece.move(destination);
			game.incrementRound();
		}
		
		private ChessPiece determineChessPiece(String move) {
			// must be a pawn
			return determineChessPiece(move, isWhiteTurn() ? "P" : "p");
		}
		
		private ChessPiece determineChessPiece(String move, String piece) {
			Location destination = chessBoard.getLocation(move);
			return evaluateSuspects(piece, destination);
		}
		
		private ChessPiece evaluateSuspects(String piece, Location destination) {
			// get suspects
			piece = isWhiteTurn() ? piece : piece.toLowerCase();
			Set<ChessPiece> suspects = chessPieceSubsetManager.getChessPieces(
					ChessPieceAllegianceType.valueOf(piece)); 
			
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
		
		private ChessPiece determineFileChessPiece(String file, String piece, String move) {
			ChessPiece icanReach = null;
			Location l = chessBoard.getLocation(move);
			Character c = file.charAt(0);
			if (Character.isDigit(c)) {
				icanReach = determineRankChessPiece(file, piece, move);
			} else {
				piece = isWhiteTurn() ? piece : piece.toLowerCase();
				Set<ChessPiece> suspects = chessPieceSubsetManager.getChessPieces(
						ChessPieceAllegianceType.valueOf(piece));
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
			Location destination = chessBoard.getLocation(algebraicDestination);
			piece = isWhiteTurn() ? piece : piece.toLowerCase();
			// white's turn
			Set<ChessPiece> suspects = chessPieceSubsetManager.getChessPieces(
					ChessPieceAllegianceType.valueOf(piece));
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
			if (isWhiteTurn()) {
				// white's turn
				castleMoves.add(new CastleMove(game.getRound(), "w", "king"));
				Set<ChessPiece> kings = chessPieceSubsetManager.getChessPieces(
						ChessPieceAllegianceType.WHITE_KING);
				ChessPiece king = (ChessPiece) kings.toArray()[0];
				Location l = chessBoard.getLocation("g1");
				king.move(l);
				Set<ChessPiece> rooks = chessPieceSubsetManager.getChessPieces(
						ChessPieceAllegianceType.WHITE_ROOK);
				ChessPiece r = null;
				for (ChessPiece rook : rooks) {
					if (rook.getLocation().getAlgebraicLocation().equals("h1")) {
						r = rook;
					}
				}
				updateBoard("f1", r);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "king"));
				Set<ChessPiece> kings = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_KING);
				ChessPiece k = (ChessPiece) kings.toArray()[0];
				Location l = new Location("g8");
				k.move(l);
				Set<ChessPiece> rooks = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_ROOK);
				ChessPiece r = null;
				for (ChessPiece rook : rooks) {
					if (rook.getLocation().getAlgebraicLocation().equals("h8")) {
						r = rook;
					}
				}
				updateBoard("f8", r);
			}
		}
		
		private void handleQueenSideCastle() {
			if (isWhiteTurn()) {
				// white's turn
				castleMoves.add(new CastleMove(game.getRound(), "w", "queen"));
				Set<ChessPiece> kings = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.WHITE_KING);
				ChessPiece k = (ChessPiece) kings.toArray()[0];
				Location l = new Location("c1");
				k.move(l);
				Set<ChessPiece> rooks = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.WHITE_ROOK);
				ChessPiece r = null;
				for (ChessPiece rook : rooks) {
					if (rook.getLocation().equals(new Location("a1"))) {
						r = rook;
					}
				}
				updateBoard("d1", r);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "queen"));
				Set<ChessPiece> kings = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_KING);
				ChessPiece k = (ChessPiece) kings.toArray()[0];
				Location l = new Location("c8");
				k.move(l);
				Set<ChessPiece> rooks = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_ROOK);
				ChessPiece r = null;
				for (ChessPiece rook : rooks) {
					if (rook.getLocation().equals(new Location("a8"))) {
						r = rook;
					}
				}
				Location l2 = new Location("d8");
				r.move(l2);
				game.setRound(game.getRound() + 1);
			}
		}
		
		private void handleGameOver() {
			game.setInProgress(false);
		}
		
		private void handleLengthFourCapture(String currentMove) {
			String type = currentMove.substring(0, 1);
			String algebraicDestination = currentMove.substring(2, 4);
			ChessPiece chessPiece = null;
			if (Character.isLowerCase(type.charAt(0))) {
				// a pawn moved
				String file = type;
				if (isWhiteTurn()) {
					chessPiece = determineFileChessPiece(file, "P", algebraicDestination);
				} else {
					chessPiece = determineFileChessPiece(file, "p", algebraicDestination);
				}
			} else {
				// a normal piece moved
				chessPiece = determineChessPiece(algebraicDestination, type);
			}
			handleCapture(algebraicDestination, chessPiece);
		}
		
		// Captures with 5 chars!
		private void handleLongCapture(String currentMove) {
			if (currentMove.substring(4, 5).equals("+") || currentMove.substring(4, 5).equals("#")) {
				String checkorMate = currentMove.substring(4, 5);
				String capture = currentMove.substring(0,4);
				handleLengthFourCapture(capture);
				if (checkorMate.equals("+")) {
					game.setCheck(true);
				} else {
					game.setCheckmate(true);
				}
			} else {
				// parse relevant pieces
				String type = currentMove.substring(0, 1);
				String file = currentMove.substring(1, 2);
				String algebraicDestination = currentMove.substring(3, 5);
				
				// a normal piece moved (pawns not possible!)
				ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
				handleCapture(algebraicDestination, chessPiece);
			}
		}
		
		private void handleCapture(String algebraicDestination, ChessPiece chessPiece) {
			// remove old piece
			Location destination = chessBoard.getLocation(algebraicDestination);
			ChessPiece dead = game.getBoard().getChessPieceByLocation(destination);
			removePiece(dead);
			
			// update metadata
			capturer = chessPiece;
			captured = dead;
			updateBoard(algebraicDestination, chessPiece);
		}
		
		private void handleLengthThreeCheck(String currentMove) {
			// parse relevant pieces
			String algebraicDestination = currentMove.substring(0, 2);
			String checkType = currentMove.substring(2, 3);
			
			// handle
			ChessPiece chessPiece = determineChessPiece(algebraicDestination);
			handleCheck(algebraicDestination, checkType, chessPiece);
		}
		
		private void handleLengthFourCheck(String currentMove) {
			// parse relevant pieces
			String type = currentMove.substring(0, 1);
			String algebraicDestination = currentMove.substring(1, 3);
			String checkType = currentMove.substring(3, 4);
			
			// handle
			ChessPiece chessPiece = determineChessPiece(algebraicDestination, type);
			handleCheck(algebraicDestination, checkType, chessPiece);
		}
		
		private void handleLengthFiveCheck(String currentMove) {
			// parse relevant pieces
			String type = currentMove.substring(0, 1);
			String file = currentMove.substring(1, 2);
			String algebraicDestination = currentMove.substring(2, 4);
			String checkType = currentMove.substring(4, 5);
			
			// handle
			ChessPiece chessPiece = determineFileChessPiece(file, type, algebraicDestination);
			handleCheck(algebraicDestination, checkType, chessPiece);
		}
		
		private void handleCheck(String algebraicDestination, String checkType,
				ChessPiece chessPiece) {
			if (checkType.equals("+")) {
				game.setCheck(true);
			} else {
				game.setCheckmate(true);
			}
			updateBoard(algebraicDestination, chessPiece);
		}
	}
}