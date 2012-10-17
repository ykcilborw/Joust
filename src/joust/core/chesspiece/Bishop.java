package joust.core.chesspiece;

import joust.core.general.*;
import java.util.ArrayList;
import java.util.HashMap;


public class Bishop extends ChessPiece {
	private Location myLocation;
	private String myColor;
	private boolean isAlive;
	int myID;
	
	public Bishop(Location l, String color, int id) {
		myLocation = l;
		myColor = color;
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
		return "Bishop";
	}
	
	public String getmySymbol() {
		if (myColor.equals("b")) {
			return "b";
		} else {
			return "B";
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
	
	public void nowDead() {
		isAlive = false;
	}
	
	public ArrayList<Location> getPossibleMoves(Game g){
		// generate all northwest directions, then northeast, then southwest, then southeast
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = myLocation.getmyX();
		int y = myLocation.getmyY();
		//System.out.println("bishop initial loc: " + Location.convert(x, y));
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			//System.out.println("bishop poss loc: " + Location.convert(nextX, nextY));
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			//System.out.println("result: " + result);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((result.equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((result.equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY + 1;
			//System.out.println("bishop poss loc: " + Location.convert(nextX, nextY));
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			//System.out.println("result: " + result);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((result.equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((result.equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	public ArrayList<Location> getDefenseMoves(Game g){
		// generate all northwest directions, then northeast, then southwest, then southeast
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = myLocation.getmyX();
		int y = myLocation.getmyY();
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(g, l);
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		//System.out.println("bishop canReach size: " + possibles.size());
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			Location temp = possibles.get(i);
			//System.out.println("bishop canReach move: " + temp);
			if (temp.equals(l)) {
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
