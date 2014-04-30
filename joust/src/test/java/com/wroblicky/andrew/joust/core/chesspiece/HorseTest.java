package com.wroblicky.andrew.joust.core.chesspiece;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;

public class HorseTest {

	@Test
	public void testGetMyType() {
		Horse horse = new Horse();
		Assert.assertEquals(horse.getMyType(), "Horse");
	}

	@Test
	public void testGetMySymbol() {
		Horse blackHorse = new Horse(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackHorse.getMySymbol(), "n");
		
		Horse whiteHorse = new Horse(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteHorse.getMySymbol(), "N");
	}

	/*@Test
	public void testGetPossibleMoves() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetDefenseMoves() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanReach() {
		fail("Not yet implemented");
	}

	@Test
	public void testCanDefend() {
		fail("Not yet implemented");
	} */

	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Horse horse = new Horse(Allegiance.WHITE, 1, board);
		board.addChessPiece(horse, board.getLocation("d4"));
		Assert.assertEquals(horse.getLocation() == board.getLocation("d4"), true);
		
		horse.move(board.getLocation("e6"));
		Assert.assertEquals(horse.getLocation().getAlgebraicLocation().equals("e6"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e6")) == horse, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == horse, false);
	}
}