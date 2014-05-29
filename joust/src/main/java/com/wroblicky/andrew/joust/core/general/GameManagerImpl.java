package com.wroblicky.andrew.joust.core.general;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

public class GameManagerImpl {
	private List<ChessPiece> myActivePieces;
	private List<ChessPiece> myBlackActives;
	private List<ChessPiece> myWhiteActives;
	private Game game;
	private Map<String, ArrayList<ChessPiece>> stringtoCP;
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
	
	public Map<String, ArrayList<ChessPiece>> getStringToCP() {
		return stringtoCP;
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
	
	public void setStringToCP(HashMap<String, ArrayList<ChessPiece>> map) {
		stringtoCP = map;
	}
	
	public boolean isWhiteTurn() {
		return game.getRound() % 2 == 0;
	}
	
	protected void printVisitedBoards() {
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

	protected void printBoard2(int round) {
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
			board[currX - 1][currY - 1] = current.getMySymbol(); 
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
		myActivePieces.remove(piece);
		String color = piece.getAllegiance().getAllegiance();
		if (piece.getMySymbol().equals("1") || piece.getMySymbol().equals("2") || piece.getMySymbol().equals("3") ||
			piece.getMySymbol().equals("4") || piece.getMySymbol().equals("5") || piece.getMySymbol().equals("6")) {
			// update individual piece list
			// determine color
			if (piece.getMySymbol().equals("1")) {
				if (color.equals("b")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("p");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("p");
					stringtoCP.put("p", deadMemberFamily);
				} else {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("P");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("P");
					stringtoCP.put("P", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("2")) {
				if (color.equals("b")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("r");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("r");
					stringtoCP.put("r", deadMemberFamily);
				} else {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("R");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("R");
					stringtoCP.put("R", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("3")) {
				if (color.equals("b")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("n");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("n");
					stringtoCP.put("n", deadMemberFamily);
				} else {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("N");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("N");
					stringtoCP.put("N", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("4")) {
				if (color.equals("b")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("b");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("b");
					stringtoCP.put("b", deadMemberFamily);
				} else {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("B");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("B");
					stringtoCP.put("B", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("5")) {
				if (color.equals("b")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("q");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("q");
					stringtoCP.put("q", deadMemberFamily);
				} else {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("Q");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("Q");
					stringtoCP.put("Q", deadMemberFamily);
				}
			} else if (piece.getMySymbol().equals("6")) {
					if (color.equals("b")) {
						ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("k");
						deadMemberFamily.remove(piece);
						stringtoCP.remove("k");
						stringtoCP.put("k", deadMemberFamily);
					} else {
						ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("K");
						deadMemberFamily.remove(piece);
						stringtoCP.remove("K");
						stringtoCP.put("K", deadMemberFamily);
					}
			}
		} else {
			// update generic piece list
			if (piece.getMySymbol().equals("p") || piece.getMySymbol().equals("P")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("1");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("1");
					stringtoCP.put("1", deadMemberFamily);
			} else if (piece.getMySymbol().equals("r") || piece.getMySymbol().equals("R")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("2");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("2");
					stringtoCP.put("2", deadMemberFamily);
			} else if (piece.getMySymbol().equals("n") || piece.getMySymbol().equals("N")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("3");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("3");
				stringtoCP.put("3", deadMemberFamily);
			} else if (piece.getMySymbol().equals("b") || piece.getMySymbol().equals("B")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("4");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("4");
				stringtoCP.put("4", deadMemberFamily);
			} else if (piece.getMySymbol().equals("q") || piece.getMySymbol().equals("Q")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("5");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("5");
				stringtoCP.put("5", deadMemberFamily);
			} else if (piece.getMySymbol().equals("k") || piece.getMySymbol().equals("K")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("6");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("6");
				stringtoCP.put("6", deadMemberFamily);
			} 
		}
				
		if (color.equals("b")) {
			myBlackActives.remove(piece);
			ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("d");
			deadMemberFamily.remove(piece);
			stringtoCP.remove("d");
			stringtoCP.put("d", deadMemberFamily);
		} else {
			myWhiteActives.remove(piece);
			ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("l");
			deadMemberFamily.remove(piece);
			stringtoCP.remove("l");
			stringtoCP.put("l", deadMemberFamily);
		}
		ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get(piece.getMySymbol());
		deadMemberFamily.remove(piece);
		stringtoCP.remove(piece.getMySymbol());
		stringtoCP.put(piece.getMySymbol(), deadMemberFamily);
		ArrayList<ChessPiece> deadMemberFamily2 = stringtoCP.get("g");
		deadMemberFamily2.remove(piece);
		stringtoCP.remove("g");
		stringtoCP.put("g", deadMemberFamily);
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
			if (currentMove.length() == 2) {
				handleLengthTwoMove(currentMove);
			} else if(currentMove.length() == 3){
				handleLengthThreeMove(currentMove);
			} else if(currentMove.length() == 4){
				handleLengthFourMove(currentMove);
			} else if (currentMove.length() == 5) {
				handleLengthFiveMove(currentMove);
			} else if(currentMove.equals("O-O")){
				handleKingSideCastle();
			} else if (currentMove.equals("O-O-O")) {
				handleQueenSideCastle();
			} else if (currentMove.equals("1/2-1/2") || currentMove.equals("1-0") || currentMove.equals("0-1")) {
				handleGameOver();
			} else { // not sure what this is
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
				handleShortCheck(currentMove);
			} else {
				String piece = currentMove.substring(0, 1);
				String move = currentMove.substring(1, 3);
				ChessPiece c = determineChessPiece(move, piece);
				updateBoard(move, c);
			}
		}
		
		private void handleLengthFourMove(String currentMove) {
			// Determine whether capture or more specific move
			//System.out.println("current move length 4");
			if (currentMove.substring(1, 2).equals("x")) {
				//System.out.println("handling capture");
				handleCapture(currentMove);
			} else if (currentMove.contains("+") || currentMove.contains("#")){
				//System.out.println("current move 4 check!");
				handleCheck(currentMove);
			} else {
				//System.out.println("current move 4 normal");
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
				// could be capture with check/checkmate
				handleLongCapture(currentMove);
			} else {
				if (currentMove.substring(4, 5).equals("+") || currentMove.substring(4, 5).equals("#")) {
					handleFileWithCheck(currentMove);
				}
				// TODO
				// could be generic move specifying moving piece, its file, its rank, and where went
				// also could be pawn promotion with check/checkmate
			}
		}
		
		private void updateBoard(String move, ChessPiece chessPiece) {
			Location destination = chessBoard.getLocation(move);
			chessPiece.move(destination);
			game.incrementRound();
		}
		
		private ChessPiece determineChessPiece(String move) {
			ChessPiece chessPiece = null;
			Location destination = chessBoard.getLocation(move);
			if (isWhiteTurn()) {
				// white's turn
				//System.out.println("white");
				chessPiece = evaluateSuspects(stringtoCP.get("P"), destination);
			} else {
				// black's turn
				//System.out.println("black");
				chessPiece = evaluateSuspects(stringtoCP.get("p"), destination);
			}
			return chessPiece;
		}
		
		private ChessPiece determineChessPiece(String move, String piece) {
			ChessPiece chessPiece = null;
			Location destination = chessBoard.getLocation(move);
			if (isWhiteTurn()) {
				// white's turn
				//System.out.println("white");
				chessPiece = evaluateSuspects(stringtoCP.get(piece), destination);
			} else {
				// black's turn
				//System.out.println("black");
				chessPiece = evaluateSuspects(stringtoCP.get(piece.toLowerCase()), destination);
			}
			return chessPiece;
		}
		
		private ChessPiece evaluateSuspects(List<ChessPiece> suspects, Location destination) {
			for (ChessPiece suspect : suspects) {
				if (suspect.canReach(destination)) {
					return suspect;
				}
			}
			System.err.println("Improperly formatted PGN file. " +
					"Could not find a piece that could've legally moved to " + destination);
			System.exit(-1);
			return null;
		}
		
		private ChessPiece determineFileChessPiece(String file, String piece, String move) {
			ChessPiece icanReach = null;
			Location l = chessBoard.getLocation(move);
			Character c = file.charAt(0);
			if (Character.isDigit(c)) {
				icanReach = determineRankChessPiece(file, piece, move);
			} else {
				//System.out.println("lx: " + l.getXCoordinate());
				//System.out.println("ly: " + l.getYCoordinate());
				//System.out.println("game.getRound(): " + game.getRound());
				if (isWhiteTurn()) {
					// white's turn
					//System.out.println("white");
					ArrayList<ChessPiece> suspects = stringtoCP.get(piece);
					for (int i = 0; i < suspects.size(); i++) {
						ChessPiece current = suspects.get(i);
						if (current.getFile().equals(file) && current.canReach(l)) {
							icanReach = current;
							break;
						}
					}
					if (icanReach == null) {
						System.err.println("Improperly formatted PGN file. " +
								"Could not find a piece that could've legally moved to " + move);
						System.exit(-1);
					}
				} else {
					// black's turn
					//System.out.println("black");
					String lower = piece.toLowerCase();
					ArrayList<ChessPiece> suspects = stringtoCP.get(lower);
					for (int i = 0; i < suspects.size(); i++) {
						ChessPiece current = suspects.get(i);
						if (current.getFile().equals(file) && current.canReach(l)) {
							icanReach = current;
							break;
						}
					}
					if (icanReach == null) {
						System.err.println("Improperly formatted PGN file. " +
								"Could not find a piece that could've legally moved to " + move);
						System.exit(-1);
					}
				}
			}
			return icanReach;
		}
		
		private ChessPiece determineRankChessPiece(String rank, String piece, String move) {
			//System.out.println("determine file ChessPiece: " + piece);
			ChessPiece icanReach = null;
			//System.out.println("determine rank chess piece move: " + move);
			//System.out.println("rank: " + rank);
			Location l = new Location(move);
			//System.out.println("lx: " + l.getXCoordinate());
			//System.out.println("ly: " + l.getYCoordinate());
			//System.out.println("game.getRound(): " + game.getRound());
			if (isWhiteTurn()) {
				// white's turn
				//System.out.println("det rank white");
				List<ChessPiece> suspects = stringtoCP.get(piece);
				for (int i = 0; i < suspects.size(); i++) {
					ChessPiece current = suspects.get(i);
					//System.out.println("current rank: " + current.getRank());
					//System.out.println("current canReach: " + current.canReach(this, l));
					if (current.getRank().equals(rank) && current.canReach(l)) {
						icanReach = current;
						break;
					}
				}
				if (icanReach == null) {
					System.err.println("Improperly formatted PGN file. " +
							"Could not find a piece that could've legally moved to " + move);
					System.exit(-1);
				}
			} else {
				// black's turn
				//System.out.println("det rank black");
				String lower = piece.toLowerCase();
				ArrayList<ChessPiece> suspects = stringtoCP.get(lower);
				for (int i = 0; i < suspects.size(); i++) {
					ChessPiece current = suspects.get(i);
					if (current.getRank().equals(rank) && current.canReach(l)) {
						icanReach = current;
						break;
					}
				}
				if (icanReach == null) {
					System.err.println("Improperly formatted PGN file. " +
							"Could not find a piece that could've legally moved to " + move);
					System.exit(-1);
				}
			}
			return icanReach;
		}

		
		private void handleKingSideCastle() {
			if (game.getRound() % 2 == 0) {
				// white's turn
				castleMoves.add(new CastleMove(game.getRound(), "w", "king"));
				ArrayList<ChessPiece> kings = getStringToCP().get("K");
				ChessPiece k = kings.get(0);
				Location l = new Location("g1");
				k.move(l);
				ArrayList<ChessPiece> rooks = getStringToCP().get("R");
				ChessPiece r = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().equals(new Location("h1"))) {
						r = rooks.get(i);
					}
				}
				Location l2 = new Location("f1");
				r.move(l2);
				game.setRound(game.getRound() + 1);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "king"));
				ArrayList<ChessPiece> kings = getStringToCP().get("k");
				ChessPiece k = kings.get(0);
				Location l = new Location("g8");
				k.move(l);
				ArrayList<ChessPiece> rooks = getStringToCP().get("r");
				ChessPiece r = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().equals(new Location("h8"))) {
						r = rooks.get(i);
					}
				}
				Location l2 = new Location("f8");
				r.move(l2);
				game.setRound(game.getRound() + 1);
			}
		}
		
		private void handleQueenSideCastle() {
			if (isWhiteTurn()) {
				// white's turn
				castleMoves.add(new CastleMove(game.getRound(), "w", "queen"));
				ArrayList<ChessPiece> kings = getStringToCP().get("K");
				ChessPiece k = kings.get(0);
				Location l = new Location("c1");
				k.move(l);
				ArrayList<ChessPiece> rooks = getStringToCP().get("R");
				ChessPiece rook = null;
				for (int i = 0; i < rooks.size(); i++) {
					if (rooks.get(i).getLocation().equals(new Location("a1"))) {
						rook = rooks.get(i);
					}
				}
				updateBoard("d1", rook);
			} else {
				castleMoves.add(new CastleMove(game.getRound(), "b", "queen"));
				ArrayList<ChessPiece> kings = getStringToCP().get("k");
				ChessPiece k = kings.get(0);
				Location l = new Location("c8");
				k.move(l);
				ArrayList<ChessPiece> rooks = getStringToCP().get("r");
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
		
		// for captures with 4 characters
		private void handleCapture(String currentMove) {
			String type = currentMove.substring(0, 1);
			String move = currentMove.substring(2, 4);
			//System.out.println("handleCapture type: " + type);
			//System.out.println("handleCapture move: " + move);
			ChessPiece c = null;
			if (Character.isLowerCase(type.charAt(0))) {
				// a pawn moved
				String file = type;
				String piece = "";
				if (game.getRound() % 2 == 0) {
					piece = "P";
				} else {
					piece = "p";
				}
				c = determineFileChessPiece(file, piece, move);
			} else {
				// a normal piece moved
				c = determineChessPiece(move, type);
				//System.out.println("handleCapture c: " + c);
			}
			Location l = new Location(move);
			
			// kill the old piece
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			Location l2 = chessBoard.getLocation(y - 1, x - 1);
		
			ChessPiece dead = game.getBoard().getChessPieceByLocation(l2);
			//System.out.println("l2: " + l2);
			//System.out.println("dead id: " + dead.getID());
			//System.out.println("dead id: " + dead.getLocation());
			removePiece(dead);
			
			//move the new one
			c.move(l);
			game.setRound(game.getRound() + 1);
			capturer = c;
			//System.out.println("game capturer: " + capturer);
			captured = dead;
			//System.out.println("game captured: " + captured);
		}
		
		// Captures with 5 chars!
		private void handleLongCapture(String currentMove) {
			if (currentMove.substring(4, 5).equals("+") || currentMove.substring(4, 5).equals("#")) {
				String checkorMate = currentMove.substring(4, 5);
				String capture = currentMove.substring(0,4);
				handleCapture(capture);
				if (checkorMate.equals("+")) {
					game.setCheck(true);
				} else {
					game.setCheckmate(true);
				}
			} else {
				//String capture = currentMove.substring(0,4);
				//handleCapture(capture);
				String type = currentMove.substring(0, 1);
				String file = currentMove.substring(1, 2);
				String move = currentMove.substring(3, 5);
				//System.out.println("handleCapture file: " + file);
				//System.out.println("handleCapture type: " + type);
				//System.out.println("handleCapture move: " + move);
				ChessPiece c = null;
				// a normal piece moved (pawns not possible!)
				c = determineFileChessPiece(file, type, move);
				//System.out.println("handleCapture c: " + c);
				Location l = new Location(move);
				
				// kill the old piece
				int x = l.getXCoordinate();
				int y = l.getYCoordinate();
				Location l2 = chessBoard.getLocation(y - 1, x - 1);
			
				ChessPiece dead = game.getBoard().getChessPieceByLocation(l2);
				//System.out.println("l2: " + l2);
				//System.out.println("dead id: " + dead.getID());
				//System.out.println("dead id: " + dead.getLocation());
				removePiece(dead);
				
				//move the new one
				c.move(l);
				game.setRound(game.getRound() + 1);
				capturer = c;
				//System.out.println("game capturer: " + capturer);
				captured = dead;
				//System.out.println("game captured: " + captured);
				}
		}
		
		// 3 characters
		private void handleShortCheck(String currentMove) {
			String destination = currentMove.substring(0, 2);
			String checkorMate = currentMove.substring(2, 3);
			if (checkorMate.equals("+")) {
				game.setCheck(true);
			} else {
				game.setCheckmate(true);
			}
			Location l = new Location(destination);
			ChessPiece c = determineChessPiece(destination);
			c.move(l);
			game.setRound(game.getRound() + 1);
		}
		
		// for 4 character moves
		private void handleCheck(String currentMove) {
			//System.out.println("handling check: " + currentMove);
			String type = currentMove.substring(0, 1);
			String destination = currentMove.substring(1, 3);
			String checkorMate = currentMove.substring(3, 4);
			if (checkorMate.equals("+")) {
				game.setCheck(true);
			} else {
				game.setCheckmate(true);
			}
			Location l = new Location(destination);
			ChessPiece c = determineChessPiece(destination, type);
			c.move(l);
			game.setRound(game.getRound() + 1);
		}
		
		// Has 5 characters!!
		private void handleFileWithCheck(String currentMove) {
			String type = currentMove.substring(0, 1);
			String file = currentMove.substring(1, 2);
			String dest = currentMove.substring(2, 4);
			String checkorMate = currentMove.substring(4, 5);
			if (checkorMate.equals("+")) {
				game.setCheck(true);
			} else {
				game.setCheckmate(true);
			}
			Location l = new Location(dest);
			ChessPiece c = determineFileChessPiece(file, type, dest);
			c.move(l);
			game.setRound(game.getRound() + 1);
		}
	}
}