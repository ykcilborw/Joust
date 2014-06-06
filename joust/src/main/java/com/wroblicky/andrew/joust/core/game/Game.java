package com.wroblicky.andrew.joust.core.game;

import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.move.Turn;

/**
 * A simple class that stores all the internal properties of a chess game
 * 
 * @author Andrew Wroblicky
 *
 */
public class Game {
	
	private ChessBoard board;
	private List<Turn> turns;
	
	// game properties
	private boolean isInProgress = true; // by default
	private int round = 0;
	private boolean isBlackCastleMoveAllowed = true;
	private boolean isWhiteCastleMoveAllowed = true;
	private boolean check = false;
	private boolean checkmate = false;
	
	public Game(ChessBoard board, List<Turn> turns) {
		this.board = board;
		this.turns = turns;
		
	}
	
	public ChessBoard getBoard() {
		return board;
	}
	
	public void setBoard(ChessBoard board) {
		this.board = board;
	}
	
	public List<Turn> getTurns() {
		return turns;
	}
	
	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}
	
	// game properties
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
	
	public void incrementRound() {
		this.round += 1;
	}

	public boolean isWhiteCastleMoveAllowed() {
		return isWhiteCastleMoveAllowed;
	}

	public void setWhiteCastleMoveAllowed(boolean isWhiteCastleMoveAllowed) {
		this.isWhiteCastleMoveAllowed = isWhiteCastleMoveAllowed;
	}

	public boolean isBlackCastleMoveAllowed() {
		return isBlackCastleMoveAllowed;
	}

	public void setBlackCastleMoveAllowed(boolean isBlackCastleMoveAllowed) {
		this.isBlackCastleMoveAllowed = isBlackCastleMoveAllowed;
	}

	public boolean isCheckmate() {
		return checkmate;
	}

	public void setCheckmate(boolean checkmate) {
		this.checkmate = checkmate;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
}