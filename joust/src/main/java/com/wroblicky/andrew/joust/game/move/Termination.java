package com.wroblicky.andrew.joust.game.move;

import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;

public final class Termination extends GameStateChange {
	
	private Allegiance winner;
	
	public Termination() {
		
	}
	
	public Termination (Allegiance winner) {
		this.winner = winner;
	}
	
	public boolean isBlackWinner() {
		return winner == Allegiance.BLACK;
	}
	
	public boolean isWhiteWinner() {
		return winner == Allegiance.WHITE;
	}
	
	public boolean isTie() {
		return winner == null;
	}
}