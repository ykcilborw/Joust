package com.wroblicky.andrew.joust.core.general;

import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;

public class Game {
	
	private boolean isInProgress;
	private int round;
	private List<Turn> turns;
	private ChessBoard board;
	private ChessPieceSubsetManager chessPieceSubsetManager;
	
	public boolean isInProgress() {
		return isInProgress;
	}
	
	public void setInProgress(boolean isInProgress) {
		this.isInProgress = isInProgress;
	}
	
	public int getRound() {
		return round;
	}
	
	public void setRound(int round) {
		this.round = round;
	}
	
	public List<Turn> getTurns() {
		return turns;
	}
	
	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}
	
	public ChessBoard getBoard() {
		return board;
	}
	
	public void setBoard(ChessBoard board) {
		this.board = board;
	}
	
	public ChessPieceSubsetManager getChessPieceSubsetManager() {
		return chessPieceSubsetManager;
	}
	
	public void setChessPieceSubsetManager(
			ChessPieceSubsetManager chessPieceSubsetManager) {
		this.chessPieceSubsetManager = chessPieceSubsetManager;
	}
}