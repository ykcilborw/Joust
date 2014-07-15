package com.wroblicky.andrew.joust.game.chesspiece;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Bishop;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

public class BishopTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Bishop blackBishop = new Bishop(Allegiance.BLACK, 1, board);
		Assert.assertEquals(blackBishop.getID(), "b1");
		
		Bishop whiteBishop = new Bishop(Allegiance.WHITE, 1, board);
		Assert.assertEquals(whiteBishop.getID(), "B1");
	}
	
	@Test
	public void testGetMyType() {
		Bishop bishop = new Bishop();
		Assert.assertEquals(bishop.getChessPieceType(), ChessPieceType.BISHOP);
	}

	@Test
	public void testGetMySymbol() {
		Bishop blackBishop = new Bishop(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackBishop.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_BISHOP);
		
		Bishop whiteBishop = new Bishop(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteBishop.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_BISHOP);
	}

	@Test
	public void testGetPossibleMoves() {
		ChessBoard board = new ChessBoard();
		Bishop bishop = new Bishop(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		
		// no moves
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("b2"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations = bishop.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// all moves
		board.removeChessPiece(pawn, board.getLocation("b2"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations2 = bishop.getPossibleMoves();
		Assert.assertEquals(locations2.size(), 7);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(pawn2, board.getLocation("f4"));
		List<Location> locations3 = bishop.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 3);
	}

	@Test
	public void testGetDefenseMoves() {
		ChessBoard board = new ChessBoard();
		Bishop bishop = new Bishop(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		
		// no moves
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("b2"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations = bishop.getDefenseMoves();
		Assert.assertEquals(locations.size(), 2);
		
		// all moves
		board.removeChessPiece(pawn, board.getLocation("b2"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		List<Location> locations2 = bishop.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(pawn2, board.getLocation("f4"));
		List<Location> locations3 = bishop.getDefenseMoves();
		Assert.assertEquals(locations3.size(), 2);
	}

	@Test
	public void testCanReach() {
		ChessBoard board = new ChessBoard();
		Bishop bishop = new Bishop(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("b2"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(bishop.canReach(board.getLocation("b2")), false);
		Assert.assertEquals(bishop.canReach(board.getLocation("d2")), false);
		Assert.assertEquals(bishop.canReach(board.getLocation("f4")), false);
		
		// no allies in path
		board.removeChessPiece(pawn, board.getLocation("b2"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(bishop.canReach(board.getLocation("b2")), true);
		Assert.assertEquals(bishop.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(bishop.canReach(board.getLocation("f4")), true);
		Assert.assertEquals(bishop.canReach(board.getLocation("f5")), false);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(pawn2, board.getLocation("f4"));
		Assert.assertEquals(bishop.canReach(board.getLocation("b2")), true);
		Assert.assertEquals(bishop.canReach(board.getLocation("a3")), false);
		Assert.assertEquals(bishop.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(bishop.canReach(board.getLocation("f4")), false);
	}

	@Test
	public void testCanDefend() {
		ChessBoard board = new ChessBoard();
		Bishop bishop = new Bishop(Allegiance.WHITE, 1, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 2, board);
		
		// neighbors
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("b2"));
		board.addChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(bishop.canDefend(board.getLocation("b2")), true);
		Assert.assertEquals(bishop.canDefend(board.getLocation("d2")), true);
		Assert.assertEquals(bishop.canDefend(board.getLocation("f4")), false);
		
		// no allies in path
		board.removeChessPiece(pawn, board.getLocation("b2"));
		board.removeChessPiece(pawn2, board.getLocation("d2"));
		Assert.assertEquals(bishop.canDefend(board.getLocation("b2")), false);
		Assert.assertEquals(bishop.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(bishop.canDefend(board.getLocation("f4")), false);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("a3"));
		board.addChessPiece(pawn2, board.getLocation("f4"));
		Assert.assertEquals(bishop.canDefend(board.getLocation("b2")), false);
		Assert.assertEquals(bishop.canDefend(board.getLocation("a3")), true);
		Assert.assertEquals(bishop.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(bishop.canDefend(board.getLocation("f4")), true);
	}
}