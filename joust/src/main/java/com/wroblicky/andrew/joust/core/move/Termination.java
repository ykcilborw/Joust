package com.wroblicky.andrew.joust.core.move;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;

public class Termination extends GameStateChange {
	
	private Allegiance winner;
	
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