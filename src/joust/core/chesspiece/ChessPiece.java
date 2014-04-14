package joust.core.chesspiece;

import java.util.ArrayList;
import java.util.HashMap;

import joust.core.general.Game;
import joust.core.general.Location;


public abstract class ChessPiece {
	
	Location myLocation;
	String myColor;
	boolean isAlive;
	int chessID;
	
	
	public Location getLocation() {
		return myLocation;
	}
	
	public String getFile() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(0, 1);
	}
	
	public String getRank() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(1, 2);
	}
	
	/**
	 * Returns the rank relative to whether or not the chess piece is
	 * black or white.
	 * 
	 */
	public String getRelativeRank() {
		String al = myLocation.getmyAlgebraicLocation().substring(1, 2);
		if (myColor.equals("b")) {
			int x = Integer.parseInt(al);
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
			} else if (x == 8) {
				toReturn = 1;
			}
			return new Integer(toReturn).toString();
		} else {
			return al;
		}
	}
	
	public String getColor() {
		return myColor;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
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
	public String checkAvailability(Game g, Location l) {
		String toReturn = "";
		//System.out.println("lx: " + (l.getmyX() - 1));
		//System.out.println("ly: " + (l.getmyY() - 1));
		int nextX = l.getmyX() - 1;
		int nextY = l.getmyY() - 1;
		if (nextX < 8 && nextX > -1 && nextY > -1 && nextY < 8) {
			//System.out.println("chess piece is in right range");
			Location newL = g.getBoard()[nextY][nextX];
			//System.out.println("newLx: " + newL.getmyX());
			//System.out.println("newLy: " + newL.getmyY());
			ChessPiece c = g.getmyPositions().get(newL);
			//System.out.println("c: " + c);
			if (c == null) {
				toReturn = "unoccupied";
			} else if (c.getColor().equals(this.getColor())) {
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
	 * Given a game and a location, the method updates the game's board by
	 * moving the chess piece to the new position.
	 *  
	 */
	public void move(Game g, Location l) {
		HashMap<Location, ChessPiece> positions = g.getmyPositions();
		Location onBoard = Location.getBoardLocation(g, myLocation);
		positions.remove(onBoard);
		myLocation = l;
		Location onBoard2 = Location.getBoardLocation(g, l);
		positions.remove(onBoard2);
		positions.put(onBoard2, this);
	}
	
	/**
	 * Updates the chess piece to indicate that it is no longer on the board
	 */
	public void nowDead() {
		isAlive = false;
	}
	
	public boolean equals(ChessPiece chessPiece) {
		boolean toReturn = false;
		if (chessID == chessPiece.getID() && chessPiece.getMyType().equals(this.getMyType())) {
			toReturn = true;
		}
		return toReturn;
	}
	
	public String toString() {
		return "[ " + getMyType() + ", " + getLocation() + " ]";
	}
}