package com.wroblicky.andrew.joust.game.move;

import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;

public final class Move extends GameStateChange {
	
	private ChessPiece chessPiece;
	private Location start;
	private Location destination; // if null indicates the chess piece is to be removed
	
	public Move(ChessPiece chessPiece, Location start, Location destination) {
		this.chessPiece = chessPiece;
		this.start = start;
		this.destination = destination;
	}
	
	public ChessPiece getChessPiece() {
		return chessPiece;
	}
	
	public Location getStart() {
		return start;
	}

	public Location getDestination() {
		return destination;
	}
}