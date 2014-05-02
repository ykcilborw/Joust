package com.wroblicky.andrew.joust.core.board;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.core.chesspiece.Pawn;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.move.Move;

public class ChessBoardTest {

	@Test
	public void testGetLocation() {
		// algebraic
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a5");
		Assert.assertEquals(location.getAlgebraicLocation().equals("a5"), true);
		Assert.assertEquals(location.getXCoordinate() == 0, true);
		Assert.assertEquals(location.getYCoordinate() == 4, true);
		
		// x, y version
		Location location2 = board.getLocation(0, 4);
		Assert.assertEquals(location2.getAlgebraicLocation().equals("a5"), true);
		Assert.assertEquals(location2.getXCoordinate() == 0, true);
		Assert.assertEquals(location2.getYCoordinate() == 4, true);
	}
	
	@Test
	public void testOnBoard() {
		ChessBoard board = new ChessBoard();
		Assert.assertEquals(board.onBoard(2, 2), true);
		Assert.assertEquals(board.onBoard(8, 8), false);
		Assert.assertEquals(board.onBoard(0, 7), true);
		Assert.assertEquals(board.onBoard(-1, 4), false);
		Assert.assertEquals(board.onBoard(16, 9), false);
	}
	
	@Test
	public void testGetLocationByChessPiece() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, new Location("a5"));
		Assert.assertEquals(board.getLocationByChessPiece(pawn).getAlgebraicLocation(), "a5");
	}

	@Test
	public void testGetChessPieceByLocation() {
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location).getMySymbol(), "p");
	}

	@Test
	public void testAddChessPiece() {
		ChessBoard board = new ChessBoard();
		Location originalLocation = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, originalLocation);
		
		// make sure chess piece was added
		Assert.assertEquals(board.getChessPieceByLocation(originalLocation).getMySymbol(), "p");
	}

	@Test
	public void testMoveChessPiece() {
		ChessBoard board = new ChessBoard();
		Location originalLocation = board.getLocation("a1");
		Location newLocation = board.getLocation("a2");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, originalLocation);
		
		// make sure chess piece was added
		Assert.assertEquals(board.getChessPieceByLocation(originalLocation).getMySymbol(), "p");
		
		// make sure chess piece was not already in new location
		Assert.assertEquals(board.getChessPieceByLocation(newLocation), null);
		
		board.moveChessPiece(new Move(pawn, originalLocation, newLocation));
		Assert.assertEquals(board.getChessPieceByLocation(newLocation).getMySymbol(), "p");
	}

	@Test
	public void testRemoveChessPiece() {
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location).getMySymbol(), ("p"));
		board.removeChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location), null);
	}
}