package com.wroblicky.andrew.joust.game.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.Move;

public final class ChessBoard {

	private final Location[][] chessBoard;
	private final Map<String, Location> chessPieceToLocation;

	// constructors
	public ChessBoard() {
		this.chessBoard = new Location[8][8];
		this.chessPieceToLocation = new HashMap<String, Location>();
		createBackingBoard();
	}

	public ChessBoard(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
		this.chessPieceToLocation = new HashMap<String, Location>();
	}

	/**
	 * Returns the Location object associated with a provided algebraic position
	 */
	public Location getLocation(String algebraicLocation) {
		int x = Util.fileToNum(algebraicLocation.substring(0, 1)) - 1;
		int y = Util.rankToNum(algebraicLocation.substring(1, 2)) - 1;
		if (onBoard(x, y)) {
			return chessBoard[x][y];
		}
		return null;
	}

	/**
	 * Returns the location at a given x, y
	 *
	 * @param x
	 *            (0 to 7 inclusive are allowed values)
	 * @param y
	 *            (0 to 7 inclusive are allowed values)
	 */
	public Location getLocation(int x, int y) {
		if (onBoard(x, y)) {
			return chessBoard[x][y];
		}
		return null;
	}

	/**
	 * Returns the location that is deltaX and deltaY spaces away.
	 *
	 * @param location
	 * @param deltaX
	 *            (positive indicates movement to the right)
	 * @param deltaY
	 *            (positive indicates movement forward)
	 */
	public Location getLocation(Location location, int deltaX, int deltaY) {
		int x = location.getXCoordinate() + deltaX;
		int y = location.getYCoordinate() + deltaY;

		return getLocation(x, y);
	}

	public Location getNorthNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate();
		int y = location.getYCoordinate() + 1;

		return getLocation(x, y);
	}

	public Location getNorthWestNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() - 1;
		int y = location.getYCoordinate() + 1;

		return getLocation(x, y);
	}

	public Location getWestNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() - 1;
		int y = location.getYCoordinate();

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
		int x = location.getXCoordinate();
		int y = location.getYCoordinate() - 1;

		return getLocation(x, y);
	}

	public Location getSouthEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() + 1;
		int y = location.getYCoordinate() - 1;

		return getLocation(x, y);
	}

	public Location getEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() + 1;
		int y = location.getYCoordinate();

		return getLocation(x, y);
	}

	public Location getNorthEastNeighbor(Location location) {
		// get adjusted x and y
		int x = location.getXCoordinate() + 1;
		int y = location.getYCoordinate() + 1;

		return getLocation(x, y);
	}

	/**
	 * Adds a Location object to the chess board
	 */
	public void addLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		if (onBoard(x, y)) {
			chessBoard[x][y] = location;
		} else {
			throw new RuntimeException("Can't add location:"
					+ location.getAlgebraicLocation());
		}
	}

	/**
	 * Returns whether or not a given x, y are within the domain of the board
	 *
	 * @param x
	 *            (0 to 7 inclusive)
	 * @param y
	 *            (0 to 7 inclusive)
	 */
	public static boolean onBoard(int x, int y) {
		if (x >= 0 && x < 8 && y >= 0 && y < 8) {
			return true;
		}
		return false;
	}

	/**
	 * Given a chess piece, returns the Location associated with it
	 */
	public Location getLocationByChessPiece(ChessPiece chessPiece) {
		return chessPieceToLocation.get(chessPiece.getID());
	}

	/**
	 * Given a Location object, returns the chess piece associated with it
	 */
	public ChessPiece getChessPieceByLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		return chessBoard[x][y].getChessPiece();
	}

	/**
	 * Adds a chess piece to the chess board
	 */
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		addChessPieceToBoard(chessPiece, location);
		chessPieceToLocation.put(chessPiece.getID(), location);
	}

	/**
	 * Helper method that adds the chess piece to the chessBoard array
	 */
	private void addChessPieceToBoard(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		if (chessBoard[x][y].getChessPiece() != null) {
			throw new RuntimeException("The chess piece " + chessPiece
					+ " can't take " + " this location because "
					+ chessBoard[x][y].getChessPiece()
					+ " is already occupying it");
		}
		chessBoard[x][y].setChessPiece(chessPiece);
	}

	/**
	 * Moves the chess piece on the board
	 */
	public void moveChessPiece(Move move) {
		removeChessPiece(move.getChessPiece(), move.getStart());
		addChessPiece(move.getChessPiece(), move.getDestination());
	}

	/**
	 * Moves the chess piece on the board
	 */
	public void moveChessPiece(ChessPiece chessPiece, Location start,
			Location destination) {
		removeChessPiece(chessPiece, start);
		addChessPiece(chessPiece, destination);
	}

	/**
	 * Removes the chess piece from the board
	 */
	public void removeChessPiece(ChessPiece chessPiece) {
		removeChessPieceFromBoard(chessPiece,
				this.getLocationByChessPiece(chessPiece));
		chessPieceToLocation.remove(chessPiece);
	}

	/**
	 * Removes the chess piece from the board
	 */
	public void removeChessPiece(ChessPiece chessPiece, Location location) {
		removeChessPieceFromBoard(chessPiece, location);
		chessPieceToLocation.remove(chessPiece);
	}

	/**
	 * Helper method that removes the chess piece from the chessBoard array
	 */
	private void removeChessPieceFromBoard(ChessPiece chessPiece,
			Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(null);
	}

	/**
	 * Returns an iterator to loop over the locations on the board
	 */
	public ChessBoardIterator iterator() {
		return new ChessBoardIterator(chessBoard);
	}

	/**
	 * Initializes the Location objects for every space of the board
	 */
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

	/**
	 * Convenience method to visualize the board in standard out
	 */
	public static void printBoard(Set<ChessPiece> chessPieces) {
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; // denotes unoccupied
			}
		}
		for (ChessPiece chessPiece : chessPieces) {
			ChessPiece current = chessPiece;
			int currX = current.getLocation().getXCoordinate();
			int currY = current.getLocation().getYCoordinate();
			board[currX][currY] = current.getChessPieceAllegianceType()
					.getChessPieceAllegianceType();
		}

		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
}