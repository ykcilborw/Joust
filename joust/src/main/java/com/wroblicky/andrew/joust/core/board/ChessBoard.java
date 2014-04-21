package com.wroblicky.andrew.joust.core.board;

import java.util.HashMap;
import java.util.Map;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.move.Move;

public class ChessBoard {
	
	private Location[][] chessBoard;
	private Map<ChessPiece, Location> chessPieceToLocation;
	
	// constructors
	public ChessBoard() {
		this.chessBoard = new Location[8][8];
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
	}
	
	public ChessBoard(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
		this.chessPieceToLocation = new HashMap<ChessPiece, Location>();
	}
	
	public Location getLocationByChessPiece(ChessPiece chessPiece) {
		return chessPieceToLocation.get(chessPiece);
	}
	
	public ChessPiece getChessPieceByLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		return chessBoard[x][y].getChessPiece();
	}
	
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		addChessPieceToBoard(chessPiece, location);
		chessPieceToLocation.put(chessPiece, location);
	}
	
	private void addChessPieceToBoard(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(chessPiece);
	}
	
	public void moveChessPiece(Move move) {
		removeChessPiece(move.getChessPiece(), move.getStart());
		addChessPiece(move.getChessPiece(), move.getDestination());
	}
	
	public void removeChessPiece(ChessPiece chessPiece, Location location) {
		removeChessPieceFromBoard(chessPiece, location);
		chessPieceToLocation.remove(chessPiece);
	}
	
	public void removeChessPieceFromBoard(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(null);
	}
	
	@Deprecated
	public Location[][] getBoard() {
		return chessBoard;
	}

	public void printBoard() {
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(chessBoard[i][j].getChessPiece().getMySymbol() + " ");
			}
			System.out.println("");
		}
	}
}
