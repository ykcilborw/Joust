package com.wroblicky.andrew.joust.game;

import java.util.Set;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.GameStateChange;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Qualifiable;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Scope;

/**
 * Manages the state of the Game object
 * 
 * @author Andrew Wroblicky
 *
 */
public final class GameManager {
	
	private Game game;

	
	public GameManager(Game game) {
		this.game = game;
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
	
	public void handleCapture(String algebraicDestination, ChessPiece capturer) {
		// remove old piece
		Location destination = game.getBoard().getLocation(algebraicDestination);
		ChessPiece captured = game.getBoard().getChessPieceByLocation(destination);
		// null destination means piece was captured
		Turn turn = new Turn(new Move(captured, captured.getLocation(), null)); 
		
		// update metadata
		turn.setCaptured(captured);
		turn.setCapturer(capturer);
		turn.addMove(new Move(capturer, capturer.getLocation(), 
				getBoard().getLocation(algebraicDestination)));
		updateBoard(turn);
	}
	
	public void handleCheck(String algebraicDestination, String checkType,
			ChessPiece chessPiece) {
		if (checkType.equals("+")) {
			game.setCheck(true);
		} else {
			game.setCheckmate(true);
		}
		updateBoard(new Turn(new Move(chessPiece, chessPiece.getLocation(), 
				getBoard().getLocation(algebraicDestination))));
	}
	
	public void handleGameOver() {
		game.setInProgress(false);
	}
	
	public void updateBoard(Turn turn) {
		for (GameStateChange gameStateChange : turn.getGameStateChanges()) {
			handleMove(gameStateChange);
		}
		game.incrementRound();
		game.addTurn(turn);
	}
	
	private void handleMove(GameStateChange gameStateChange) {
		if (gameStateChange instanceof Move) {
			Move move = (Move) gameStateChange;
			ChessPiece chessPiece = move.getChessPiece();
			Location destination = move.getDestination();
			if (destination == null) {
				game.removeChessPiece(chessPiece);
			} else {
				chessPiece.move(destination);
			}
		}
	}
}