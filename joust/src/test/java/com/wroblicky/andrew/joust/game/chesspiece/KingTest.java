package com.wroblicky.andrew.joust.game.chesspiece;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Bishop;
import com.wroblicky.andrew.joust.game.chesspiece.King;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.Queen;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;

public class KingTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.BLACK, 1, board);
		Assert.assertEquals(king.getID(), "k1");
	}
	
	@Test
	public void testGetMyType() {
		King king = new King();
		Assert.assertEquals(king.getChessPieceType(), ChessPieceType.KING);
	}

	@Test
	public void testGetMySymbol() {
		King blackKing = new King(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackKing.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_KING);
		
		King whiteKing = new King(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteKing.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_KING);
	}

	@Test
	public void testGetPossibleMoves() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.WHITE, 1, board);
		Queen queen = new Queen(Allegiance.WHITE, 1, board);
		Bishop bishop = new Bishop(Allegiance.WHITE, 2, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		Pawn pawn3 = new Pawn(Allegiance.WHITE, 4, board);
		
		// no moves
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(queen, board.getLocation("e1"));
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("c2"));
		List<Location> locations = king.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// all moves
		board.removeChessPiece(queen, board.getLocation("e1"));
		board.removeChessPiece(bishop, board.getLocation("c1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("c2"));
		board.removeChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(king, board.getLocation("c4"));
		List<Location> locations2 = king.getPossibleMoves();
		Assert.assertEquals(locations2.size(), 8);
		
		// restricted number of moves
		board.removeChessPiece(king, board.getLocation("c4"));
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(pawn, board.getLocation("e2"));
		List<Location> locations3 = king.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 4);
	}

	@Test
	public void testGetDefenseMoves() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.WHITE, 1, board);
		Queen queen = new Queen(Allegiance.WHITE, 1, board);
		Bishop bishop = new Bishop(Allegiance.WHITE, 2, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		Pawn pawn3 = new Pawn(Allegiance.WHITE, 4, board);
		
		// no moves
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(queen, board.getLocation("e1"));
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("c2"));
		List<Location> locations = king.getDefenseMoves();
		Assert.assertEquals(locations.size(), 5);
		
		// all moves
		board.removeChessPiece(queen, board.getLocation("e1"));
		board.removeChessPiece(bishop, board.getLocation("c1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("c2"));
		List<Location> locations2 = king.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("e2"));
		List<Location> locations3 = king.getDefenseMoves();
		Assert.assertEquals(locations3.size(), 1);
	}

	@Test
	public void testCanReach() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.WHITE, 1, board);
		Queen queen = new Queen(Allegiance.WHITE, 1, board);
		Bishop bishop = new Bishop(Allegiance.WHITE, 2, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		Pawn pawn3 = new Pawn(Allegiance.WHITE, 4, board);
		
		// neighbors
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(queen, board.getLocation("e1"));
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("c2"));
		Assert.assertEquals(king.canReach(board.getLocation("e1")), false);
		Assert.assertEquals(king.canReach(board.getLocation("c1")), false);
		Assert.assertEquals(king.canReach(board.getLocation("d2")), false);
		Assert.assertEquals(king.canReach(board.getLocation("e2")), false);
		Assert.assertEquals(king.canReach(board.getLocation("c2")), false);
		Assert.assertEquals(king.canReach(board.getLocation("e9")), false);
		
		// no allies in path
		board.removeChessPiece(queen, board.getLocation("e1"));
		board.removeChessPiece(bishop, board.getLocation("c1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("c2"));
		Assert.assertEquals(king.canReach(board.getLocation("e1")), true);
		Assert.assertEquals(king.canReach(board.getLocation("c1")), true);
		Assert.assertEquals(king.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(king.canReach(board.getLocation("e2")), true);
		Assert.assertEquals(king.canReach(board.getLocation("c2")), true);
		Assert.assertEquals(king.canReach(board.getLocation("c8")), false);
		
		// some allies in path
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		Assert.assertEquals(king.canReach(board.getLocation("d2")), false);
		Assert.assertEquals(king.canReach(board.getLocation("e2")), false);
		Assert.assertEquals(king.canReach(board.getLocation("c1")), true);
		Assert.assertEquals(king.canReach(board.getLocation("c7")), false);
	}

	@Test
	public void testCanDefend() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.WHITE, 1, board);
		Queen queen = new Queen(Allegiance.WHITE, 1, board);
		Bishop bishop = new Bishop(Allegiance.WHITE, 2, board);
		Pawn pawn = new Pawn(Allegiance.WHITE, 2, board);
		Pawn pawn2 = new Pawn(Allegiance.WHITE, 3, board);
		Pawn pawn3 = new Pawn(Allegiance.WHITE, 4, board);
		
		// neighbors
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(queen, board.getLocation("e1"));
		board.addChessPiece(bishop, board.getLocation("c1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("c2"));
		Assert.assertEquals(king.canDefend(board.getLocation("e1")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("c1")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("d2")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("e2")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("c2")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("e9")), false);
		
		// no allies in path
		board.removeChessPiece(queen, board.getLocation("e1"));
		board.removeChessPiece(bishop, board.getLocation("c1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("c2"));
		Assert.assertEquals(king.canDefend(board.getLocation("e1")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("c1")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("e2")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("c2")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("c8")), false);
		
		// some allies in path
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		Assert.assertEquals(king.canDefend(board.getLocation("d2")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("e2")), true);
		Assert.assertEquals(king.canDefend(board.getLocation("c1")), false);
		Assert.assertEquals(king.canDefend(board.getLocation("c7")), false);
	}

	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		King king = new King(Allegiance.WHITE, 1, board);
		board.addChessPiece(king, board.getLocation("e1"));
		Assert.assertEquals(king.getLocation() == board.getLocation("e1"), true);
		
		king.move(board.getLocation("e2"));
		Assert.assertEquals(king.getLocation().getAlgebraicLocation().equals("e2"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e2")) == king, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == king, false);
	}
}