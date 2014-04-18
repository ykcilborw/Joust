package joust.core.general;

import joust.core.board.ChessBoard;
import joust.core.chesspiece.*;
import joust.core.chesspiece.ChessPiece.Allegiance;

import java.util.*;
import java.io.*;

public class Game {
	private int myRound;
	private HashMap<Location, ChessPiece> myPositions;
	private ArrayList<ChessPiece> myActivePieces;
	private ArrayList<ChessPiece> myBlackActives;
	private ArrayList<ChessPiece> myWhiteActives;
	private ChessBoard chessBoard;
	private boolean castling4Black;
	private boolean castling4White;
	private boolean isInProgress;
	private HashMap<String, ArrayList<ChessPiece>> stringtoCP;
	private ArrayList<String[][]> visitedBoards; // debugging ONLY!!!
	private ChessPiece captured;
	private ChessPiece capturer; 
	private ArrayList<CastleMove> castleMoves; // for ChessDemoVisualizations ONLY!!
	private boolean checkOn;
	private boolean checkMateOn;
	
	public Game(int round, HashMap<Location, ChessPiece> occupied, ArrayList<ChessPiece> activePieces,
			ArrayList<ChessPiece> blacks, ArrayList<ChessPiece> whites, Location[][] board) {
		myRound = round;
		myPositions = occupied;
		myActivePieces = activePieces;
		myBlackActives = blacks;
		myWhiteActives = whites;
		chessBoard = new ChessBoard(board);
		castling4Black = true;
		castling4White = true;
		isInProgress = true;	
		visitedBoards = new ArrayList<String[][]>();
		castleMoves = new ArrayList<CastleMove>();
		checkOn = false;
		checkMateOn = false;
	}
	
	public Game(int round, HashMap<Location, ChessPiece> occupied, ArrayList<ChessPiece> activePieces,
			ArrayList<ChessPiece> blacks, ArrayList<ChessPiece> whites, Location[][] board, 
			boolean blackCastle, boolean whiteCastle) {
		myRound = round;
		myPositions = occupied;
		myActivePieces = activePieces;
		myBlackActives = blacks;
		myWhiteActives = whites;
		chessBoard = new ChessBoard(board);
		castling4Black = blackCastle;
		castling4White = whiteCastle;
		isInProgress = true;
		visitedBoards = new ArrayList<String[][]>();
		castleMoves = new ArrayList<CastleMove>();
		checkOn = false;
		checkMateOn = false;
	}
	
	public int getmyRound() {
		return myRound;
	}
	
	public HashMap<Location, ChessPiece> getMyPositions() {
		return myPositions;
	}
	
	public ArrayList<ChessPiece> getActivePieces() {
		return myActivePieces;
	}
	
	public ArrayList<ChessPiece> getBlackPieces() {
		return myBlackActives;
	}
	
	public ArrayList<ChessPiece> getWhitePieces() {
		return myWhiteActives;
	}
	
