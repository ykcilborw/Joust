package com.wroblicky.andrew.joust.core.chesspiece;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.core.general.Location;

public class ChessPieceTest {

	@Test
	public void testGetLocation() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		board.addChessPiece(pawn, new Location("a5"));
		Assert.assertEquals(pawn.getLocation().getAlgebraicLocation(), "a5");
	}

	@Test
	public void testGetFile() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		board.addChessPiece(pawn, board.getLocation("a5"));
		Assert.assertEquals(pawn.getFile(), "a");
	}

	@Test
	public void testGetRank() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		board.addChessPiece(pawn, new Location("a5"));
		Assert.assertEquals(pawn.getRank(), "5");
	}

	@Test
	public void testGetRelativeRank() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		board.addChessPiece(pawn, new Location("a5"));
		Assert.assertEquals(pawn.getRelativeRank(), "4");
	}

	@Test
	public void testGetAllegiance() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		Assert.assertEquals(pawn.getAllegiance(), Allegiance.BLACK);
	}

	@Test
	public void testIsBlack() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.BLACK, 1, board);
		Assert.assertEquals(pawn.isBlack(), true);
		Assert.assertEquals(pawn.isWhite(), false);
	}

	@Test
	public void testIsWhite() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		Assert.assertEquals(pawn.isBlack(), false);
		Assert.assertEquals(pawn.isWhite(), true);
	}

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 17, board);
		Assert.assertEquals(pawn.getID(), 17);
	}

	@Test
	public void testCheckAvailability() {
		ChessBoard board = new ChessBoard();
		Pawn pawn1 = new Pawn(Allegiance.WHITE, 1, board);
		Pawn pawn2 = new Pawn(Allegiance.BLACK, 2, board);
		board.addChessPiece(pawn1, new Location("a5"));
		board.addChessPiece(pawn2, new Location("a6"));
		Assert.assertEquals(pawn1.checkAvailability(board.getLocation("a5")).equals("friend"), true);
		Assert.assertEquals(pawn1.checkAvailability(board.getLocation("a7")).equals("unoccupied"), true);
		Assert.assertEquals(pawn2.checkAvailability(board.getLocation("a5")).equals("enemy"), true);
	}

	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		board.addChessPiece(pawn, new Location("a5"));
		pawn.move(board.getLocation("a6"));
		Assert.assertEquals(pawn.getLocation().getAlgebraicLocation(), "a6");
		Assert.assertEquals(pawn.getLocation().getAlgebraicLocation().equals("a5"), false);
	}
}