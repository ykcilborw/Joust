package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the bishop chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Bishop extends ChessPiece {
	
	public Bishop() {
		this.alive = true;
		this.allegiance = Allegiance.BLACK;
		this.chessID = 1;
		this.chessBoard = new ChessBoard(new Location[8][8]);
	}
	
	public Bishop(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.alive = true;
		this.allegiance = allegiance;
		this.chessID = chessID;
		this.chessBoard = chessBoard;
	}
	
	@Override
	public String getMyType() {
		return "Bishop";
	}
	
	@Override
	public String getMySymbol() {
		if (isBlack()) {
			return "b";
		} else {
			return "B";
		}
	}
	
	@Override
	// generate all northwest directions, then northeast, then southwest, then southeast
	public List<Location> getPossibleMoves(){
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
			String result = checkAvailability(l);
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
			String result = checkAvailability(l);
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
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
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
			String result = checkAvailability(l);
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
	public List<Location> getDefenseMoves(){
		// generate all northwest directions, then northeast, then southwest, then southeast
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
			String result = checkAvailability(l);
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
			String result = checkAvailability(l);
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
			String result = checkAvailability(l);
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
			String result = checkAvailability(l);
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
	public boolean canReach(Location l) {
		List<Location> possibles = getPossibleMoves();
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			Location temp = possibles.get(i);
			if (temp.equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	@Override
	public boolean canDefend(Location l) {
		List<Location> possibles = getDefenseMoves();
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
}
