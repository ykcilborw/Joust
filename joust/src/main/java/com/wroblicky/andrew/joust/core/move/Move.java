package com.wroblicky.andrew.joust.core.move;

import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

public class Move {
	
	private ChessPiece chessPiece;
	private Location start;
	private Location destination;
	
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