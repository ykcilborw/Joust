package joust.core.chesspiece;

import java.util.ArrayList;

import joust.core.general.Game;
import joust.core.general.Location;

public class King extends ChessPiece{
	
	
	public King(Location l, Allegiance allegiance, int id) {
		this.location = l;
		this.allegiance = allegiance;
		this.alive = true;
		this.chessID = id;
	}
	
	public String getMyType() {
		return "King";
	}
	
	public String getMySymbol() {
		if (isBlack()) {
			return "k";
		} else {
			return "K";
		}
	}
	
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(location.move(1, 0, 0, 0)); // up
		possibles.add(location.move(0, 1, 0, 0)); // left
		possibles.add(location.move(0, 0, 1, 0));  // down
		possibles.add(location.move(0, 0, 0, 1));  // right
		possibles.add(location.move(1, 1, 0, 0)); // up left  
		possibles.add(location.move(1, 0, 0, 1));  // up right
		possibles.add(location.move(0, 1, 1, 0));  // down left
		possibles.add(location.move(0, 0, 1, 1));  // down right
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			if ((checkAvailability(g, l).equals("unoccupied") || checkAvailability(g, l).equals("enemy")) && x < 9 && x > 0 && y < 9 && y > 0) {
				possibles2.add(l);
			}
		}
		
		return possibles2;
	}
	
	public ArrayList<Location> getDefenseMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(location.move(1, 0, 0, 0));
		possibles.add(location.move(0, 1, 0, 0));
		possibles.add(location.move(0, 0, 1, 0));
		possibles.add(location.move(0, 0, 0, 1));
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			if (checkAvailability(g, l).equals("friend") && x < 9 && x > 0 && y < 9 && y > 0) {
				possibles2.add(l);
			}
		}
		
		return possibles2;
	}
	
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			Location temp = possibles.get(i);
			System.out.println("king canReach move: " + temp);
			if (temp.equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
		/*
		int x = l.getXCoordinate();
		int y = l.getYCoordinate();
		int x2 = location.getXCoordinate();
		int y2 = location.getYCoordinate();
		boolean toReturn = false;
		if ((x - x2 == 1 && y - y2 == 0) || (x - x2 == -1 && y - y2 == 0) || (x - x2 == 0 && y - y2 == 1) || (x - x2 == 0 && y - y2 == -1)) {
			toReturn = true;
		}
		return toReturn;
		*/
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
