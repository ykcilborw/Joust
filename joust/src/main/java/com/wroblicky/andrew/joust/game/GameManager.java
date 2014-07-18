package com.wroblicky.andrew.joust.game;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.move.GameStateChange;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.move.Termination;
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
	
	public void playTurn(Turn turn) {
		for (GameStateChange gameStateChange : turn.getGameStateChanges()) {
			playGameStateChange(gameStateChange);
		}
		if (turn.isCheck()) {
			game.setCheck(true);
		}
		if (turn.isCheckmate()) {
			game.setCheckmate(true);
		}
		game.incrementRound();
		game.addTurn(turn);
	}
	
	private void playGameStateChange(GameStateChange gameStateChange) {
		if (gameStateChange instanceof Move) {
			Move move = (Move) gameStateChange;
			ChessPiece chessPiece = move.getChessPiece();
			Location destination = move.getDestination();
			if (destination == null) {
				game.removeChessPiece(chessPiece);
			} else {
				chessPiece.move(destination);
			}
		} else if (gameStateChange instanceof Termination) {
			game.setInProgress(false);
		}
	}
	
	public void undoTurn() {
		Turn turn = game.getTurns().get(game.getRound() - 1);
		// iterate in reverse
		List<GameStateChange> gameStateChanges = turn.getGameStateChanges();
		ListIterator<GameStateChange> gameStateChangeIterator = gameStateChanges.listIterator(gameStateChanges.size());
		while (gameStateChangeIterator.hasPrevious()) {
			undoGameStateChange(gameStateChangeIterator.previous());
		}
		
		if (turn.isCheck()) {
			game.setCheck(false);
		}
		if (turn.isCheckmate()) {
			game.setCheckmate(false);
		}
		game.decrementRound();
		game.removeTurn();
	}
	
	private void undoGameStateChange(GameStateChange gameStateChange) {
		if (gameStateChange instanceof Move) {
			Move move = (Move) gameStateChange;
			ChessPiece chessPiece = move.getChessPiece();
			if (move.getDestination() == null) {
				game.addChessPiece(chessPiece, move.getStart());
			} else {
				Location destination = move.getStart(); // start since undoing
				chessPiece.move(destination);
			}
		} else if (gameStateChange instanceof Termination) {
			game.setInProgress(true);
		}
	}
}