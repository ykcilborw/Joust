package com.wroblicky.andrew.joust.core.board;

import java.util.ArrayList;
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
		this.chessBoard = new Location[8][8];
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
		createBackingBoard();
	}
	
	public ChessBoard(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
	}
	
	public Location getLocation(String algebraicLocation) {
		int x = Util.fileToNum(algebraicLocation.substring(0, 1)) - 1;
		int y = Util.rankToNum(algebraicLocation.substring(1, 2)) - 1;
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
	
	public void addLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		if (onBoard(x, y)) {
			chessBoard[x][y] = location;
		} else {
			System.out.println("can't add location:" + location.getAlgebraicLocation());
		}
	}
	
	public boolean onBoard(int x, int y) {
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
	
	private void createBackingBoard() {
		ArrayList<String> fileValues = new ArrayList<String>();
		fileValues.add("a");
		fileValues.add("b");
		fileValues.add("c");
		fileValues.add("d");
		fileValues.add("e");
		fileValues.add("f");
		fileValues.add("g");
		fileValues.add("h");
		
		for (String file : fileValues) {
			for (int i = 1; i < 9; i++) {
				this.addLocation(new Location(file + i));
			}
		}
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
