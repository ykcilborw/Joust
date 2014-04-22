package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Game;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.general.move.Move;

/**
 * Abstract class representing a chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public abstract class ChessPiece {
	
	boolean alive;
	Allegiance allegiance;
	int chessID; // represents original starting location
	ChessBoard chessBoard;
	
	public enum Allegiance {
		
		BLACK("black"), 
		WHITE("white");
		
		private String allegiance;
		
		Allegiance (String allegiance) {
			this.allegiance = allegiance;
		}
		
		public String getAllegiance() {
			return this.allegiance;
		}
	}
	
	public enum Occupier {
		
		FRIEND("friend"),
		ENEMY("enemy"), 
		UNOCCUPIED("unoccupied");
		
		private String occupier;
		
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
		} else {
			return "";
		}
	}
	
	public String getRank() {
		Location location = chessBoard.getLocationByChessPiece(this);
		if (location != null) {
			return location.getRank();
		} else {
			return "";
		}
	}
	
	/**
	 * Returns the rank relative to whether or not the chess piece is
	 * black or white.
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
		} else {
			return rank;
		}
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
	public int getID() {
		return chessID;
	}
	
	/**
	 * Returns the full name of the particular chess piece.
	 * 
	 * e.g. Pawn, Queen, etc.
	 * 
	 */
	public abstract String getMyType();
	
	/**
	 * Returns a character representing the particular chess piece.
	 * 
	 * e.g. q for queen
	 * 
	 */
	public abstract String getMySymbol();
	
	/**
	 * Returns a list of the possible positions, the current chess piece
	 * can move to
	 * 
	 */
	public abstract ArrayList<Location> getPossibleMoves(Game g);

	/**
	 * Returns a list of the possible positions, the current chess piece
	 * can defend.
	 * 
	 */
	public abstract ArrayList<Location> getDefenseMoves(Game g);
	
	/**
	 * Returns whether or not the chess piece can reach a particular location
	 * given a particular game.
	 * 
	 */
	public abstract boolean canReach(Game g, Location l);
	
	/**
	 * Returns whether or not the chess piece can defend a unit on a particular location
	 * given a particular game.
	 * 
	 */
	public abstract boolean canDefend(Game g, Location l);
	
	//need to reimplement getdefensemoves and getpossible moves so not needlessly rechecking bounds
	public String checkAvailability(Location l) {
		String toReturn = "";
		//System.out.println("lx: " + (l.getXCoordinate() - 1));
		//System.out.println("ly: " + (l.getYCoordinate() - 1));
		int nextX = l.getXCoordinate() - 1;
		int nextY = l.getYCoordinate() - 1;
		if (nextX < 8 && nextX > -1 && nextY > -1 && nextY < 8) {
			//System.out.println("chess piece is in right range");
			Location newL = chessBoard.getBoard()[nextY][nextX];
			//System.out.println("newLx: " + newL.getXCoordinate());
			//System.out.println("newLy: " + newL.getYCoordinate());
			ChessPiece c = chessBoard.getChessPieceByLocation(newL);
			//System.out.println("c: " + c);
			if (c == null) {
				toReturn = "unoccupied";
			} else if (c.getAllegiance() == this.getAllegiance()) {
				toReturn = "friend";
			} else {
				toReturn = "enemy";
			}
		} else {
			toReturn = "unoccupied";
		}
		return toReturn;
	}
	
	/**
	 * Updates the board by moving the chess piece to the new position.
	 */
	public void move(Location newLocation) {
		chessBoard.moveChessPiece(new Move(this, chessBoard.getLocationByChessPiece(this),
				newLocation));
	}
	
	public String toString() {
		return "[ " + getMyType() + ", " + getLocation() + " ]";
	}
}