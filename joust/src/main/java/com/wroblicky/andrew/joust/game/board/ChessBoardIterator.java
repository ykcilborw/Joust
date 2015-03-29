package com.wroblicky.andrew.joust.game.board;

/**
 * Utility class for iterating over chess board squares
 *
 * @author Andrew Wroblicky
 *
 */
public final class ChessBoardIterator {

	private final Location[][] chessBoard;
	private int currentX;
	private int currentY;

	public ChessBoardIterator(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
		this.currentX = 0;
		this.currentY = 0;
	}

	public boolean hasNext() {
		if (currentX < 8 && currentY < 8) {
			return true;
		}
		return false;
	}

	public Location next() {
		Location toReturn = chessBoard[currentX][currentY];
		if (currentX < 7) {
			currentX += 1;
		} else {
			currentX = 0;
			currentY += 1;
		}
		return toReturn;
	}
}