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
 * Represents the queen chess piece
 *
 * @author Andrew Wroblicky
 *
 */
public final class Queen extends ChessPiece {

	public Queen() {
		this.allegiance = Allegiance.BLACK;
		this.alive = true;
		this.chessBoard = new ChessBoard();
		this.chessID = "q1";
	}

	public Queen(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = getChessPieceAllegianceType()
				.getChessPieceAllegianceType() + chessID;
	}

	@Override
	public ChessPieceType getChessPieceType() {
		return ChessPieceType.QUEEN;
	}

	@Override
	public ChessPieceAllegianceType getChessPieceAllegianceType() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_QUEEN;
		}
		return ChessPieceAllegianceType.WHITE_QUEEN;
	}

	@Override
	public List<Location> getPossibleMoves() {
		List<Location> possibles = new ArrayList<Location>();
		possibles = getPossibleMovesHelper(possibles, 1, 1);
		possibles = getPossibleMovesHelper(possibles, 1, -1);
		possibles = getPossibleMovesHelper(possibles, -1, 1);
		possibles = getPossibleMovesHelper(possibles, -1, -1);
		possibles = getPossibleMovesHelper(possibles, 1, 0);
		possibles = getPossibleMovesHelper(possibles, -1, 0);
		possibles = getPossibleMovesHelper(possibles, 0, 1);
		possibles = getPossibleMovesHelper(possibles, 0, -1);
		return possibles;
	}

	private List<Location> getPossibleMovesHelper(List<Location> locations,
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
		possibles = getDefenseMovesHelper(possibles, 1, 0);
		possibles = getDefenseMovesHelper(possibles, -1, 0);
		possibles = getDefenseMovesHelper(possibles, 0, 1);
		possibles = getDefenseMovesHelper(possibles, 0, -1);
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