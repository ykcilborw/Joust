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

public class RookTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Rook blackCastle = new Rook(Allegiance.BLACK, 1, board);
		Assert.assertEquals(blackCastle.getID(), "r1");
		
		Rook whiteCastle = new Rook(Allegiance.WHITE, 1, board);
		Assert.assertEquals(whiteCastle.getID(), "R1");
	}
	
	@Test
	public void testGetMyType() {
		Rook castle = new Rook();
		Assert.assertEquals(castle.getChessPieceType(), ChessPieceType.ROOK);
	}

	@Test
	public void testGetMySymbol() {
		Rook blackCastle = new Rook(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackCastle.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_ROOK);
		
		Rook whiteCastle = new Rook(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteCastle.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_ROOK);
	}

	@Test
	public void testGetPossibleMoves() {
		ChessBoard board = new ChessBoard();
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
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
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
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
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
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
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		Knight horse = new Knight(Allegiance.WHITE, 1, board);
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
		Rook castle = new Rook(Allegiance.WHITE, 1, board);
		board.addChessPiece(castle, board.getLocation("d4"));
		Assert.assertEquals(castle.getLocation() == board.getLocation("d4"), true);
		
		castle.move(board.getLocation("d8"));
		Assert.assertEquals(castle.getLocation().getAlgebraicLocation().equals("d8"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d8")) == castle, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == castle, false);
	}
}