package com.wroblicky.andrew.joust.game.chesspiece;

import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.FRIEND;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.List;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

/**
 * Abstract class representing a chess piece
 *
 * @author Andrew Wroblicky
 *
 */
public abstract class ChessPiece {

	boolean alive;
	Allegiance allegiance;
	String chessID; // represents original starting location
	ChessBoard chessBoard;

	public enum Allegiance {

		BLACK("black"), WHITE("white");

		private final String allegiance;

		Allegiance(String allegiance) {
			this.allegiance = allegiance;
		}

		public String getAllegiance() {
			return this.allegiance;
		}
	}

	public enum Occupier {

		FRIEND("friend"), ENEMY("enemy"), UNOCCUPIED("unoccupied");

		private final String occupier;

		Occupier(String occupier) {
			this.occupier = occupier;
		}

		public String getOccupier() {
			return occupier;
		}
	}

	public Location getLocation() {
		return chessBoard.getLocationByChessPiece(this);
	}

	public String getFile() {
		Location location = chessBoard.getLocationByChessPiece(this);
		if (location != null) {
			return location.getFile();
		}
		return "";
	}

	public String getRank() {
		Location location = chessBoard.getLocationByChessPiece(this);
		if (location != null) {
			return location.getRank();
		}
		return "";
	}

	/**
	 * Returns the rank relative to whether or not the chess piece is black or
	 * white.
	 */
	public String getRelativeRank() {
		String rank = getRank();
		if (allegiance == Allegiance.BLACK) {
			int x = Util.rankToNum(rank);
			int toReturn = 0;
			if (x == 1) {
				toReturn = 8;
			} else if (x == 2) {
				toReturn = 7;
			} else if (x == 3) {
				toReturn = 6;
			} else if (x == 4) {
				toReturn = 5;
			} else if (x == 5) {
				toReturn = 4;
			} else if (x == 6) {
				toReturn = 3;
			} else if (x == 7) {
				toReturn = 2;
			} else {
				toReturn = 1;
			}
			return "" + toReturn;
		}
		return rank;
	}

	// in game play related methods
	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	// allegiance related methods
	public Allegiance getAllegiance() {
		return allegiance;
	}

	public boolean isBlack() {
		return (allegiance == Allegiance.BLACK);
	}

	public boolean isWhite() {
		return (allegiance == Allegiance.WHITE);
	}

	// id related method
	public String getID() {
		return chessID;
	}

	/**
	 * Returns the full name of the particular chess piece.
	 *
	 * e.g. Pawn, Queen, etc.
	 *
	 */
	public abstract ChessPieceType getChessPieceType();

	/**
	 * Returns a character representing the particular chess piece.
	 *
	 * e.g. q for queen
	 *
	 */
	public abstract ChessPieceAllegianceType getChessPieceAllegianceType();

	/**
	 * Returns a list of the possible positions, the current chess piece can
	 * move to
	 *
	 */
	public abstract List<Location> getPossibleMoves();

	/**
	 * Returns a list of the possible positions, the current chess piece can
	 * defend.
	 *
	 */
	public abstract List<Location> getDefenseMoves();

	/**
	 * Returns whether or not the chess piece can reach a particular location
	 * given a particular game.
	 *
	 */
	public boolean canReach(Location location) {
		List<Location> possibilities = getPossibleMoves();
		for (Location possibility : possibilities) {
			if (possibility.equals(location)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns whether or not the chess piece can defend a unit on a particular
	 * location given a particular game.
	 *
	 */
	public boolean canDefend(Location location) {
		List<Location> possibilities = getDefenseMoves();
		for (Location possibility : possibilities) {
			if (possibility.equals(location)) {
				return true;
			}
		}
		return false;
	}

	boolean isCapableOfOccupation(Location location) {
		if (location != null) {
			int x = location.getXCoordinate();
			int y = location.getYCoordinate();
			return (checkAvailability(location) == UNOCCUPIED || checkAvailability(location) == ENEMY)
					&& ChessBoard.onBoard(x, y);
		}
		return false;
	}

	boolean isFriend(Location location) {
		if (location != null) {
			int x = location.getXCoordinate();
			int y = location.getYCoordinate();
			return checkAvailability(location) == FRIEND
					&& ChessBoard.onBoard(x, y);
		}
		return false;
	}

	/**
	 * Determines the relationship of the chess piece to a particular location
	 */
	public Occupier checkAvailability(Location location) {
		Occupier toReturn = UNOCCUPIED;
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		if (ChessBoard.onBoard(x, y)) {
			Location newL = chessBoard.getLocation(x, y);
			ChessPiece c = chessBoard.getChessPieceByLocation(newL);
			if (c == null) {
				toReturn = UNOCCUPIED;
			} else if (c.getAllegiance() == this.getAllegiance()) {
				toReturn = FRIEND;
			} else {
				toReturn = ENEMY;
			}
		}
		return toReturn;
	}

	/**
	 * Updates the board by moving the chess piece to the new position.
	 */
	public void move(Location newLocation) {
		chessBoard.moveChessPiece(this,
				chessBoard.getLocationByChessPiece(this), newLocation);
	}

	@Override
	public String toString() {
		return "[ " + getChessPieceType() + ", " + getLocation() + " ]";
	}
}