package com.wroblicky.andrew.joust.core.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.CastleMove;
import com.wroblicky.andrew.joust.core.general.Util;

public class GameManagerImpl {
	private List<ChessPiece> myActivePieces;
	private List<ChessPiece> myBlackActives;
	private List<ChessPiece> myWhiteActives;
	private Game game;
	private Map<String, List<ChessPiece>> chessPieceLookup;
	private List<String[][]> visitedBoards; // debugging ONLY!!!
	private List<CastleMove> castleMoves; // for ChessDemoVisualizations ONLY!!
	private ChessPiece capturer;
	private ChessPiece captured;
	
	
	public GameManagerImpl(Game game, List<ChessPiece> activePieces,
			List<ChessPiece> blacks, List<ChessPiece> whites) {
		this.game = game;
		myActivePieces = activePieces;
		myBlackActives = blacks;
		myWhiteActives = whites;
		visitedBoards = new ArrayList<String[][]>();
		castleMoves = new ArrayList<CastleMove>();
	}
	
	public int getRound() {
		return game.getRound();
	}
	
	public List<ChessPiece> getActivePieces() {
		return myActivePieces;
	}
	
	public List<ChessPiece> getBlackPieces() {
		return myBlackActives;
	}
	
	public List<ChessPiece> getWhitePieces() {
		return myWhiteActives;
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
	
	public Map<String, List<ChessPiece>> getChessPieceLookup() {
		return chessPieceLookup;
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
	
	public void setChessPieceLookup(HashMap<String, List<ChessPiece>> map) {
		chessPieceLookup = map;
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
		for (int k = 0; k < myActivePieces.size(); k++) {
			ChessPiece current = myActivePieces.get(k);
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
	
	// TODO: make less ugly
	public void removePiece(ChessPiece piece) {
		myActivePieces.remove(piece);
		String color = piece.getAllegiance().getAllegiance();
		if (piece.getMySymbol().equals("1") || piece.getMySymbol().equals("2") || piece.getMySymbol().equals("3") ||
			piece.getMySymbol().equals("4") || piece.getMySymbol().equals("5") || piece.getMySymbol().equals("6")) {
			// update individual piece list
			// determine color
			if (piece.getMySymbol().equals("1")) {
				if (color.equals("b")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("p");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("p");
					chessPieceLookup.put("p", deadMemberFamily);
				} else {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("P");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("P");
					chessPieceLookup.put("P", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("2")) {
				if (color.equals("b")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("r");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("r");
					chessPieceLookup.put("r", deadMemberFamily);
				} else {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("R");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("R");
					chessPieceLookup.put("R", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("3")) {
				if (color.equals("b")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("n");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("n");
					chessPieceLookup.put("n", deadMemberFamily);
				} else {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("N");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("N");
					chessPieceLookup.put("N", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("4")) {
				if (color.equals("b")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("b");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("b");
					chessPieceLookup.put("b", deadMemberFamily);
				} else {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("B");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("B");
					chessPieceLookup.put("B", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("5")) {
				if (color.equals("b")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("q");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("q");
					chessPieceLookup.put("q", deadMemberFamily);
				} else {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("Q");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("Q");
					chessPieceLookup.put("Q", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("6")) {
					if (color.equals("b")) {
						List<ChessPiece> deadMemberFamily = chessPieceLookup.get("k");
						deadMemberFamily.remove(piece);
						chessPieceLookup.remove("k");
						chessPieceLookup.put("k", deadMemberFamily);
					} else {
						List<ChessPiece> deadMemberFamily = chessPieceLookup.get("K");
						deadMemberFamily.remove(piece);
						chessPieceLookup.remove("K");
						chessPieceLookup.put("K", deadMemberFamily);
					}
			}
		} else {
			// update generic piece list
			if (piece.getMySymbol().equals("p") || piece.getMySymbol().equals("P")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("1");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("1");
					chessPieceLookup.put("1", deadMemberFamily);
			} else if (piece.getMySymbol().equals("r") || piece.getMySymbol().equals("R")) {
					List<ChessPiece> deadMemberFamily = chessPieceLookup.get("2");
					deadMemberFamily.remove(piece);
					chessPieceLookup.remove("2");
					chessPieceLookup.put("2", deadMemberFamily);
			} else if (piece.getMySymbol().equals("n") || piece.getMySymbol().equals("N")) {
				List<ChessPiece> deadMemberFamily = chessPieceLookup.get("3");
				deadMemberFamily.remove(piece);
				chessPieceLookup.remove("3");
				chessPieceLookup.put("3", deadMemberFamily);
			} else if (piece.getMySymbol().equals("b") || piece.getMySymbol().equals("B")) {
				List<ChessPiece> deadMemberFamily = chessPieceLookup.get("4");
				deadMemberFamily.remove(piece);
				chessPieceLookup.remove("4");
				chessPieceLookup.put("4", deadMemberFamily);
			} else if (piece.getMySymbol().equals("q") || piece.getMySymbol().equals("Q")) {
				List<ChessPiece> deadMemberFamily = chessPieceLookup.get("5");
				deadMemberFamily.remove(piece);
				chessPieceLookup.remove("5");
				chessPieceLookup.put("5", deadMemberFamily);
			} else if (piece.getMySymbol().equals("k") || piece.getMySymbol().equals("K")) {
				List<ChessPiece> deadMemberFamily = chessPieceLookup.get("6");
				deadMemberFamily.remove(piece);
				chessPieceLookup.remove("6");
				chessPieceLookup.put("6", deadMemberFamily);
			} 
		}
				
		if (color.equals("b")) {
			myBlackActives.remove(piece);
			List<ChessPiece> deadMemberFamily = chessPieceLookup.get("d");
			deadMemberFamily.remove(piece);
			chessPieceLookup.remove("d");
			chessPieceLookup.put("d", deadMemberFamily);
		} else {
			myWhiteActives.remove(piece);
			List<ChessPiece> deadMemberFamily = chessPieceLookup.get("l");
			deadMemberFamily.remove(piece);
			chessPieceLookup.remove("l");
			chessPieceLookup.put("l", deadMemberFamily);
		}
		List<ChessPiece> deadMemberFamily = chessPieceLookup.get(piece.getMySymbol());
		deadMemberFamily.remove(piece);
		chessPieceLookup.remove(piece.getMySymbol());
		chessPieceLookup.put(piece.getMySymbol().toString(), deadMemberFamily);
		List<ChessPiece> deadMemberFamily2 = chessPieceLookup.get("g");
		deadMemberFamily2.remove(piece);
		chessPieceLookup.remove("g");
		chessPieceLookup.put("g", deadMemberFamily);
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
			//System.out.println("current move length 4");
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
			List<ChessPiece> suspects = chessPieceLookup.get(piece); 
			
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
				// white's turn
				List<ChessPiece> suspects = chessPieceLookup.get(piece);
				for (int i = 0; i < suspects.size(); i++) {
					ChessPiece current = suspects.get(i);
					if (current.getFile().equals(file) && current.canReach(l)) {
						icanReach = current;
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
			List<ChessPiece> suspects = chessPieceLookup.get(piece);
			for (int i = 0; i < suspects.size(); i++) {
				ChessPiece current = suspects.get(i);
				if (current.getRank().equals(rank) && current.canReach(destination)) {
					icanReach = current;
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
				List<ChessPiece> kings = getChessPieceLookup().get("K");
				ChessPiece king = kings.get(0);
				Location l = chessBoard.getLocation("g1");
				king.move(l);
				List<ChessPiece> rooks = getChessPieceLookup().get("R");
				ChessPiece r = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().getAlgebraicLocation().equals("h1")) {
						r = rooks.get(i);
					}
				}
				updateBoard("f1", r);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "king"));
				List<ChessPiece> kings = getChessPieceLookup().get("k");
				ChessPiece k = kings.get(0);
				Location l = new Location("g8");
				k.move(l);
				List<ChessPiece> rooks = getChessPieceLookup().get("r");
				ChessPiece r = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().getAlgebraicLocation().equals("h8")) {
						r = rooks.get(i);
					}
				}
				updateBoard("f8", r);
			}
		}
		
		private void handleQueenSideCastle() {
			if (isWhiteTurn()) {
				// white's turn
				castleMoves.add(new CastleMove(game.getRound(), "w", "queen"));
				List<ChessPiece> kings = getChessPieceLookup().get("K");
				ChessPiece k = kings.get(0);
				Location l = new Location("c1");
				k.move(l);
				List<ChessPiece> rooks = getChessPieceLookup().get("R");
				ChessPiece rook = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().equals(new Location("a1"))) {
						rook = rooks.get(i);
					}
				}
				updateBoard("d1", rook);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "queen"));
				List<ChessPiece> kings = getChessPieceLookup().get("k");
				ChessPiece k = kings.get(0);
				Location l = new Location("c8");
				k.move(l);
				List<ChessPiece> rooks = getChessPieceLookup().get("r");
				ChessPiece r = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().equals(new Location("a8"))) {
						r = rooks.get(i);
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