package joust.core.general;

import joust.core.chesspiece.*;
import java.util.*;
import java.io.*;

public class Game {
	private int myRound;
	private HashMap<Location, ChessPiece> myPositions;
	private ArrayList<ChessPiece> myActivePieces;
	private ArrayList<ChessPiece> myBlackActives;
	private ArrayList<ChessPiece> myWhiteActives;
	private Location[][] myBoard = new Location[8][8];
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
		myBoard = board;
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
		myBoard = board;
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
	
	public HashMap<Location, ChessPiece> getmyPositions() {
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
	
	public Location[][] getBoard() {
		return myBoard;
	}
	
	public void removePiece(ChessPiece piece) {
		myActivePieces.remove(piece);
		String color = piece.getColor();
		if (piece.getmySymbol().equals("1") || piece.getmySymbol().equals("2") || piece.getmySymbol().equals("3") ||
			piece.getmySymbol().equals("4") || piece.getmySymbol().equals("5") || piece.getmySymbol().equals("6")) {
			// update individual piece list
			// determine color
			if (piece.getmySymbol().equals("1")) {
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
			} else if (piece.getmySymbol().equals("2")) {
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
			} else if (piece.getmySymbol().equals("3")) {
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
			} else if (piece.getmySymbol().equals("4")) {
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
			} else if (piece.getmySymbol().equals("5")) {
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
			} else if (piece.getmySymbol().equals("6")) {
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
			if (piece.getmySymbol().equals("p") || piece.getmySymbol().equals("P")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("1");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("1");
					stringtoCP.put("1", deadMemberFamily);
			} else if (piece.getmySymbol().equals("r") || piece.getmySymbol().equals("R")) {
					ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("2");
					deadMemberFamily.remove(piece);
					stringtoCP.remove("2");
					stringtoCP.put("2", deadMemberFamily);
			} else if (piece.getmySymbol().equals("n") || piece.getmySymbol().equals("N")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("3");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("3");
				stringtoCP.put("3", deadMemberFamily);
			} else if (piece.getmySymbol().equals("b") || piece.getmySymbol().equals("B")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("4");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("4");
				stringtoCP.put("4", deadMemberFamily);
			} else if (piece.getmySymbol().equals("q") || piece.getmySymbol().equals("Q")) {
				ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get("5");
				deadMemberFamily.remove(piece);
				stringtoCP.remove("5");
				stringtoCP.put("5", deadMemberFamily);
			} else if (piece.getmySymbol().equals("k") || piece.getmySymbol().equals("K")) {
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
		ArrayList<ChessPiece> deadMemberFamily = stringtoCP.get(piece.getmySymbol());
		deadMemberFamily.remove(piece);
		stringtoCP.remove(piece.getmySymbol());
		stringtoCP.put(piece.getmySymbol(), deadMemberFamily);
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
			c.move(this, l);
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
				c.move(this, l);
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
				c.move(this, l);
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
			//System.out.println("lx: " + l.getmyX());
			//System.out.println("ly: " + l.getmyY());
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
		//System.out.println("lx: " + l.getmyX());
		//System.out.println("ly: " + l.getmyY());
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
			k.move(this, l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("R");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("h1"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("f1");
			r.move(this, l2);
			myRound += 1;
		} else {
			castleMoves.add(new CastleMove(myRound, "b", "king"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("k");
			ChessPiece k = kings.get(0);
			Location l = new Location("g8");
			k.move(this, l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("r");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("h8"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("f8");
			r.move(this, l2);
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
			k.move(this, l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("R");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("a1"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("d1");
			r.move(this, l2);
			myRound += 1;
		} else {
			castleMoves.add(new CastleMove(myRound, "b", "queen"));
			ArrayList<ChessPiece> kings = this.getStringToCP().get("k");
			ChessPiece k = kings.get(0);
			Location l = new Location("c8");
			k.move(this, l);
			ArrayList<ChessPiece> rooks = this.getStringToCP().get("r");
			ChessPiece r = null;
			for (int i = 0; i < rooks.size(); i++) {
				if (rooks.get(i).getLocation().equals(new Location("a8"))) {
					r = rooks.get(i);
				}
			}
			Location l2 = new Location("d8");
			r.move(this, l2);
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
		int x = l.getmyX();
		int y = l.getmyY();
		Location l2 = myBoard[y-1][x-1];
	
		ChessPiece dead = myPositions.get(l2);
		//System.out.println("myPositions: \n" + myPositions);
		//System.out.println("l2: " + l2);
		//System.out.println("dead id: " + dead.getID());
		//System.out.println("dead id: " + dead.getLocation());
		removePiece(dead);
		
		//move the new one
		c.move(this, l);
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
			int x = l.getmyX();
			int y = l.getmyY();
			Location l2 = myBoard[y-1][x-1];
		
			ChessPiece dead = myPositions.get(l2);
			//System.out.println("myPositions: \n" + myPositions);
			//System.out.println("l2: " + l2);
			//System.out.println("dead id: " + dead.getID());
			//System.out.println("dead id: " + dead.getLocation());
			removePiece(dead);
			
			//move the new one
			c.move(this, l);
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
		c.move(this, l);
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
		c.move(this, l);
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
		c.move(this, l);
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
				int currX = current.getLocation().getmyX();
				int currY = current.getLocation().getmyY();
				board[currX - 1][currY - 1] = current.getmySymbol(); 
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
			int x = l.getmyX();
			int y = l.getmyY();
			ChessPiece current = myPositions.get(l);
			if (current != null) {
				board[x - 1][y - 1] = current.getmySymbol();
			}
		}
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
	}
	
	protected static Game setupDefaultGame() {
		// Load initial default initial configuration into game
		HashMap<Location, ChessPiece> map = new HashMap<Location, ChessPiece>();
		ArrayList<ChessPiece> actives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> blackActives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> whiteActives = new ArrayList<ChessPiece>();
		Location[][] board = new Location[8][8];
		
		//create location objects
		Location a1 = new Location("a1");
		board[0][0] = a1;
		Location b1 = new Location("b1");
		board[0][1] = b1;
		Location c1 = new Location("c1");
		board[0][2] = c1;
		Location d1 = new Location("d1");
		board[0][3] = d1;
		Location e1 = new Location("e1");
		board[0][4] = e1;
		Location f1 = new Location("f1");
		board[0][5] = f1;
		Location g1 = new Location("g1");
		board[0][6] = g1;
		Location h1 = new Location("h1");
		board[0][7] = h1;
		
		Location a2 = new Location("a2");
		board[1][0] = a2;
		Location b2 = new Location("b2");
		board[1][1] = b2;
		Location c2 = new Location("c2");
		board[1][2] = c2;
		Location d2 = new Location("d2");
		board[1][3] = d2;
		Location e2 = new Location("e2");
		board[1][4] = e2;
		Location f2 = new Location("f2");
		board[1][5] = f2;
		Location g2 = new Location("g2");
		board[1][6] = g2;
		Location h2 = new Location("h2");
		board[1][7] = h2;
		
		Location a3 = new Location("a3");
		board[2][0] = a3;
		Location b3 = new Location("b3");
		board[2][1] = b3;
		Location c3 = new Location("c3");
		board[2][2] = c3;
		Location d3 = new Location("d3");
		board[2][3] = d3;
		Location e3 = new Location("e3");
		board[2][4] = e3;
		Location f3 = new Location("f3");
		board[2][5] = f3;
		Location g3 = new Location("g3");
		board[2][6] = g3;
		Location h3 = new Location("h3");
		board[2][7] = h3;
		
		Location a4 = new Location("a4");
		board[3][0] = a4;
		Location b4 = new Location("b4");
		board[3][1] = b4;
		Location c4 = new Location("c4");
		board[3][2] = c4;
		Location d4 = new Location("d4");
		board[3][3] = d4;
		Location e4 = new Location("e4");
		board[3][4] = e4;
		Location f4 = new Location("f4");
		board[3][5] = f4;
		Location g4 = new Location("g4");
		board[3][6] = g4;
		Location h4 = new Location("h4");
		board[3][7] = h4;
		
		Location a5 = new Location("a5");
		board[4][0] = a5;
		Location b5 = new Location("b5");
		board[4][1] = b5;
		Location c5 = new Location("c5");
		board[4][2] = c5;
		Location d5 = new Location("d5");
		board[4][3] = d5;
		Location e5 = new Location("e5");
		board[4][4] = e5;
		Location f5 = new Location("f5");
		board[4][5] = f5;
		Location g5 = new Location("g5");
		board[4][6] = g5;
		Location h5 = new Location("h5");
		board[4][7] = h5;
		
		Location a6 = new Location("a6");
		board[5][0] = a6;
		Location b6 = new Location("b6");
		board[5][1] = b6;
		Location c6 = new Location("c6");
		board[5][2] = c6;
		Location d6 = new Location("d6");
		board[5][3] = d6;
		Location e6 = new Location("e6");
		board[5][4] = e6;
		Location f6 = new Location("f6");
		board[5][5] = f6;
		Location g6 = new Location("g6");
		board[5][6] = g6;
		Location h6 = new Location("h6");
		board[5][7] = h6;
		
		Location a7 = new Location("a7");
		board[6][0] = a7;
		Location b7 = new Location("b7");
		board[6][1] = b7;
		Location c7 = new Location("c7");
		board[6][2] = c7;
		Location d7 = new Location("d7");
		board[6][3] = d7;
		Location e7 = new Location("e7");
		board[6][4] = e7;
		Location f7 = new Location("f7");
		board[6][5] = f7;
		Location g7 = new Location("g7");
		board[6][6] = g7;
		Location h7 = new Location("h7");
		board[6][7] = h7;
		
		Location a8 = new Location("a8");
		board[7][0] = a8;
		Location b8 = new Location("b8");
		board[7][1] = b8;
		Location c8 = new Location("c8");
		board[7][2] = c8;
		Location d8 = new Location("d8");
		board[7][3] = d8;
		Location e8 = new Location("e8");
		board[7][4] = e8;
		Location f8 = new Location("f8");
		board[7][5] = f8;
		Location g8 = new Location("g8");
		board[7][6] = g8;
		Location h8 = new Location("h8");
		board[7][7] = h8;
		
		
		Pawn wp1 = new Pawn(new Location("a2"), "w", 1);
		Pawn wp2 = new Pawn(new Location("b2"), "w", 2);
		Pawn wp3 = new Pawn(new Location("c2"), "w", 3);
		Pawn wp4 = new Pawn(new Location("d2"), "w", 4);
		Pawn wp5 = new Pawn(new Location("e2"), "w", 5);
		Pawn wp6 = new Pawn(new Location("f2"), "w", 6);
		Pawn wp7 = new Pawn(new Location("g2"), "w", 7);
		Pawn wp8 = new Pawn(new Location("h2"), "w", 8);
		Pawn bp1 = new Pawn(new Location("a7"), "b", 1);
		Pawn bp2 = new Pawn(new Location("b7"), "b", 2);
		Pawn bp3 = new Pawn(new Location("c7"), "b", 3);
		Pawn bp4 = new Pawn(new Location("d7"), "b", 4);
		Pawn bp5 = new Pawn(new Location("e7"), "b", 5);
		Pawn bp6 = new Pawn(new Location("f7"), "b", 6);
		Pawn bp7 = new Pawn(new Location("g7"), "b", 7);
		Pawn bp8 = new Pawn(new Location("h7"), "b", 8);
		Castle wc1 = new Castle(new Location("a1"), "w", 1);
		Castle wc2 = new Castle(new Location("h1"), "w", 2);
		Horse wh1 = new Horse(new Location("b1"), "w", 1);
		Horse wh2 = new Horse(new Location("g1"), "w", 2);
		Bishop wb1 = new Bishop(new Location("c1"), "w", 1);
		Bishop wb2 = new Bishop(new Location("f1"), "w", 2);
		Queen wq1 = new Queen(new Location("d1"), "w", 1);
		King wk1 = new King(new Location("e1"), "w", 2);
		Castle bc1 = new Castle(new Location("a8"), "b", 1);
		Castle bc2 = new Castle(new Location("h8"), "b", 2);
		Horse bh1 = new Horse(new Location("b8"), "b", 1);
		Horse bh2 = new Horse(new Location("g8"), "b", 2);
		Bishop bb1 = new Bishop(new Location("c8"), "b", 1);
		Bishop bb2 = new Bishop(new Location("f8"), "b", 2);
		Queen bq1 = new Queen(new Location("d8"), "b", 1);
		King bk1 = new King(new Location("e8"), "b", 1);
		
		// add pieces to actives
		actives.add(wp1);
		actives.add(wp2);
		actives.add(wp3);
		actives.add(wp4);
		actives.add(wp5);
		actives.add(wp6);
		actives.add(wp7);
		actives.add(wp8);
		actives.add(bp1);
		actives.add(bp2);
		actives.add(bp3);
		actives.add(bp4);
		actives.add(bp5);
		actives.add(bp6);
		actives.add(bp7);
		actives.add(bp8);
		actives.add(wc1);
		actives.add(wc2);
		actives.add(bc1);
		actives.add(bc2);
		actives.add(wh1);
		actives.add(wh2);
		actives.add(bh1);
		actives.add(bh2);
		actives.add(wb1);
		actives.add(wb2);
		actives.add(bb1);
		actives.add(bb2);
		actives.add(bq1);
		actives.add(wq1);
		actives.add(bk1);
		actives.add(wk1);
		
		whiteActives.add(wp1);
		whiteActives.add(wp2);
		whiteActives.add(wp3);
		whiteActives.add(wp4);
		whiteActives.add(wp5);
		whiteActives.add(wp6);
		whiteActives.add(wp7);
		whiteActives.add(wp8);
		blackActives.add(bp1);
		blackActives.add(bp2);
		blackActives.add(bp3);
		blackActives.add(bp4);
		blackActives.add(bp5);
		blackActives.add(bp6);
		blackActives.add(bp7);
		blackActives.add(bp8);
		whiteActives.add(wc1);
		whiteActives.add(wc2);
		blackActives.add(bc1);
		blackActives.add(bc2);
		whiteActives.add(wh1);
		whiteActives.add(wh2);
		blackActives.add(bh1);
		blackActives.add(bh2);
		whiteActives.add(wb1);
		whiteActives.add(wb2);
		blackActives.add(bb1);
		blackActives.add(bb2);
		blackActives.add(bq1);
		whiteActives.add(wq1);
		blackActives.add(bk1);
		whiteActives.add(wk1);
		
		// add pieces to hash map
		map.put(a2, wp1);
		map.put(b2, wp2);
		map.put(c2, wp3);
		map.put(d2, wp4);
		map.put(e2, wp5);
		map.put(f2, wp6);
		map.put(g2, wp7);
		map.put(h2, wp8);
		map.put(a7, bp1);
		map.put(b7, bp2);
		map.put(c7, bp3);
		map.put(d7, bp4);
		map.put(e7, bp5);
		map.put(f7, bp6);
		map.put(g7, bp7);
		map.put(h7, bp8);
		map.put(a1, wc1);
		map.put(b1, wh1);
		map.put(c1, wb1);
		map.put(d1, wq1);
		map.put(e1, wk1);
		map.put(f1, wb2);
		map.put(g1, wh2);
		map.put(h1, wc2);
		map.put(a8, bc1);
		map.put(b8, bh1);
		map.put(c8, bb1);
		map.put(d8, bq1);
		map.put(e8, bk1);
		map.put(f8, bb2);
		map.put(g8, bh2);
		map.put(h8, bc2);
		
		
		ArrayList<ChessPiece> allPawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allRooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKnights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allBishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allQueens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKings = new ArrayList<ChessPiece>();
		
		Game g = new Game(0, map, actives, blackActives, whiteActives, board, false, false);
		HashMap<String, ArrayList<ChessPiece>> lookup = new HashMap<String, ArrayList<ChessPiece>>();
		ArrayList<ChessPiece> pawns = new ArrayList<ChessPiece>();
		pawns.add(wp1);
		pawns.add(wp2);
		pawns.add(wp3);
		pawns.add(wp4);
		pawns.add(wp5);
		pawns.add(wp6);
		pawns.add(wp7);
		pawns.add(wp8);
		lookup.put("P", pawns);
		ArrayList<ChessPiece> pawns2 = new ArrayList<ChessPiece>();
		pawns2.add(bp1);
		pawns2.add(bp2);
		pawns2.add(bp3);
		pawns2.add(bp4);
		pawns2.add(bp5);
		pawns2.add(bp6);
		pawns2.add(bp7);
		pawns2.add(bp8);
		lookup.put("p", pawns2);
		ArrayList<ChessPiece> rooks = new ArrayList<ChessPiece>();
		rooks.add(wc1);
		rooks.add(wc2);
		lookup.put("R", rooks);
		ArrayList<ChessPiece> rooks2 = new ArrayList<ChessPiece>();
		rooks2.add(bc1);
		rooks2.add(bc2);
		lookup.put("r", rooks2);
		ArrayList<ChessPiece> knights = new ArrayList<ChessPiece>();
		knights.add(wh1);
		knights.add(wh2);
		lookup.put("N", knights);
		ArrayList<ChessPiece> knights2 = new ArrayList<ChessPiece>();
		knights2.add(bh1);
		knights2.add(bh2);
		lookup.put("n", knights2);
		ArrayList<ChessPiece> bishops = new ArrayList<ChessPiece>();
		bishops.add(wb1);
		bishops.add(wb2);
		lookup.put("B", bishops);
		ArrayList<ChessPiece> bishops2 = new ArrayList<ChessPiece>();
		bishops2.add(bb1);
		bishops2.add(bb2);
		lookup.put("b", bishops2);
		ArrayList<ChessPiece> queens = new ArrayList<ChessPiece>();
		queens.add(wq1);
		lookup.put("Q", queens);
		ArrayList<ChessPiece> queens2 = new ArrayList<ChessPiece>();
		queens2.add(bq1);
		lookup.put("q", queens2);
		ArrayList<ChessPiece> kings = new ArrayList<ChessPiece>();
		kings.add(wk1);
		lookup.put("K", kings);
		ArrayList<ChessPiece> kings2 = new ArrayList<ChessPiece>();
		kings2.add(bk1);
		
		allPawns.addAll(pawns);
		allPawns.addAll(pawns2);
		allRooks.addAll(rooks);
		allRooks.addAll(rooks2);
		allKnights.addAll(knights);
		allKnights.addAll(knights2);
		allBishops.addAll(bishops);
		allBishops.addAll(bishops2);
		allQueens.addAll(queens);
		allQueens.addAll(queens2);
		allKings.addAll(kings);
		allKings.addAll(kings2);
		
		
		lookup.put("k", kings2);
		lookup.put("g", actives);
		lookup.put("G", actives);
		lookup.put("d", blackActives);
		lookup.put("D", blackActives);
		lookup.put("l", whiteActives);
		lookup.put("L", whiteActives);
		lookup.put("1", allPawns);
		lookup.put("2", allRooks);
		lookup.put("3", allKnights);
		lookup.put("4", allBishops);
		lookup.put("5", allQueens);
		lookup.put("6", allKings);
		g.setStringToCP(lookup);
		return g;
	}
	
	public static Game setupSpecialLayout(String initialConfig) {
		Game g = null;
		HashMap<Location, ChessPiece> map = new HashMap<Location, ChessPiece>();
		ArrayList<ChessPiece> actives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> blackActives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> whiteActives = new ArrayList<ChessPiece>();
		HashMap<String, ArrayList<ChessPiece>> lookup = new HashMap<String, ArrayList<ChessPiece>>();
		ArrayList<ChessPiece> pawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> pawns2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> rooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> rooks2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> knights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> knights2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> bishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> bishops2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> queens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> queens2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> kings = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> kings2 = new ArrayList<ChessPiece>();
		
		ArrayList<ChessPiece> allPawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allRooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKnights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allBishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allQueens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKings = new ArrayList<ChessPiece>();
		
		Location[][] board = new Location[8][8];
		Location a1 = new Location("a1");
		board[0][0] = a1;
		Location b1 = new Location("b1");
		board[0][1] = b1;
		Location c1 = new Location("c1");
		board[0][2] = c1;
		Location d1 = new Location("d1");
		board[0][3] = d1;
		Location e1 = new Location("e1");
		board[0][4] = e1;
		Location f1 = new Location("f1");
		board[0][5] = f1;
		Location g1 = new Location("g1");
		board[0][6] = g1;
		Location h1 = new Location("h1");
		board[0][7] = h1;
		
		Location a2 = new Location("a2");
		board[1][0] = a2;
		Location b2 = new Location("b2");
		board[1][1] = b2;
		Location c2 = new Location("c2");
		board[1][2] = c2;
		Location d2 = new Location("d2");
		board[1][3] = d2;
		Location e2 = new Location("e2");
		board[1][4] = e2;
		Location f2 = new Location("f2");
		board[1][5] = f2;
		Location g2 = new Location("g2");
		board[1][6] = g2;
		Location h2 = new Location("h2");
		board[1][7] = h2;
		
		Location a3 = new Location("a3");
		board[2][0] = a3;
		Location b3 = new Location("b3");
		board[2][1] = b3;
		Location c3 = new Location("c3");
		board[2][2] = c3;
		Location d3 = new Location("d3");
		board[2][3] = d3;
		Location e3 = new Location("e3");
		board[2][4] = e3;
		Location f3 = new Location("f3");
		board[2][5] = f3;
		Location g3 = new Location("g3");
		board[2][6] = g3;
		Location h3 = new Location("h3");
		board[2][7] = h3;
		
		Location a4 = new Location("a4");
		board[3][0] = a4;
		Location b4 = new Location("b4");
		board[3][1] = b4;
		Location c4 = new Location("c4");
		board[3][2] = c4;
		Location d4 = new Location("d4");
		board[3][3] = d4;
		Location e4 = new Location("e4");
		board[3][4] = e4;
		Location f4 = new Location("f4");
		board[3][5] = f4;
		Location g4 = new Location("g4");
		board[3][6] = g4;
		Location h4 = new Location("h4");
		board[3][7] = h4;
		
		Location a5 = new Location("a5");
		board[4][0] = a5;
		Location b5 = new Location("b5");
		board[4][1] = b5;
		Location c5 = new Location("c5");
		board[4][2] = c5;
		Location d5 = new Location("d5");
		board[4][3] = d5;
		Location e5 = new Location("e5");
		board[4][4] = e5;
		Location f5 = new Location("f5");
		board[4][5] = f5;
		Location g5 = new Location("g5");
		board[4][6] = g5;
		Location h5 = new Location("h5");
		board[4][7] = h5;
		
		Location a6 = new Location("a6");
		board[5][0] = a6;
		Location b6 = new Location("b6");
		board[5][1] = b6;
		Location c6 = new Location("c6");
		board[5][2] = c6;
		Location d6 = new Location("d6");
		board[5][3] = d6;
		Location e6 = new Location("e6");
		board[5][4] = e6;
		Location f6 = new Location("f6");
		board[5][5] = f6;
		Location g6 = new Location("g6");
		board[5][6] = g6;
		Location h6 = new Location("h6");
		board[5][7] = h6;
		
		Location a7 = new Location("a7");
		board[6][0] = a7;
		Location b7 = new Location("b7");
		board[6][1] = b7;
		Location c7 = new Location("c7");
		board[6][2] = c7;
		Location d7 = new Location("d7");
		board[6][3] = d7;
		Location e7 = new Location("e7");
		board[6][4] = e7;
		Location f7 = new Location("f7");
		board[6][5] = f7;
		Location g7 = new Location("g7");
		board[6][6] = g7;
		Location h7 = new Location("h7");
		board[6][7] = h7;
		
		Location a8 = new Location("a8");
		board[7][0] = a8;
		Location b8 = new Location("b8");
		board[7][1] = b8;
		Location c8 = new Location("c8");
		board[7][2] = c8;
		Location d8 = new Location("d8");
		board[7][3] = d8;
		Location e8 = new Location("e8");
		board[7][4] = e8;
		Location f8 = new Location("f8");
		board[7][5] = f8;
		Location g8 = new Location("g8");
		board[7][6] = g8;
		Location h8 = new Location("h8");
		board[7][7] = h8;
		try {
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(initialConfig);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				String piece = strLine.substring(0, 1);
				//System.out.println("text: " + strLine.substring(2, 4));
				Location location = Util.getLocation(strLine.substring(2, 4), board);
				//System.out.println("location2: " + location2);
				int pawnCounter = 1;
				int PawnCounter = 1;
				int rookCounter = 1;
				int RookCounter = 1;
				int knightCounter = 1;
				int KnightCounter = 1;
				int bishopCounter = 1;
				int BishopCounter = 1;
				int queenCounter = 1;
				int QueenCounter = 1;
				int kingCounter = 1;
				int KingCounter = 1;
				if (piece.equals("p")) {
					Pawn p = new Pawn(location, "b", pawnCounter);
					pawnCounter += 1;
					actives.add(p);
					blackActives.add(p);
					map.put(location, p);
					pawns2.add(p);
				} else if(piece.equals("P")) {
					Pawn p = new Pawn(location, "w", PawnCounter);
					PawnCounter += 1;
					actives.add(p);
					whiteActives.add(p);
					map.put(location, p);
					pawns.add(p);
				} else if (piece.equals("R")) {
					Castle c = new Castle(location, "w", RookCounter);
					RookCounter += 1;
					actives.add(c);
					whiteActives.add(c);
					map.put(location, c);
					rooks.add(c);
				} else if (piece.equals("r")) {
					Castle c = new Castle(location, "b", rookCounter);
					rookCounter += 1;
					actives.add(c);
					blackActives.add(c);
					map.put(location,c);
					rooks2.add(c);
				} else if (piece.equals("n")) {
					Horse h = new Horse(location, "b", knightCounter);
					knightCounter += 1;
					actives.add(h);
					blackActives.add(h);
					map.put(location, h);
					knights2.add(h);
				} else if (piece.equals("N")) {
					Horse h = new Horse(location, "w", KnightCounter);
					KnightCounter += 1;
					actives.add(h);
					whiteActives.add(h);
					map.put(location, h);
					knights.add(h);
				} else if (piece.equals("B")) {
					Bishop b = new Bishop(location, "w", BishopCounter);
					BishopCounter += 1;
					actives.add(b);
					whiteActives.add(b);
					map.put(location, b);
					bishops.add(b);
				} else if(piece.equals("b")) {
					Bishop b = new Bishop(location, "b", bishopCounter);
					bishopCounter += 1;
					actives.add(b);
					blackActives.add(b);
					map.put(location, b);
					bishops2.add(b);
				} else if (piece.equals("Q")) {
					Queen q = new Queen(location, "w", QueenCounter);
					QueenCounter += 1;
					actives.add(q);
					whiteActives.add(q);
					map.put(location, q);
					queens.add(q);
				} else if (piece.equals("q")) {
					Queen q = new Queen(location, "b", queenCounter);
					queenCounter += 1;
					actives.add(q);
					blackActives.add(q);
					map.put(location, q);
					queens2.add(q);
				} else if (piece.equals("K")) {
					King k = new King(location, "w", KingCounter);
					KingCounter += 1;
					actives.add(k);
					whiteActives.add(k);
					map.put(location, k);
					kings.add(k);
				} else if (piece.equals("k")) {
					King k = new King(location, "b", kingCounter);
					kingCounter += 1;
					actives.add(k);
					blackActives.add(k);
					map.put(location, k);
					kings2.add(k);
				}
			}
			
			allPawns.addAll(pawns);
			allPawns.addAll(pawns2);
			allRooks.addAll(rooks);
			allRooks.addAll(rooks2);
			allKnights.addAll(knights);
			allKnights.addAll(knights2);
			allBishops.addAll(bishops);
			allBishops.addAll(bishops2);
			allQueens.addAll(queens);
			allQueens.addAll(queens2);
			allKings.addAll(kings);
			allKings.addAll(kings2);
			
			
			lookup.put("P", pawns);
			lookup.put("p", pawns2);
			lookup.put("R", rooks);
			lookup.put("r", rooks2);
			lookup.put("N", knights);
			lookup.put("n", knights2);
			lookup.put("B", bishops);
			lookup.put("b", bishops2);
			lookup.put("Q", queens);
			lookup.put("q", queens2);
			lookup.put("K", kings);
			lookup.put("k", kings2);
			lookup.put("g", actives);
			lookup.put("G", actives);
			lookup.put("d", blackActives);
			lookup.put("D", blackActives);
			lookup.put("l", whiteActives);
			lookup.put("L", whiteActives);
			lookup.put("1", allPawns);
			lookup.put("2", allRooks);
			lookup.put("3", allKnights);
			lookup.put("4", allBishops);
			lookup.put("5", allQueens);
			lookup.put("6", allKings);
			g = new Game(0, map, actives, blackActives, whiteActives, board, false, false);
			g.setStringToCP(lookup);
			//Close the input stream
			in.close();
		} catch (Exception e){//Catch exception if any
			System.err.println("An error occurred trying to open the initialConfig file");
			System.err.println("Error: " + e.getMessage());
			System.exit(-1);
		}
		return g;
	}
		

}
