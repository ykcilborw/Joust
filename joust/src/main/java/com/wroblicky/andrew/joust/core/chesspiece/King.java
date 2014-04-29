package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the king chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class King extends ChessPiece{
	
	public King() {
		this.allegiance = allegiance.BLACK;
		this.alive = true;
		this.chessBoard = new ChessBoard();
		this.chessID = 1;
	}
	
	public King(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
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
	
	@Override
	public List<Location> getPossibleMoves(){
		List<Location> possibles = new ArrayList<Location>();
		Location initialLocation = getLocation();
		
		// get set of possibilities
		possibles.add(chessBoard.getNorthNeighbor(initialLocation)); // up
		possibles.add(chessBoard.getWestNeighbor(initialLocation)); // left
		possibles.add(chessBoard.getSouthNeighbor(initialLocation));  // down
		possibles.add(chessBoard.getEastNeighbor(initialLocation));  // right
		possibles.add(chessBoard.getNorthWestNeighbor(initialLocation)); // up left  
		possibles.add(chessBoard.getNorthEastNeighbor(initialLocation));  // up right
		possibles.add(chessBoard.getSouthWestNeighbor(initialLocation));  // down left
		possibles.add(chessBoard.getSouthEastNeighbor(initialLocation));  // down right
		
		// remove locations that are not valid
		for (Location location : possibles) {
			int x = location.getXCoordinate();
			int y = location.getYCoordinate();
			if (!((checkAvailability(location).equals("unoccupied") ||
					checkAvailability(location).equals("enemy")) && chessBoard.onBoard(x, y))) {
				possibles.remove(location);
			}
		}
		return possibles;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		ArrayList<Location> possibles = new ArrayList<Location>();
		possibles.add(getLocation().move(1, 0, 0, 0));
		possibles.add(getLocation().move(0, 1, 0, 0));
		possibles.add(getLocation().move(0, 0, 1, 0));
		possibles.add(getLocation().move(0, 0, 0, 1));
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
}