package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Game;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the queen chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Queen extends ChessPiece {
	
	public Queen(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = id;
	}
	
	@Override
	public String getMyType() {
		return "Queen";
	}
	
	@Override
	public String getMySymbol() {
		if (isBlack()) {
			return "q";
		} else {
			return "Q";
		}
	}
	
	@Override
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = getLocation().getXCoordinate();
		int y = getLocation().getYCoordinate();
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextX = nextX + 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextY = y;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextY = y;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextX = x;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextX = x;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	@Override
	public ArrayList<Location> getDefenseMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = getLocation().getXCoordinate();
		int y = getLocation().getYCoordinate();
		//System.out.println("queen defense x: " + x);
		//System.out.println("queen defense y: " + y);
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			//System.out.println("queen defense available: " + checkAvailability(l));
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
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
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
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
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
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
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
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
			nextY = y;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextY = y;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextX = x;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			nextX = x;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	@Override
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			//System.out.println("getLocation(): " + possibles.get(i).getXCoordinate() + ", " + possibles.get(i).getYCoordinate());
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	@Override
	public boolean canDefend(Game g, Location l) {
		ArrayList<Location> possibles = this.getDefenseMoves(g);
		//System.out.println("can defend size queen: " + possibles.size());
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
}