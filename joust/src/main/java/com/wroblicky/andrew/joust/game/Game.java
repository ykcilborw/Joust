package com.wroblicky.andrew.joust.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.game.subset.ChessPieceSubsetManager;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Qualifiable;

/**
 * A class that stores all the internal properties of a chess game
 * 
 * @author Andrew Wroblicky
 *
 */
public final class Game {
	
	private ChessBoard board;
	private List<Turn> turns = new ArrayList<Turn>();
	private ChessPieceSubsetManager chessPieceSubsetManager;
	
	// game properties
	private boolean isInProgress = true; // by default
	private int round = 0;
	private boolean isBlackCastleMoveAllowed = true;
	private boolean isWhiteCastleMoveAllowed = true;
	private boolean check = false;
	private boolean checkmate = false;
	
	public Game(ChessBoard board, List<Turn> turns, 
			ChessPieceSubsetManager chessPieceSubsetManager) {
		this.board = board;
		this.turns = turns;
		this.chessPieceSubsetManager = chessPieceSubsetManager;
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
	
	public void addTurn(Turn turn) {
		this.turns.add(turn);
	}
	
	public void removeTurn() {
		this.turns.remove(turns.size() - 1);
	}
	
	public Set<ChessPiece> getChessPieces(Qualifiable qualification) {
		return chessPieceSubsetManager.getChessPieces(qualification);
	}
	
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		board.addChessPiece(chessPiece, location);
		chessPieceSubsetManager.addChessPiece(chessPiece);
	}

	public void removeChessPiece(ChessPiece chessPiece) {
		board.removeChessPiece(chessPiece);
		chessPieceSubsetManager.removeChessPiece(chessPiece);
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
	
	public void decrementRound() {
		if (this.round > 0) {
			this.round -= 1;
		}
	}
	
	public Turn getCurrentTurn() {
		if (!turns.isEmpty()) {
			return turns.get(round - 1);
		} else {
			return new Turn();
		}
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