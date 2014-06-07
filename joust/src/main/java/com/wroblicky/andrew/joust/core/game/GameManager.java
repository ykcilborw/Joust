package com.wroblicky.andrew.joust.core.game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.ChessPieceLookup;
import com.wroblicky.andrew.joust.core.general.ChessPieceSubsetManager;
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
	private List<String[][]> visitedBoards; // debugging ONLY!!!
	private ChessPiece capturer;
	private ChessPiece captured;
	private ChessPieceLookup chessPieceLookup;

	public GameManager(Game game, ChessPieceSubsetManager chessPieceSubsetManager) {
		this.game = game;
		visitedBoards = new ArrayList<String[][]>();
		chessPieceLookup = new ChessPieceLookup(chessPieceSubsetManager);
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
	
	public void printVisitedBoards() {
		FileWriter fstream = null;
		try {
			fstream = new FileWriter("out.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			for (int i = 0; i < visitedBoards.size(); i++) {
				int round = i;
				String[][] board = visitedBoards.get(i);
				out.write("Current Round: " + round + "\n");
					for (int j = 7; j > -1; j--) {
						for (int k = 0; k < 8; k++) {
							out.write(board[k][j] + " ");
						}
						out.write("\n");
					}
			}
			out.close();
		} catch (Exception e) {
			System.err.println("Could not find file to print boards to");
			System.exit(-1);
		}
	}

	public void printBoard2(int round) {
		String[][] board = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = "-"; //denotes unoccupied
			}
		}
		Set<ChessPiece> chessPieces = getActivePieces();
		for (ChessPiece chessPiece : chessPieces) {
			ChessPiece current = chessPiece;
			int currX = current.getLocation().getXCoordinate();
			int currY = current.getLocation().getYCoordinate();
			board[currX - 1][currY - 1] = current.getMySymbol().toString(); 
		}
		visitedBoards.add(board);
		
		System.out.print("Current Round: " + round + "\n");
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println("");
		}
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