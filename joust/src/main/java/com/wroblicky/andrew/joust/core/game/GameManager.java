package com.wroblicky.andrew.joust.core.game;

import java.util.HashSet;
import java.util.Set;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.ChessPieceLookup;
import com.wroblicky.andrew.joust.core.general.ChessPieceSubsetManager;
import com.wroblicky.andrew.joust.core.general.Util;
import com.wroblicky.andrew.joust.core.qualifiable.Qualifiable;
import com.wroblicky.andrew.joust.core.qualifiable.Scope;

/**
 * Simple wrapper around the Game class for mutation operations
 * 
 * 
 * @author Andrew Wroblicky
 *
 */
public class GameManager {
	private Game game;
	private ChessPiece capturer;
	private ChessPiece captured;
	private ChessPieceLookup chessPieceLookup;

	public GameManager(Game game) {
		this.game = game;
		chessPieceLookup = new ChessPieceLookup(new ChessPieceSubsetManager(new HashSet<ChessPiece>()));
	}
	
	public int getRound() {
		return game.getRound();
	}
		
	public Set<ChessPiece> getActivePieces() {
		return game.getChessPieces(Scope.ACTIVE);
	}
	
	public Set<ChessPiece> getBlackPieces() {
		return game.getChessPieces(Scope.BLACK_ACTIVE);
	}
	
	public Set<ChessPiece> getWhitePieces() {
		return game.getChessPieces(Scope.WHITE_ACTIVE);
	}
	
	public Set<ChessPiece> getChessPieces(Qualifiable qualification) {
		return game.getChessPieces(qualification);
	}
	
	public ChessPieceLookup getChessPieceLookup() {
		return chessPieceLookup;
	}
	
	public ChessBoard getBoard() {
		return game.getBoard();
	}
	
	@Deprecated
	public void setRound(int round) {
		this.game.setRound(round);
	}
	
	public boolean getBlackCastle() {
		return game.isBlackCastleMoveAllowed();
	}
	
	public boolean getWhiteCastle() {
		return game.isWhiteCastleMoveAllowed();
	}
	
	public boolean isInProgress() {
		return game.isInProgress();
	}
	
	public ChessPiece getCapturer() {
		return capturer;
	}
	
	public ChessPiece getCaptured() {
		return captured;
	}
	
	public Game getGame() {
		return game;
	}
	
	public boolean getCheckOn() {
		return game.isCheck();
	}
	
	public boolean getCheckMateOn() {
		return game.isCheckmate();
	}
	
	public boolean isWhiteTurn() {
		return game.getRound() % 2 == 0;
	}
	
	public void handleCapture(String algebraicDestination, ChessPiece chessPiece) {
		// remove old piece
		Location destination = game.getBoard().getLocation(algebraicDestination);
		ChessPiece dead = game.getBoard().getChessPieceByLocation(destination);
		removePiece(dead);
		
		// update metadata
		capturer = chessPiece;
		captured = dead;
		updateBoard(algebraicDestination, chessPiece);
	}
	
	public void handleCheck(String algebraicDestination, String checkType,
			ChessPiece chessPiece) {
		if (checkType.equals("+")) {
			game.setCheck(true);
		} else {
			game.setCheckmate(true);
		}
		updateBoard(algebraicDestination, chessPiece);
	}
	
	public void handleGameOver() {
		game.setInProgress(false);
	}
	
	public void updateBoard(String algebraicDestination, ChessPiece chessPiece) {
		Location destination = game.getBoard().getLocation(algebraicDestination);
		chessPiece.move(destination);
		game.incrementRound();
	}
	
	public void removePiece(ChessPiece chessPiece) {
		game.removeChessPiece(chessPiece);
	}
}