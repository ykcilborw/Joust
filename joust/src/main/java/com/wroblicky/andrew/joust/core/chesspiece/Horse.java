package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceType;

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
		this.chessID = "n1";
		this.chessBoard = new ChessBoard();
	}
	
	public Horse(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessID = getMySymbol().getChessPieceAllegianceType() + chessID;
		this.chessBoard = chessBoard;
	}
	
	@Override
	public ChessPieceType getMyType() {
		return ChessPieceType.KNIGHT;
	}
	
	@Override
	public ChessPieceAllegianceType getMySymbol() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_KNIGHT;
		} else {
			return ChessPieceAllegianceType.WHITE_KNIGHT;
		}
	}
	
	@Override
	public List<Location> getPossibleMoves(){
		List<Location> possibles = getMoveSearchSpace();
		ListIterator<Location> possibilityIterator = possibles.listIterator();
		
		while (possibilityIterator.hasNext()) {
			Location location = possibilityIterator.next();
			if (!isCapableOfOccupation(location)) {
				possibilityIterator.remove();
			}
		}
		return possibles;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		List<Location> possibles = getMoveSearchSpace();
		ListIterator<Location> possibilityIterator = possibles.listIterator();
		
		while (possibilityIterator.hasNext()) {
			Location location = possibilityIterator.next();
			if (!isFriend(location)) {
				possibilityIterator.remove();
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