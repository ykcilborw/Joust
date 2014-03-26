package joust.core.chesspiece;

import java.util.ArrayList;

import joust.core.general.Game;
import joust.core.general.Location;

/**
 * Represents the Bishop chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Bishop extends ChessPiece {
	
	
	public Bishop(Location l, String color, int id) {
		myLocation = l;
		myColor = color;
		isAlive = true;
		myID = id;
	}
	
	@Override
	public String getMyType() {
		return "Bishop";
	}
	
	@Override
	public String getMySymbol() {
		if (myColor.equals("b")) {
			return "b";
		} else {
			return "B";
		}
	}
	
	@Override
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
	
	@Override
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
	
	@Override
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
	
	@Override
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
