package joust.core.chesspiece;

import joust.core.general.*;
import java.util.ArrayList;
import java.util.HashMap;

public class King extends ChessPiece{
	
	
	public King(Location l, String color, int id) {
		this.myLocation = l;
		this.myColor = color;
		this.isAlive = true;
		this.chessID = id;
	}
	
	public String getMyType() {
		return "King";
	}
	
	public String getMySymbol() {
		if (myColor.equals("b")) {
			return "k";
		} else {
			return "K";
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
	
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(myLocation.move(1, 0, 0, 0)); // up
		possibles.add(myLocation.move(0, 1, 0, 0)); // left
		possibles.add(myLocation.move(0, 0, 1, 0));  // down
		possibles.add(myLocation.move(0, 0, 0, 1));  // right
		possibles.add(myLocation.move(1, 1, 0, 0)); // up left  
		possibles.add(myLocation.move(1, 0, 0, 1));  // up right
		possibles.add(myLocation.move(0, 1, 1, 0));  // down left
		possibles.add(myLocation.move(0, 0, 1, 1));  // down right
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
		possibles.add(myLocation.move(1, 0, 0, 0));
		possibles.add(myLocation.move(0, 1, 0, 0));
		possibles.add(myLocation.move(0, 0, 1, 0));
		possibles.add(myLocation.move(0, 0, 0, 1));
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
			Location temp = possibles.get(i);
			System.out.println("king canReach move: " + temp);
			if (temp.equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
		/*
		int x = l.getmyX();
		int y = l.getmyY();
		int x2 = myLocation.getmyX();
		int y2 = myLocation.getmyY();
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
		if (chessID == b.getID() && b.getMyType().equals(this.getMyType())) {
			toReturn = true;
		}
		return toReturn;
	}

}
