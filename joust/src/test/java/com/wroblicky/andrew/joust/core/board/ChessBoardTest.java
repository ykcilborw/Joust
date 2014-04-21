package com.wroblicky.andrew.joust.core.board;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.core.chesspiece.Pawn;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.move.Move;

public class ChessBoardTest {


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
		Location location = board.getLocation("a0");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location).getMySymbol(), ("p"));
		board.removeChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location), null);
	}

	@Test
	public void testGetBoard() {
		// to be removed as a method
	}
}