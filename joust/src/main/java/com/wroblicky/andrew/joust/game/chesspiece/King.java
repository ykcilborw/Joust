package com.wroblicky.andrew.joust.game.chesspiece;

import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.ENEMY;
import static com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier.UNOCCUPIED;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

/**
 * Represents the king chess piece
 *
 * @author Andrew Wroblicky
 *
 */
public final class King extends ChessPiece {

	public King() {
		this.allegiance = Allegiance.BLACK;
		this.alive = true;
		this.chessBoard = new ChessBoard();
		this.chessID = "k1";
	}

	public King(Allegiance allegiance, int chessID, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = getChessPieceAllegianceType()
				.getChessPieceAllegianceType() + chessID;
	}

	@Override
	public ChessPieceType getChessPieceType() {
		return ChessPieceType.KING;
	}

	@Override
	public ChessPieceAllegianceType getChessPieceAllegianceType() {
		if (isBlack()) {
			return ChessPieceAllegianceType.BLACK_KING;
		}
		return ChessPieceAllegianceType.WHITE_KING;
	}

	@Override
	public List<Location> getPossibleMoves() {
		List<Location> possibles = getMoveSearchSpace();
		ListIterator<Location> possibilityIterator = possibles.listIterator();

		while (possibilityIterator.hasNext()) {
			Location location = possibilityIterator.next();
			if (location == null
					|| (checkAvailability(location) != UNOCCUPIED && checkAvailability(location) != ENEMY)) {
				possibilityIterator.remove();
			}
		}
		return possibles;
	}

	@Override
	public List<Location> getDefenseMoves() {
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
		possibles.add(chessBoard.getNorthNeighbor(initialLocation)); // up
		possibles.add(chessBoard.getWestNeighbor(initialLocation)); // left
		possibles.add(chessBoard.getSouthNeighbor(initialLocation)); // down
		possibles.add(chessBoard.getEastNeighbor(initialLocation)); // right
		possibles.add(chessBoard.getNorthWestNeighbor(initialLocation)); // up
																			// left
		possibles.add(chessBoard.getNorthEastNeighbor(initialLocation)); // up
																			// right
		possibles.add(chessBoard.getSouthWestNeighbor(initialLocation)); // down
																			// left
		possibles.add(chessBoard.getSouthEastNeighbor(initialLocation)); // down
																			// right

		return possibles;
	}
}