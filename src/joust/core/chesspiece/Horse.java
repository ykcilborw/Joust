package joust.core.chesspiece;

import java.util.ArrayList;

import joust.core.board.ChessBoard;
import joust.core.general.Game;
import joust.core.general.Location;


public class Horse extends ChessPiece {
	
	
	public Horse(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessID = chessID;
		this.chessBoard = chessBoard;
	}
	
	public String getMyType() {
		return "Horse";
	}
	
	public String getMySymbol() {
		if (isBlack()) {
			return "n";
		} else {
			return "N";
		}
	}
	
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();	
		possibles.add(getLocation().move(2, 1, 0, 0));		//Up: 2, Left: 1
		possibles.add(getLocation().move(2, 0, 0, 1));		//Up: 2, Right: 1
		possibles.add(getLocation().move(1, 2, 0, 0));		//Up: 1, Left: 2
		possibles.add(getLocation().move(1, 0, 0, 2));		//Up: 1, Right: 2
		
		possibles.add(getLocation().move(0, 1, 2, 0));		//Down: 2, Left: 1
		possibles.add(getLocation().move(0, 0, 2, 1));		//Down: 2, Right: 1
		possibles.add(getLocation().move(0, 2, 1, 0));		//Down: 1, Left: 2
		possibles.add(getLocation().move(0, 0, 1, 2));		//Down: 1, Right: 2
		
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			if ((checkAvailability(l).equals("unoccupied") || checkAvailability(l).equals("enemy")) && x < 9 && x > 0 && y < 9 && y > 0) {
				possibles2.add(l);
			}
		}
		return possibles2;
	}
	
	
	public ArrayList<Location> getDefenseMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(getLocation().move(2, 1, 0, 0));
		possibles.add(getLocation().move(2, 0, 0, 1));
		possibles.add(getLocation().move(0, 1, 2, 0));
		possibles.add(getLocation().move(0, 0, 2, 1));
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			if (checkAvailability(l).equals("friend") && x < 9 && x > 0 && y < 9 && y > 0) {
				possibles2.add(l);
			}
		}
		return possibles2;
	}
	
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	public boolean canDefend(Game g, Location l) {
		ArrayList<Location> possibles = this.getDefenseMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
}