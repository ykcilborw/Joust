package com.wroblicky.andrew.joust.game.move;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;

/**
 * Represents a move or sequence of chess piece gameStateChanges that a player
 * can make during his or her turn.
 * 
 * @author Andrew
 *
 */
public class Turn {
	
	private List<GameStateChange> gameStateChanges;
	private ChessPiece captured;
	private ChessPiece capturer;

	
	public Turn(Move move) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(move);
	}
	
	public Turn(Move move, ChessPiece captured, ChessPiece capturer) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(move);
		this.captured = captured;
		this.capturer = capturer;
	}
	
	public List<GameStateChange> getGameStateChanges() {
		return gameStateChanges;
	}
	
	public void addMove(Move move) {
		this.gameStateChanges.add(move);
	}
	
	public ChessPiece getCaptured() {
		return captured;
	}

	public void setCaptured(ChessPiece captured) {
		this.captured = captured;
	}
	
	public ChessPiece getCapturer() {
		return capturer;
	}

	public void setCapturer(ChessPiece capturer) {
		this.capturer = capturer;
	}
}