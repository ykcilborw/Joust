package com.wroblicky.andrew.joust.game.chesspiece;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Occupier;

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
	public void testCheckAvailability() {
		ChessBoard board = new ChessBoard();
		Pawn pawn1 = new Pawn(Allegiance.WHITE, 1, board);
		Pawn pawn2 = new Pawn(Allegiance.BLACK, 2, board);
		board.addChessPiece(pawn1, board.getLocation("a5"));
		board.addChessPiece(pawn2, board.getLocation("a6"));
		Assert.assertEquals(pawn1.checkAvailability(board.getLocation("a5")) == Occupier.FRIEND, true);
		Assert.assertEquals(pawn1.checkAvailability(board.getLocation("a7")) == Occupier.UNOCCUPIED, true);
		Assert.assertEquals(pawn2.checkAvailability(board.getLocation("a5")) == Occupier.ENEMY, true);
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