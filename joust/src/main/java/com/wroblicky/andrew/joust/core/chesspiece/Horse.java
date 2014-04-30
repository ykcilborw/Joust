package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the horse chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Horse extends ChessPiece {
	
	public Horse() {
		this.allegiance = Allegiance.BLACK;
		this.alive = true;
		this.chessID = 1;
		this.chessBoard = new ChessBoard();
	}
	
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
	
	@Override
	public List<Location> getPossibleMoves(){
		List<Location> possibles = getMoveSearchSpace();
		
		for (Location location : possibles) {
			if (!isCapableOfOccupation(location)) {
				possibles.remove(location);
			}
		}
		return possibles;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		List<Location> possibles = getMoveSearchSpace();
		
		for (Location location : possibles) {
			if (!isFriend(location)) {
				possibles.remove(location);
			}
		}
		return possibles;
	}
	
	List<Location> getMoveSearchSpace() {
		List<Location> possibles = new ArrayList<Location>();
		Location initialLocation = getLocation();
		
		// get set of possibilities
		possibles.add(chessBoard.getLocation(initialLocation, 1, 2)); // Right 1, Up 2
		possibles.add(chessBoard.getLocation(initialLocation, 1, -2)); // Right 1 Down 2
		possibles.add(chessBoard.getLocation(initialLocation, -1, 2)); // Left 1 Up 2
		possibles.add(chessBoard.getLocation(initialLocation, -1, -2)); // Left 1 Down 2
		
		possibles.add(chessBoard.getLocation(initialLocation, -2, -1)); // Left 2 Down 1
		possibles.add(chessBoard.getLocation(initialLocation, -2, 1)); // Left 2 Up 1
		possibles.add(chessBoard.getLocation(initialLocation, 2, -1)); // Right 2 Down 1
		possibles.add(chessBoard.getLocation(initialLocation, 2, 1)); // Right 2 Up 1
		
		return possibles;
	}
}