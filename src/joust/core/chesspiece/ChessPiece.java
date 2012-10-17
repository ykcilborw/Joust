package joust.core.chesspiece;

import joust.core.general.Location;
import joust.core.general.Game;
import java.util.*;


public abstract class ChessPiece {
	private Location myLocation;
	private String myColor;
	
	public abstract Location getLocation();
	
	public abstract String getColor();
	
	public abstract boolean isAlive();
	
	public abstract int getID();
	
	public abstract String getmyType();
	
	public abstract String getmySymbol(); // for printing
	
	public abstract String getFile();
	
	public abstract String getRank();
	
	public abstract String getRelRank();
	
	public abstract ArrayList<Location> getPossibleMoves(Game g);

	public abstract ArrayList<Location> getDefenseMoves(Game g);
	
	public abstract boolean canReach(Game g, Location l);
	
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
	
	public abstract void move(Game g, Location l); 
	
	public String toString() {
		return getmySymbol();
	}
}
