package com.wroblicky.andrew.joust.game.chesspiece;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Knight;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

public class PawnTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Pawn blackPawn = new Pawn(Allegiance.BLACK, 1, board);
		Assert.assertEquals(blackPawn.getID(), "p1");
		
		Pawn whitePawn = new Pawn(Allegiance.WHITE, 1, board);
		Assert.assertEquals(whitePawn.getID(), "P1");
	}
	
	@Test
	public void testGetMyType() {
		Pawn pawn = new Pawn();
		Assert.assertEquals(pawn.getChessPieceType(), ChessPieceType.PAWN);
	}

	@Test
	public void testGetMySymbol() {
		Pawn blackPawn = new Pawn(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackPawn.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
		
		Pawn whitePawn = new Pawn(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whitePawn.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_PAWN);
	}

	@Test
	public void testGetPossibleMoves() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		Pawn pawn2 = new Pawn(Allegiance.BLACK, 2, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		
		// no moves
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(pawn2, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("b2"));
		List<Location> locations = pawn.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// only forward
		board.removeChessPiece(horse, board.getLocation("b2"));
		board.removeChessPiece(pawn2, board.getLocation("a3"));
		List<Location> locations2 = pawn.getPossibleMoves();
		Assert.assertEquals(locations2.size(), 2);
		
		// all moves
		board.addChessPiece(pawn2, board.getLocation("b3"));
		List<Location> locations3 = pawn.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 3);
	}

	@Test
	public void testGetDefenseMoves() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(pawn2, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("b3"));
		List<Location> locations = pawn.getDefenseMoves();
		Assert.assertEquals(locations.size(), 1);
		
		// no allies in path
		board.removeChessPiece(horse, board.getLocation("b3"));
		board.removeChessPiece(pawn2, board.getLocation("a3"));
		List<Location> locations2 = pawn.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// one ally in path
		board.addChessPiece(pawn2, board.getLocation("a3"));
		List<Location> locations3 = pawn.getDefenseMoves();
		Assert.assertEquals(locations3.size(), 0);
	}

	@Test
	public void testCanReach() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn3 = new Pawn(Allegiance.WHITE, 3, board);
		Pawn pawn4 = new Pawn(Allegiance.WHITE, 4, board);
		Pawn pawn5 = new Pawn(Allegiance.WHITE, 5, board);
		Pawn blackPawn = new Pawn(Allegiance.BLACK, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		
		
		// neighbors
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(pawn2, board.getLocation("a3"));
		board.addChessPiece(pawn3, board.getLocation("b2"));
		board.addChessPiece(pawn4, board.getLocation("a1"));
		board.addChessPiece(pawn5, board.getLocation("b1"));
		board.addChessPiece(horse, board.getLocation("b3"));
		Assert.assertEquals(pawn.canReach(board.getLocation("a2")), false);
		Assert.assertEquals(pawn.canReach(board.getLocation("b2")), false);
		Assert.assertEquals(pawn.canReach(board.getLocation("a1")), false);
		Assert.assertEquals(pawn.canReach(board.getLocation("e7")), false);
		
		// no allies in path
		board.removeChessPiece(horse, board.getLocation("b3"));
		board.removeChessPiece(pawn2, board.getLocation("a3"));
		board.removeChessPiece(pawn3, board.getLocation("b2"));
		board.removeChessPiece(pawn4, board.getLocation("a1"));
		board.removeChessPiece(pawn5, board.getLocation("b1"));
		board.addChessPiece(blackPawn, board.getLocation("b3"));
		Assert.assertEquals(pawn.canReach(board.getLocation("a3")), true);
		Assert.assertEquals(pawn.canReach(board.getLocation("b3")), true);
		Assert.assertEquals(pawn.canReach(board.getLocation("a1")), false);
		Assert.assertEquals(pawn.canReach(board.getLocation("e7")), false);
	}

	@Test
	public void testCanDefend() {
		// TODO
	}
	
	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn(Allegiance.WHITE, 1, board);
		board.addChessPiece(pawn, board.getLocation("d4"));
		Assert.assertEquals(pawn.getLocation() == board.getLocation("d4"), true);
		
		pawn.move(board.getLocation("d5"));
		Assert.assertEquals(pawn.getLocation().getAlgebraicLocation().equals("d5"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d5")) == pawn, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == pawn, false);
	}
}