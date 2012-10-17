package joust.core.chesspiece;

import joust.core.general.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Horse extends ChessPiece {
	private Location myLocation;
	private String myColor;
	private boolean isAlive;
	private int myID;
	
	public Horse(Location l, String c, int id) {
		myLocation = l;
		myColor = c;
		isAlive = true;
		myID = id;
	}
	
	public Location getLocation() {
		return myLocation;
	}
	
	public String getColor() {
		return myColor;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public int getID() {
		return myID;
	}
	
	public String getmyType() {
		return "Horse";
	}
	
	public String getmySymbol() {
		if (myColor.equals("b")) {
			return "n";
		} else {
			return "N";
		}
	}
	
	public String getFile() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(0, 1);
	}
	
	public String getRank() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(1, 2);
	}
	
	public String getRelRank() {
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
	
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();	
		possibles.add(myLocation.move(2, 1, 0, 0));		//Up: 2, Left: 1
		possibles.add(myLocation.move(2, 0, 0, 1));		//Up: 2, Right: 1
		possibles.add(myLocation.move(1, 2, 0, 0));		//Up: 1, Left: 2
		possibles.add(myLocation.move(1, 0, 0, 2));		//Up: 1, Right: 2
		
		possibles.add(myLocation.move(0, 1, 2, 0));		//Down: 2, Left: 1
		possibles.add(myLocation.move(0, 0, 2, 1));		//Down: 2, Right: 1
		possibles.add(myLocation.move(0, 2, 1, 0));		//Down: 1, Left: 2
		possibles.add(myLocation.move(0, 0, 1, 2));		//Down: 1, Right: 2
		
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getmyX();
			int y = l.getmyY();
			if ((checkAvailability(g, l).equals("unoccupied") || checkAvailability(g, l).equals("enemy")) && x < 9 && x > 0 && y < 9 && y > 0) {
				possibles2.add(l);
			}
		}
		return possibles2;
	}
	
	
	public ArrayList<Location> getDefenseMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(myLocation.move(2, 1, 0, 0));
		possibles.add(myLocation.move(2, 0, 0, 1));
		possibles.add(myLocation.move(0, 1, 2, 0));
		possibles.add(myLocation.move(0, 0, 2, 1));
		ArrayList<Location> possibles2 = new ArrayList<Location>();
		for (int i = 0; i < possibles.size(); i++) {
			Location l = possibles.get(i);
			int x = l.getmyX();
			int y = l.getmyY();
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
	
	
	public void move(Game g, Location l) {
		HashMap<Location, ChessPiece> positions = g.getmyPositions();
		Location onBoard = Location.getBoardLocation(g, myLocation);
		positions.remove(onBoard);
		//positions.put(onBoard, null);
		myLocation = l;
		Location onBoard2 = Location.getBoardLocation(g, l);
		positions.remove(onBoard2);
		positions.put(onBoard2, this);
	}
	
	public boolean equals(ChessPiece b) {
		boolean toReturn = false;
		if (myID == b.getID() && b.getmyType().equals(this.getmyType())) {
			toReturn = true;
		}
		return toReturn;
	}

}
