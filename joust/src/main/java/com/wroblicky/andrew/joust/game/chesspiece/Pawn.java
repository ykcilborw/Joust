package com.wroblicky.andrew.joust.game.chesspiece;

import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

/**
 * Represents the pawn chess piece
 *
 * @author Andrew Wroblicky
 *
 */
public final class Pawn extends ChessPiece {

	private boolean hasMovedAtAll;
	private boolean movedTwice;

	public Pawn() {
		this.allegiance = Allegiance.BLACK;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = "p1";
		this.chessBoard = new ChessBoard();
	}

	public Pawn(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = getChessPieceAllegianceType()
				.getChessPieceAllegianceType() + chessID;
		this.chessBoard = chessBoard;
	}

	public boolean isMovedAtAll() {
		return hasMovedAtAll;
	}

	public boolean isMovedTwice() {
		return movedTwice;
	}

	@Override
	public ChessPieceType getChessPieceType() {
		return ChessPieceType.PAWN;
	}

	@Override
	public ChessPieceAllegianceType getChessPieceAllegianceType() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_PAWN;
		}
		return ChessPieceAllegianceType.WHITE_PAWN;
	}

	public void setMovedAtAll(boolean hasMovedAtAll) {
		this.hasMovedAtAll = hasMovedAtAll;
	}

	public void setMovedTwice(boolean movedTwice) {
		this.movedTwice = movedTwice;
	}

	// TODO
	// add support for pawn promotion
	@Override
	public List<Location> getPossibleMoves() {
		List<Location> possibles = new ArrayList<Location>();
		Location moveUpLeft = getWestAttackNeighbor();
		Location moveUpRight = getEastAttackNeighbor();
		Location left = chessBoard.getWestNeighbor(getLocation());
		Location right = chessBoard.getEastNeighbor(getLocation());

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
		if (left != null && checkAvailability(left) == ENEMY
				&& checkAvailability(moveUpLeft) == UNOCCUPIED) {
			ChessPiece enemy = chessBoard.getChessPieceByLocation(left);
			if (enemy instanceof Pawn) {
				Pawn enemyPawn = (Pawn) enemy;
				if (enemyPawn.isMovedTwice()
						&& enemyPawn.getRelativeRank().equals("4")) {
					possibles.add(moveUpLeft);
				}
			}
		}

		if (right != null && checkAvailability(right) == ENEMY
				&& checkAvailability(moveUpRight) == UNOCCUPIED) {
			ChessPiece enemy = chessBoard.getChessPieceByLocation(right);
			if (enemy instanceof Pawn) {
				Pawn enemyPawn = (Pawn) enemy;
				if (enemyPawn.isMovedTwice()
						&& enemyPawn.getRelativeRank().equals("4")) {
					possibles.add(moveUpRight);
				}
			}
		}

		return possibles;
	}

	private Location getEastAttackNeighbor() {
		Location currentLocation = getLocation();
		return isWhite() ? chessBoard.getNorthEastNeighbor(currentLocation)
				: chessBoard.getSouthEastNeighbor(currentLocation);
	}

	private Location getWestAttackNeighbor() {
		Location currentLocation = getLocation();
		return isWhite() ? chessBoard.getNorthWestNeighbor(currentLocation)
				: chessBoard.getSouthWestNeighbor(currentLocation);
	}

	/**
	 * Retrieves upward possible pawn moves
	 */
	private List<Location> getForwardMoves(List<Location> locations) {
		Location moveUpOnce = null;
		Location moveUpTwice = null;
		if (isWhite()) {
			moveUpOnce = chessBoard.getNorthNeighbor(getLocation());
			moveUpTwice = chessBoard.getLocation(getLocation(), 0, 2);
		} else {
			moveUpOnce = chessBoard.getSouthNeighbor(getLocation());
			moveUpTwice = chessBoard.getLocation(getLocation(), 0, -2);
		}

		if (moveUpOnce != null && checkAvailability(moveUpOnce) == UNOCCUPIED) {
			locations.add(moveUpOnce);
			if (!hasMovedAtAll && moveUpTwice != null
					&& checkAvailability(moveUpTwice) == UNOCCUPIED) {
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

		if (isWhite()) {
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