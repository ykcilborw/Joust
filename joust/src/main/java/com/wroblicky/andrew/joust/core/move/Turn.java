package com.wroblicky.andrew.joust.core.move;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

/**
 * Represents a move or sequence of chess piece moves that a player
 * can make during his or her turn.
 * 
 * @author Andrew
 *
 */
public class Turn {
	
	private List<Move> moves;
	private ChessPiece captured;
	private ChessPiece capturer;

	
	public Turn(Move move) {
		this.moves = new ArrayList<Move>();
		this.moves.add(move);
	}
	
	public Turn(Move move, ChessPiece captured, ChessPiece capturer) {
		this.moves = new ArrayList<Move>();
		this.moves.add(move);
		this.captured = captured;
		this.capturer = capturer;
	}
	
	public List<Move> getMoves() {
		return moves;
	}
	
	public void addMove(Move move) {
		this.moves.add(move);
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