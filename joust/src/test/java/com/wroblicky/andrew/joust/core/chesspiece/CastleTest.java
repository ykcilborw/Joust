package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.board.Location;
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
		ChessBoard board = new ChessBoard();
		Castle castle = new Castle(Allegiance.WHITE, 1, board);
		Horse horse = new Horse(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		
		// no moves
		board.addChessPiece(castle, board.getLocation("a1"));
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		List<Location> locations = castle.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// all moves
		board.removeChessPiece(horse, board.getLocation("b1"));
		board.removeChessPiece(pawn, board.getLocation("a2"));
		List<Location> locations2 = castle.getPossibleMoves();
		Assert.assertEquals(locations2.size(), 14);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("d1"));
		List<Location> locations3 = castle.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 3);
	}

	@Test
	public void testGetDefenseMoves() {
		ChessBoard board = new ChessBoard();
		Castle castle = new Castle(Allegiance.WHITE, 1, board);
		Horse horse = new Horse(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(castle, board.getLocation("a1"));
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		List<Location> locations = castle.getDefenseMoves();
		Assert.assertEquals(locations.size(), 2);
		
		// no allies in path
		board.removeChessPiece(horse, board.getLocation("b1"));
		board.removeChessPiece(pawn, board.getLocation("a2"));
		List<Location> locations2 = castle.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("d1"));
		List<Location> locations3 = castle.getDefenseMoves();
		Assert.assertEquals(locations3.size(), 2);
	}

	@Test
	public void testCanReach() {
		ChessBoard board = new ChessBoard();
		Castle castle = new Castle(Allegiance.WHITE, 1, board);
		Horse horse = new Horse(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(castle, board.getLocation("a1"));
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		Assert.assertEquals(castle.canReach(board.getLocation("a2")), false);
		Assert.assertEquals(castle.canReach(board.getLocation("b1")), false);
		Assert.assertEquals(castle.canReach(board.getLocation("e7")), false);
		
		// no allies in path
		board.removeChessPiece(horse, board.getLocation("b1"));
		board.removeChessPiece(pawn, board.getLocation("a2"));
		Assert.assertEquals(castle.canReach(board.getLocation("a2")), true);
		Assert.assertEquals(castle.canReach(board.getLocation("b1")), true);
		Assert.assertEquals(castle.canReach(board.getLocation("e7")), false);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("d1"));
		Assert.assertEquals(castle.canReach(board.getLocation("a2")), true);
		Assert.assertEquals(castle.canReach(board.getLocation("a3")), false);
		Assert.assertEquals(castle.canReach(board.getLocation("b1")), true);
		Assert.assertEquals(castle.canReach(board.getLocation("e7")), false);
	}

	@Test
	public void testCanDefend() {
		ChessBoard board = new ChessBoard();
		Castle castle = new Castle(Allegiance.WHITE, 1, board);
		Horse horse = new Horse(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(castle, board.getLocation("a1"));
		board.addChessPiece(pawn, board.getLocation("a2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		Assert.assertEquals(castle.canDefend(board.getLocation("a2")), true);
		Assert.assertEquals(castle.canDefend(board.getLocation("b1")), true);
		Assert.assertEquals(castle.canDefend(board.getLocation("e7")), false);
		
		// no allies in path
		board.removeChessPiece(horse, board.getLocation("b1"));
		board.removeChessPiece(pawn, board.getLocation("a2"));
		Assert.assertEquals(castle.canDefend(board.getLocation("a2")), false);
		Assert.assertEquals(castle.canDefend(board.getLocation("b1")), false);
		Assert.assertEquals(castle.canDefend(board.getLocation("e7")), false);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(horse, board.getLocation("d1"));
		Assert.assertEquals(castle.canDefend(board.getLocation("a2")), false);
		Assert.assertEquals(castle.canDefend(board.getLocation("a3")), true);
		Assert.assertEquals(castle.canDefend(board.getLocation("b1")), false);
		Assert.assertEquals(castle.canDefend(board.getLocation("e7")), false);
	}
	
	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Castle castle = new Castle(Allegiance.WHITE, 1, board);
		board.addChessPiece(castle, board.getLocation("d4"));
		Assert.assertEquals(castle.getLocation() == board.getLocation("d4"), true);
		
		castle.move(board.getLocation("d8"));
		Assert.assertEquals(castle.getLocation().getAlgebraicLocation().equals("d8"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d8")) == castle, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == castle, false);
	}
}