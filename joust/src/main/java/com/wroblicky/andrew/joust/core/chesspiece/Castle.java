package com.wroblicky.andrew.joust.core.chesspiece;

import static com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Occupier.FRIEND;
import static com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceType;

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
		this.chessID = "b1";
		this.chessBoard = new ChessBoard(new Location[8][8]);
	}
	
	public Castle(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = getMySymbol().getChessPieceAllegianceType() + chessID;
	}
	
	@Override
	public ChessPieceType getMyType() {
		return ChessPieceType.ROOK;
	}
	
	@Override
	public ChessPieceAllegianceType getMySymbol() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_ROOK;
		} else {
			return ChessPieceAllegianceType.WHITE_ROOK;
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
			if (possible != null && checkAvailability(possible) == UNOCCUPIED) {
				locations.add(possible);
			} else if (possible != null && checkAvailability(possible) == ENEMY) {
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
			if (possible != null && checkAvailability(possible) == UNOCCUPIED) {
			} else if (possible != null && checkAvailability(possible) == FRIEND) {
				locations.add(possible);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		return locations;
	}
}