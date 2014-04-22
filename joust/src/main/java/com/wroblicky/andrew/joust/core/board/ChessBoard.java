package com.wroblicky.andrew.joust.core.board;

import java.util.HashMap;
import java.util.Map;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.general.move.Move;

public class ChessBoard {
	
	private Location[][] chessBoard;
	private Map<ChessPiece, Location> chessPieceToLocation;
	
	public enum Direction {
			
		NORTH("north"),
		NORTHWEST("northwest"),
		WEST("west"),
		SOUTHWEST("southwest"),
		SOUTH("south"),
		SOUTHEAST("southeast"),
		EAST("east"),
		NORTHEAST("northeast");
		
		private String direction;
		
		Direction (String direction) {
			this.direction = direction;
		}
		
		public String getDirection() {
			return this.direction;
		}
		
		public Direction reverse() {
			if (direction.equals("north")) {
				return SOUTH;
			} else if (direction.equals("northwest")) {
				return SOUTHWEST;
			} else if (direction.equals("northeast")) {
				return SOUTHEAST;
			} else if (direction.equals("south")) {
				return NORTH;
			} else if (direction.equals("southwest")) {
				return NORTHWEST;
			} else if (direction.equals("southeast")) {
				return NORTHEAST;
			} else if (direction.equals("west")) {
				return WEST;
			} else {
				return EAST;
			}
		}
	}
	
	// constructors
	public ChessBoard() {
		this.chessBoard = createBackingBoard();
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
	}
	
	public ChessBoard(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
	}
	
	public Location getLocation(String algebraicLocation) {
		int x = Util.fileToNum(algebraicLocation.substring(0, 1));
		int y = Util.rankToNum(algebraicLocation.substring(1, 2));
		if (onBoard(x, y)) {
			return chessBoard[x][y];
		} else {
			return null;
		}
	}
	
	public Location getLocation(int x, int y) {
		if (onBoard(x, y)) {
			return chessBoard[x][y];
		} else {
			return null;
		}
	}
	
	private boolean onBoard(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return true;
		} else {
			return false;
		}
	}
	
	public Location getNorthNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate();
		int y = location.getYCoordinate() + 1;
		
		return getLocation(x, y);
	}
	
	public Location getNorthWestNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() + 1;
		int y = location.getYCoordinate() - 1;
		
		return getLocation(x, y);
	}
	
	public Location getWestNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate();
		int y = location.getYCoordinate() - 1;
		
		return getLocation(x, y);
	}
	
	public Location getSouthWestNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() - 1;
		int y = location.getYCoordinate() - 1;
		
		return getLocation(x, y);
	}
	
	public Location getSouthNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() - 1;
		int y = location.getYCoordinate();
		
		return getLocation(x, y);
	}
	
	public Location getSouthEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() - 1;
		int y = location.getYCoordinate() + 1;
		
		return getLocation(x, y);
	}
	
	public Location getEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate();
		int y = location.getYCoordinate() + 1;
		
		return getLocation(x, y);
	}
	
	public Location getNorthEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() + 1;
		int y = location.getYCoordinate() + 1;
		
		return getLocation(x, y);
	}
	
	public Location getLocation(Location initial, int numSpaces, Direction direction,
			Allegiance allegiance) {
		int spacesLeft = numSpaces;
		while (spacesLeft > 0) {
			
		}
		return initial;
	}
	
	public Location getLocationByChessPiece(ChessPiece chessPiece) {
		return chessPieceToLocation.get(chessPiece);
	}
	
	public ChessPiece getChessPieceByLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		return chessBoard[x][y].getChessPiece();
	}
	
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		addChessPieceToBoard(chessPiece, location);
		chessPieceToLocation.put(chessPiece, location);
	}
	
	private void addChessPieceToBoard(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(chessPiece);
	}
	
	public void moveChessPiece(Move move) {
		removeChessPiece(move.getChessPiece(), move.getStart());
		addChessPiece(move.getChessPiece(), move.getDestination());
	}
	
	public void removeChessPiece(ChessPiece chessPiece, Location location) {
		removeChessPieceFromBoard(chessPiece, location);
		chessPieceToLocation.remove(chessPiece);
	}
	
	private void removeChessPieceFromBoard(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(null);
	}
	
	@Deprecated
	public Location[][] getBoard() {
		return chessBoard;
	}
	
	private Location[][] createBackingBoard() {
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
		return board;
	}

	public void printBoard() {
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(chessBoard[i][j].getChessPiece().getMySymbol() + " ");
			}
			System.out.println("");
		}
	}
}
