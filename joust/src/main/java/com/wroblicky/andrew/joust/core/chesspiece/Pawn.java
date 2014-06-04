package com.wroblicky.andrew.joust.core.chesspiece;

import static com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceType;

/**
 * Represents the pawn chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Pawn extends ChessPiece {
	
	private boolean hasMovedAtAll;
	private boolean movedTwice;
	
	public Pawn() {
		this.allegiance = Allegiance.BLACK;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = 1;
		this.chessBoard = new ChessBoard();
	}
	
	public Pawn(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = id;
		this.chessBoard = chessBoard;
	}
	
	public boolean isMovedAtAll() {
		return hasMovedAtAll;
	}
	
	public boolean isMovedTwice() {
		return movedTwice;
	}
	
	@Override
	public ChessPieceType getMyType() {
		return ChessPieceType.PAWN;
	}
	
	@Override
	public ChessPieceAllegianceType getMySymbol() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_PAWN;
		} else {
			return ChessPieceAllegianceType.WHITE_PAWN;
		}
	}
	
	public void setMovedYet(boolean hasMovedAtAll) {
		this.hasMovedAtAll = hasMovedAtAll;
	}
	
	public void setMovedTwice(boolean movedTwice) {
		this.movedTwice = movedTwice;
	}
	
	
	//TODO
	// add support for becoming a queen 
	@Override
	public List<Location> getPossibleMoves() {
		List<Location> possibles = new ArrayList<Location>();
		Location currentLocation = getLocation();
		Location moveUpLeft = null;
		Location moveUpRight = null;
		Location left = chessBoard.getWestNeighbor(currentLocation);
		Location right = chessBoard.getEastNeighbor(currentLocation);
		
		if(isWhite()) {
			moveUpLeft = chessBoard.getNorthWestNeighbor(currentLocation);
			moveUpRight = chessBoard.getNorthEastNeighbor(currentLocation);
		} else {
			moveUpLeft = chessBoard.getSouthWestNeighbor(currentLocation);
			moveUpRight = chessBoard.getSouthEastNeighbor(currentLocation);
		}
		
		// forward moves
		possibles = getForwardMoves(possibles);
		
		// attack moves
		if (moveUpLeft != null && checkAvailability(moveUpLeft) == ENEMY) {
			possibles.add(moveUpLeft);
		}
		if (moveUpRight != null && checkAvailability(moveUpRight) == ENEMY) {
			possibles.add(moveUpRight);
		}
		
		// en passant
		if (left != null && checkAvailability(left) == ENEMY && checkAvailability(moveUpLeft) == UNOCCUPIED) {
			ChessPiece enemy = chessBoard.getChessPieceByLocation(left);
			if (enemy instanceof Pawn) {
				Pawn enemyPawn = (Pawn) enemy;
				if (enemyPawn.isMovedTwice() && enemyPawn.getRelativeRank().equals("4")) {
					possibles.add(moveUpLeft);
				}
			}
		}
		
		if (right != null && checkAvailability(right) == ENEMY && checkAvailability(moveUpRight) == UNOCCUPIED) {
			ChessPiece enemy = chessBoard.getChessPieceByLocation(right);
			if (enemy instanceof Pawn) {
				Pawn enemyPawn = (Pawn) enemy;
				if (enemyPawn.isMovedTwice() && enemyPawn.getRelativeRank().equals("4")) {
					possibles.add(moveUpRight);
				}
			}
		}
		
		return possibles;
	}
	
	/**
	 * Retrieves upward possible pawn moves
	 */
	private List<Location> getForwardMoves(List<Location> locations) {
		Location moveUpOnce = null;
		Location moveUpTwice = null;
		if(isWhite()) {
			moveUpOnce = chessBoard.getNorthNeighbor(getLocation());
			moveUpTwice = chessBoard.getLocation(getLocation(), 0, 2);
		} else {
			moveUpOnce = chessBoard.getSouthNeighbor(getLocation());
			moveUpTwice = chessBoard.getLocation(getLocation(), 0, -2);
		}
		
		if (moveUpOnce != null && checkAvailability(moveUpOnce) == UNOCCUPIED) {
			locations.add(moveUpOnce);
			if (!hasMovedAtAll && moveUpTwice != null && checkAvailability(moveUpTwice).equals("UNOCCUPIED") ) {
				locations.add(moveUpTwice);
			}
		}
		
		return locations;
	}
	
	@Override
	public List<Location> getDefenseMoves() {
		List<Location> possibles = new ArrayList<Location>();
		Location currentLocation = getLocation();
		Location moveUpLeft = null;
		Location moveUpRight = null;
		
		if(isWhite()) {
			moveUpLeft = chessBoard.getNorthWestNeighbor(currentLocation);
			moveUpRight = chessBoard.getNorthEastNeighbor(currentLocation);
		} else {
			moveUpLeft = chessBoard.getSouthWestNeighbor(currentLocation);
			moveUpRight = chessBoard.getSouthEastNeighbor(currentLocation);
		}
		
		if (isFriend(moveUpLeft)) {
			possibles.add(moveUpLeft);
		}
		
		if (isFriend(moveUpRight)) {
			possibles.add(moveUpRight);
		}
		
		return possibles;
	}
}