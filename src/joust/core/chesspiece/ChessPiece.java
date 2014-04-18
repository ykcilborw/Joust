package joust.core.chesspiece;

import java.util.ArrayList;
import java.util.HashMap;

import joust.core.board.ChessBoard;
import joust.core.general.Game;
import joust.core.general.Location;
import joust.core.general.move.Move;

/**
 * Abstract class representing a chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public abstract class ChessPiece {
	
	boolean alive;
	Allegiance allegiance;
	int chessID;
	ChessBoard chessBoard;
	
	public enum Allegiance {
		
		BLACK("black"), WHITE("white");
		
		private String allegiance;
		
		Allegiance (String allegiance) {
			this.allegiance = allegiance;
		}
		
		public String getAllegiance() {
			return this.allegiance;
		}
	}
	
	public Location getLocation() {
		return chessBoard.getLocationByChessPiece(this);
	}
	
	public String getFile() {
		return chessBoard.getLocationByChessPiece(this).getFile();
	}
	
	public String getRank() {
		return chessBoard.getLocationByChessPiece(this).getRank();
	}
	
	/**
	 * Returns the rank relative to whether or not the chess piece is
	 * black or white.
	 * 
	 */
	public String getRelativeRank() {
		String rank = chessBoard.getLocationByChessPiece(this).getRank();
		if (allegiance == Allegiance.BLACK) {
			int x = Integer.parseInt(rank);
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
			return rank;
		}
	}
	
	public Allegiance getAllegiance() {
		return allegiance;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public int getID() {
		return chessID;
	}
	
	public boolean isBlack() {
		return (allegiance == Allegiance.BLACK);
	}
	
	public boolean isWhite() {
		return (allegiance == Allegiance.WHITE);
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
	 * Given a game and a location, the method updates the game's board by
	 * moving the chess piece to the new position.
	 *  
	 */
	
	// implemented poorly. Chess piece should ask game object, which holds everything
	// shouldn't need to update game object and each chess piece's location object
	public void move(Location newLocation) {
		/*HashMap<Location, ChessPiece> positions = this.game.getMyPositions();
		Location currentLocation = Location.getBoardLocation(this.game, this.location);
		positions.remove(currentLocation);
		this.location = newLocation;
		Location onBoard2 = Location.getBoardLocation(this.game, newLocation);
		positions.remove(onBoard2);
		positions.put(onBoard2, this); */
		chessBoard.moveChessPiece(new Move(this, chessBoard.getLocationByChessPiece(this), newLocation));
	} 
	
	public boolean equals(ChessPiece chessPiece) {
		boolean toReturn = false;
		if (chessID == chessPiece.getID() && chessPiece.getClass().equals(this.getClass())) {
			toReturn = true;
		}
		return toReturn;
	}
	
	public String toString() {
		return "[ " + getMyType() + ", " + getLocation() + " ]";
	}
}