	@Deprecated
	public Location[][] getBoard() {
		return chessBoard.getBoard();
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
	
	public boolean getBlackCastle() {
		return castling4Black;
	}
	
	public boolean getWhiteCastle() {
		return castling4White;
	}
	
	public boolean getisInProgress() {
		return isInProgress;
	}
	
	public HashMap<String, ArrayList<ChessPiece>> getStringToCP() {
		return stringtoCP;
	}
	
	public ChessPiece getCapturer() {
		return capturer;
	}
	
	public ChessPiece getCaptured() {
		return captured;
	}
	
	public ArrayList<CastleMove> getCastleMoves() {
		return castleMoves;
	}
	
	public boolean getCheckOn() {
		return checkOn;
	}
	
	public boolean getCheckMateOn() {
		return checkMateOn;
	}
	
	public void setBlackCastle(boolean b) {
		castling4Black = b;
	}
	
	public void setWhiteCastle(boolean b) {
		castling4White = b;
	}
	
	public void setisInProgress(boolean b) {
		isInProgress = b;
	}
	
	public void setStringToCP(HashMap<String, ArrayList<ChessPiece>> map) {
		stringtoCP = map;
	}
	
	public void setRound(int round) {
		myRound = round;
	}
	
	public void update(ArrayList<String> moves) {
		capturer = null;
		captured = null;
		checkOn = false;
		checkMateOn = false;
		String currentMove = null;
		if (myRound < moves.size()) {
			currentMove = moves.get(myRound);
		} else {
			System.out.println("Game Over!");
			System.exit(-1);
		}
		//System.out.println("curentMove: " + currentMove);
		if (currentMove.length() == 2) {
			// figure out which chess piece must've made the move
			ChessPiece c = determineChessPiece(currentMove);
			//System.out.println("c: " + c);
			Location l = new Location(currentMove);
			c.move(l);
			myRound += 1;
		} else if(currentMove.equals("O-O")){
			handleKingSideCastle();
		} else if (currentMove.equals("O-O-O")) {
			handleQueenSideCastle();
		} else if (currentMove.equals("1/2-1/2") || currentMove.equals("1-0") || currentMove.equals("0-1")) {
			this.isInProgress = false;
		} else if(currentMove.length() == 3){
			// could be a pawn check
			if (currentMove.substring(2, 3).equals("+") || currentMove.substring(2, 3).equals("#")) {
				handleShortCheck(currentMove);
			} else {
				String piece = currentMove.substring(0, 1);
				String move = currentMove.substring(1, 3);
				Location l = new Location(move);
				ChessPiece c = determineChessPiece(move, piece);
				c.move(l);
				myRound += 1;
			}
		} else if(currentMove.length() == 4){
			// Determine whether capture or more specific move
			//System.out.println("current move length 4");
			if (currentMove.substring(1, 2).equals("x")) {
				//System.out.println("handling capture");
				handleCapture(currentMove);
			} else if (currentMove.contains("+") || currentMove.contains("#")){
				handleCheck(currentMove);
				//System.out.println("current move 4 check!");
			} else {
				//System.out.println("current move 4 normal");
				String piece = currentMove.substring(0, 1);
				String file = currentMove.substring(1, 2);
				String move = currentMove.substring(2, 4);
				Location l = new Location(move);
				ChessPiece c = determineFileChessPiece(file, piece, move);
				c.move(l);
				myRound += 1;
			}
			// TODO Implement pawn promotion
		} else if (currentMove.length() == 5) {
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
	}
	
	private ChessPiece determineChessPiece(String move) {
		ChessPiece icanReach = null;
		Location l = new Location(move);
		if (myRound % 2 == 0) {
			// white's turn
			//System.out.println("white");
			ArrayList<ChessPiece> suspects = stringtoCP.get("P");
			for (int i = 0; i < suspects.size(); i++) {
				ChessPiece current = suspects.get(i);
				if (current.canReach(this, l)) {
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
			ArrayList<ChessPiece> suspects = stringtoCP.get("p");
			for (int i = 0; i < suspects.size(); i++) {
				ChessPiece current = suspects.get(i);
				//System.out.println("dCP1 black cand loc: " + current.getLocation());
				if (current.canReach(this, l)) {
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
	
	private ChessPiece determineChessPiece(String move, String piece) {
		ChessPiece icanReach = null;
		//System.out.println("move: " + move);
		//System.out.println("piece: " + piece);
		Location l = new Location(move);
		if (myRound % 2 == 0) {
			// white's turn
			//System.out.println("white");
			ArrayList<ChessPiece> suspects = stringtoCP.get(piece);
			for (int i = 0; i < suspects.size(); i++) {
				//System.out.println("suspects: " + suspects.get(i).getmySymbol());
				ChessPiece current = suspects.get(i);
				if (current.canReach(this, l)) {
					icanReach = current;
					//System.out.println("dcp white current: " + current.getLocation());
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
				if (current.canReach(this, l)) {
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
	
	private ChessPiece determineFileChessPiece(String file, String piece, String move) {
		//System.out.println("determine file ChessPiece: " + piece);
		ChessPiece icanReach = null;
		//System.out.println("move: " + move);
		Location l = new Location(move);
		Character c = file.charAt(0);
		if (Character.isDigit(c)) {
			icanReach = determineRankChessPiece(file, piece, move);
		} else {
			//System.out.println("lx: " + l.getXCoordinate());
			//System.out.println("ly: " + l.getYCoordinate());
			//System.out.println("myRound: " + myRound);
			if (myRound % 2 == 0) {
				// white's turn
				//System.out.println("white");
				ArrayList<ChessPiece> suspects = stringtoCP.get(piece);
				for (int i = 0; i < suspects.size(); i++) {
					ChessPiece current = suspects.get(i);
					if (current.getFile().equals(file) && current.canReach(this, l)) {
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
					if (current.getFile().equals(file) && current.canReach(this, l)) {
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
		//System.out.println("myRound: " + myRound);
		if (myRound % 2 == 0) {
			// white's turn
			//System.out.println("det rank white");
			ArrayList<ChessPiece> suspects = stringtoCP.get(piece);
			for (int i = 0; i < suspects.size(); i++) {
				ChessPiece current = suspects.get(i);
				//System.out.println("current rank: " + current.getRank());
				//System.out.println("current canReach: " + current.canReach(this, l));
				if (current.getRank().equals(rank) && current.canReach(this, l)) {
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
				if (current.getRank().equals(rank) && current.canReach(this, l)) {
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
		if (myRound % 2 == 0) {
			// white's turn
			castleMoves.add(new CastleMove(myRound, "w", "king"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("K");
			ChessPiece k = kings.get(0);
			Location l = new Location("g1");
			k.move(l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("R");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("h1"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("f1");
			r.move(l2);
			myRound += 1;
		} else {
			castleMoves.add(new CastleMove(myRound, "b", "king"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("k");
			ChessPiece k = kings.get(0);
			Location l = new Location("g8");
			k.move(l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("r");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("h8"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("f8");
			r.move(l2);
			myRound += 1;
		}
	}
	
	private void handleQueenSideCastle() {
		if (myRound % 2 == 0) {
			// white's turn
			castleMoves.add(new CastleMove(myRound, "w", "queen"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("K");
			ChessPiece k = kings.get(0);
			Location l = new Location("c1");
			k.move(l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("R");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("a1"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("d1");
			r.move(l2);
			myRound += 1;
		} else {
			castleMoves.add(new CastleMove(myRound, "b", "queen"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("k");
			ChessPiece k = kings.get(0);
			Location l = new Location("c8");
			k.move(l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("r");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("a8"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("d8");
			r.move(l2);
			myRound += 1;
		}
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
			if (myRound % 2 == 0) {
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
		Location l2 = chessBoard.getBoard()[y-1][x-1];
	
		ChessPiece dead = myPositions.get(l2);
		//System.out.println("myPositions: \n" + myPositions);
		//System.out.println("l2: " + l2);
		//System.out.println("dead id: " + dead.getID());
		//System.out.println("dead id: " + dead.getLocation());
		removePiece(dead);
		
		//move the new one
		c.move(l);
		myRound += 1;
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
				checkOn = true;
			} else {
				checkMateOn = true;
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
			Location l2 = chessBoard.getBoard()[y-1][x-1];
		
			ChessPiece dead = myPositions.get(l2);
			//System.out.println("myPositions: \n" + myPositions);
			//System.out.println("l2: " + l2);
			//System.out.println("dead id: " + dead.getID());
			//System.out.println("dead id: " + dead.getLocation());
			removePiece(dead);
			
			//move the new one
			c.move(l);
			myRound += 1;
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
			checkOn = true;
		} else {
			checkMateOn = true;
		}
		Location l = new Location(destination);
		ChessPiece c = determineChessPiece(destination);
		c.move(l);
		myRound += 1;
	}
	
	// for 4 character moves
	private void handleCheck(String currentMove) {
		//System.out.println("handling check: " + currentMove);
		String type = currentMove.substring(0, 1);
		String destination = currentMove.substring(1, 3);
		String checkorMate = currentMove.substring(3, 4);
		if (checkorMate.equals("+")) {
			checkOn = true;
		} else {
			checkMateOn = true;
		}
		Location l = new Location(destination);
		ChessPiece c = determineChessPiece(destination, type);
		c.move(l);
		myRound += 1;
	}
	
	// Has 5 characters!!
	private void handleFileWithCheck(String currentMove) {
		String type = currentMove.substring(0, 1);
		String file = currentMove.substring(1, 2);
		String dest = currentMove.substring(2, 4);
		String checkorMate = currentMove.substring(4, 5);
		if (checkorMate.equals("+")) {
			checkOn = true;
		} else {
			checkMateOn = true;
		}
		Location l = new Location(dest);
		ChessPiece c = determineFileChessPiece(file, type, dest);
		c.move(l);
		myRound += 1;
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
	
	/*
	protected void printMyPositions() {
		Set<Location> s = myPositions.keySet();
		Iterator<Location> iter = s.iterator();
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; //denotes unoccupied
			}
		}
		while (iter.hasNext()) {
			Location l = iter.next();
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			ChessPiece current = myPositions.get(l);
			if (current != null) {
				board[x - 1][y - 1] = current.getMySymbol();
			}
		}
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
	} */
}
