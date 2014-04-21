package com.wroblicky.andrew.joust.core.chesspiece;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;

public class BishopTest {

	@Test
	public void testGetMyType() {
		Bishop bishop = new Bishop();
		Assert.assertEquals(bishop.getMyType(), "Bishop");
	}

	@Test
	public void testGetMySymbol() {
		Bishop blackBishop = new Bishop(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackBishop.getMySymbol(), "b");
		
		Bishop whiteBishop = new Bishop(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteBishop.getMySymbol(), "B");
	}

	@Test
	public void testGetPossibleMoves() {
	}

	@Test
	public void testGetDefenseMoves() {
	}

	@Test
	public void testCanReach() {
	}

	@Test
	public void testCanDefend() {
	}
}