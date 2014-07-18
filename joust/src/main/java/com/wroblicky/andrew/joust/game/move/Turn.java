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
public final class Turn {
	
	private List<GameStateChange> gameStateChanges;
	private ChessPiece captured;
	private ChessPiece capturer;
	private boolean check = false;
	private boolean checkmate = false;
	
	public Turn() {
		this.gameStateChanges = new ArrayList<GameStateChange>();
	}

	public Turn(GameStateChange gameStateChange) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(gameStateChange);
	}
	
	public Turn(Move move, ChessPiece captured, ChessPiece capturer) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(move);
		this.captured = captured;
		this.capturer = capturer;
	}
	
	public Turn(Move move, boolean check, boolean checkMate) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(move);
		this.check = check;
		this.checkmate = checkMate;
	}
	
	public Turn(Move move, ChessPiece captured, ChessPiece capturer, boolean check, boolean checkMate) {
		this.gameStateChanges = new ArrayList<GameStateChange>();
		this.gameStateChanges.add(move);
		this.captured = captured;
		this.capturer = capturer;
		this.check = check;
		this.checkmate = checkMate;
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
	
	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}
}