package com.wroblicky.andrew.joust.game.chesspiece;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Knight;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.Rook;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

public class KnightTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Knight horse = new Knight(Allegiance.BLACK, 1, board);
		Assert.assertEquals(horse.getID(), "n1");
	}
	
	@Test
	public void testGetMyType() {
		Knight horse = new Knight();
		Assert.assertEquals(horse.getChessPieceType(), ChessPieceType.KNIGHT);
	}

	@Test
	public void testGetMySymbol() {
		Knight blackHorse = new Knight(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackHorse.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_KNIGHT);
		
		Knight whiteHorse = new Knight(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteHorse.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_KNIGHT);
	}

	@Test
	public void testGetPossibleMoves() {
		ChessBoard board = new ChessBoard();
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		
		// no moves
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		List<Location> locations = horse.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// all moves
		board.removeChessPiece(castle, board.getLocation("a3"));
		board.removeChessPiece(pawn, board.getLocation("c3"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations2 = horse.getPossibleMoves();
		Assert.assertEquals(locations2.size(), 3);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("a3"));
		List<Location> locations3 = horse.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 2); // d2 and c3
	}

	@Test
	public void testGetDefenseMoves() {
		ChessBoard board = new ChessBoard();
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		
		// no moves
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		List<Location> locations = horse.getDefenseMoves();
		Assert.assertEquals(locations.size(), 3);
		
		// all moves
		board.removeChessPiece(castle, board.getLocation("a3"));
		board.removeChessPiece(pawn, board.getLocation("c3"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations2 = horse.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("a3"));
		List<Location> locations3 = horse.getDefenseMoves();
		Assert.assertEquals(locations3.size(), 1); // d2 and c3
	}

	@Test
	public void testCanReach() {
		ChessBoard board = new ChessBoard();
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		
		// neighbors
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		Assert.assertEquals(horse.canReach(board.getLocation("a3")), false);
		Assert.assertEquals(horse.canReach(board.getLocation("c3")), false);
		Assert.assertEquals(horse.canReach(board.getLocation("d2")), false);
		
		// no allies in path
		board.removeChessPiece(castle, board.getLocation("a3"));
		board.removeChessPiece(pawn, board.getLocation("c3"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(horse.canReach(board.getLocation("a3")), true);
		Assert.assertEquals(horse.canReach(board.getLocation("c3")), true);
		Assert.assertEquals(horse.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(horse.canReach(board.getLocation("e7")), false);
		
		// some allies
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		Assert.assertEquals(horse.canReach(board.getLocation("a3")), false);
		Assert.assertEquals(horse.canReach(board.getLocation("c3")), false);
		Assert.assertEquals(horse.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(horse.canReach(board.getLocation("e7")), false);
	}

	@Test
	public void testCanDefend() {
		ChessBoard board = new ChessBoard();
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		
		// neighbors
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		board.addChessPiece(horse, board.getLocation("b1"));
		Assert.assertEquals(horse.canDefend(board.getLocation("a3")), true);
		Assert.assertEquals(horse.canDefend(board.getLocation("c3")), true);
		Assert.assertEquals(horse.canDefend(board.getLocation("d2")), true);
		
		// no allies in path
		board.removeChessPiece(castle, board.getLocation("a3"));
		board.removeChessPiece(pawn, board.getLocation("c3"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(horse.canDefend(board.getLocation("a3")), false);
		Assert.assertEquals(horse.canDefend(board.getLocation("c3")), false);
		Assert.assertEquals(horse.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(horse.canDefend(board.getLocation("e7")), false);
		
		// some allies
		board.addChessPiece(castle, board.getLocation("a3"));
		board.addChessPiece(pawn, board.getLocation("c3"));
		Assert.assertEquals(horse.canDefend(board.getLocation("a3")), true);
		Assert.assertEquals(horse.canDefend(board.getLocation("c3")), true);
		Assert.assertEquals(horse.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(horse.canDefend(board.getLocation("e7")), false);
	}

	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
		board.addChessPiece(horse, board.getLocation("d4"));
		Assert.assertEquals(horse.getLocation() == board.getLocation("d4"), true);
		
		horse.move(board.getLocation("e6"));
		Assert.assertEquals(horse.getLocation().getAlgebraicLocation().equals("e6"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e6")) == horse, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == horse, false);
	}
}