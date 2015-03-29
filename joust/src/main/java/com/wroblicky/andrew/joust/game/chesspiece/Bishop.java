package com.wroblicky.andrew.joust.game.chesspiece;

import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.FRIEND;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

/**
 * Represents the bishop chess piece
 *
 * @author Andrew Wroblicky
 *
 */
public final class Bishop extends ChessPiece {

	public Bishop() {
		this.alive = true;
		this.allegiance = Allegiance.BLACK;
		this.chessID = "b1";
		this.chessBoard = new ChessBoard(new Location[8][8]);
	}

	public Bishop(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.alive = true;
		this.allegiance = allegiance;
		this.chessID = getChessPieceAllegianceType()
				.getChessPieceAllegianceType() + chessID;
		this.chessBoard = chessBoard;
	}

	@Override
	public ChessPieceType getChessPieceType() {
		return ChessPieceType.BISHOP;
	}

	@Override
	public ChessPieceAllegianceType getChessPieceAllegianceType() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_BISHOP;
		}
		return ChessPieceAllegianceType.WHITE_BISHOP;
	}

	@Override
	// generate all northwest directions, then northeast, then southwest, then
	// southeast
	public List<Location> getPossibleMoves() {
		List<Location> possibles = new ArrayList<Location>();
		possibles = getPossibleMovesHelper(possibles, 1, 1);
		possibles = getPossibleMovesHelper(possibles, 1, -1);
		possibles = getPossibleMovesHelper(possibles, -1, 1);
		possibles = getPossibleMovesHelper(possibles, -1, -1);
		return possibles;
	}

	private List<Location> getPossibleMovesHelper(List<Location> locations,
			int deltaX, int deltaY) {
		Location currentLocation = getLocation();
		int nextX = currentLocation.getXCoordinate();
		int nextY = currentLocation.getYCoordinate();
		boolean stillValid = true;
		while (stillValid) {
			nextX += deltaX;
			nextY += deltaY;
			Location possible = chessBoard.getLocation(nextX, nextY);
			if (possible != null && checkAvailability(possible) == UNOCCUPIED) {
				locations.add(possible);
			} else if (possible != null && checkAvailability(possible) == ENEMY) {
				locations.add(possible);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return locations;
	}

	@Override
	public List<Location> getDefenseMoves() {
		List<Location> possibles = new ArrayList<Location>();
		possibles = getDefenseMovesHelper(possibles, 1, 1);
		possibles = getDefenseMovesHelper(possibles, 1, -1);
		possibles = getDefenseMovesHelper(possibles, -1, 1);
		possibles = getDefenseMovesHelper(possibles, -1, -1);
		return possibles;
	}

	private List<Location> getDefenseMovesHelper(List<Location> locations,
			int deltaX, int deltaY) {
		Location currentLocation = getLocation();
		int nextX = currentLocation.getXCoordinate();
		int nextY = currentLocation.getYCoordinate();
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + deltaX;
			nextY = nextY + deltaY;
			Location possible = chessBoard.getLocation(nextX, nextY);
			if (possible != null && checkAvailability(possible) == UNOCCUPIED) {
			} else if (possible != null
					&& checkAvailability(possible) == FRIEND) {
				locations.add(possible);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return locations;
	}
}