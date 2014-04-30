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
		this.allegiance = Allegiance.BLACK;
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
		List<Location> possibles = getMoveSearchSpace();
		
		// remove locations that are not valid
		for (Location location : possibles) {
			if (!(isCapableOfOccupation(location))) {
				possibles.remove(location);
			}
		}
		return possibles;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		List<Location> possibles = getMoveSearchSpace();
		
		// remove locations that are not valid
		for (Location location : possibles) {
			if (!(isFriend(location))) {
				possibles.remove(location);
			}
		}
		
		return possibles;
	}
	
	List<Location> getMoveSearchSpace() {
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
		
		return possibles;
	}
}