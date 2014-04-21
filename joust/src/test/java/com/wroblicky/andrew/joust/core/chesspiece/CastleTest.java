package com.wroblicky.andrew.joust.core.chesspiece;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;

public class CastleTest {

	@Test
	public void testGetMyType() {
		Castle castle = new Castle();
		Assert.assertEquals(castle.getMyType(), "Rook");
	}

	@Test
	public void testGetMySymbol() {
		Castle blackCastle = new Castle(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackCastle.getMySymbol(), "r");
		
		Castle whiteCastle = new Castle(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteCastle.getMySymbol(), "R");
	}

	@Test
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
	}

	@Test
	public void testCastle() {
		fail("Not yet implemented");
	}
}