package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the castle chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Castle extends ChessPiece {
	
	public Castle() {
		this.alive = true;
		this.allegiance = Allegiance.BLACK;
		this.chessID = 1;
		this.chessBoard = new ChessBoard(new Location[8][8]);
	}
	
	public Castle(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = id;
	}
	
	@Override
	public String getMyType() {
		return "Rook";
	}
	
	@Override
	public String getMySymbol() {
		if (isBlack()) {
			return "r";
		} else {
			return "R";
		}
	}
	
	//TODO add support for castling
	@Override
	public List<Location> getPossibleMoves(){
		List<Location> possibles = new ArrayList<Location>();
		possibles = getPossibleMovesHelper(possibles, 0, 1);
		possibles = getPossibleMovesHelper(possibles, 0, -1);
		possibles = getPossibleMovesHelper(possibles, 1, 0);
		possibles = getPossibleMovesHelper(possibles, -1, 0);
		return possibles;
	}
	
	private List<Location> getPossibleMovesHelper(List<Location> locations, int deltaX, int deltaY) {
		Location currentLocation = getLocation();
		int nextX = currentLocation.getXCoordinate();
		int nextY = currentLocation.getYCoordinate();
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + deltaX;
			nextY = nextY + deltaY;
			Location possible = chessBoard.getLocation(nextX, nextY);
			if (possible != null && checkAvailability(possible) == Occupier.UNOCCUPIED) {
				locations.add(possible);
			} else if (possible != null && checkAvailability(possible) == Occupier.ENEMY) {
				locations.add(possible);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		return locations;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		List<Location> possibles = new ArrayList<Location>();
		possibles = getDefenseMovesHelper(possibles, 0, 1);
		possibles = getDefenseMovesHelper(possibles, 0, -1);
		possibles = getDefenseMovesHelper(possibles, 1, 0);
		possibles = getDefenseMovesHelper(possibles, -1, 0);
		return possibles;
	}
	
	private List<Location> getDefenseMovesHelper(List<Location> locations, int deltaX, int deltaY) {
		Location currentLocation = getLocation();
		int nextX = currentLocation.getXCoordinate();
		int nextY = currentLocation.getYCoordinate();
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + deltaX;
			nextY = nextY + deltaY;
			Location possible = chessBoard.getLocation(nextX, nextY);
			if (possible != null && checkAvailability(possible) == Occupier.UNOCCUPIED) {
			} else if (possible != null && checkAvailability(possible) == Occupier.FRIEND) {
				locations.add(possible);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		return locations;
	}
}