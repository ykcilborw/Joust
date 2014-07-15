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

public class QueenTest {

	@Test
	public void testGetID() {
		ChessBoard board = new ChessBoard();
		Queen queen = new Queen(Allegiance.BLACK, 1, board);
		Assert.assertEquals(queen.getID(), "q1");
	}
	
	@Test
	public void testGetMyType() {
		Queen queen = new Queen();
		Assert.assertEquals(queen.getChessPieceType(), ChessPieceType.QUEEN);
	}

	@Test
	public void testGetMySymbol() {
		Queen blackQueen = new Queen(Allegiance.BLACK, 1, new ChessBoard());
		Assert.assertEquals(blackQueen.getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_QUEEN);
		
		Queen whiteQueen = new Queen(Allegiance.WHITE, 1, new ChessBoard());
		Assert.assertEquals(whiteQueen.getChessPieceAllegianceType(), ChessPieceAllegianceType.WHITE_QUEEN);
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
		board.addChessPiece(bishop, board.getLocation("f1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("f2"));
		List<Location> locations = queen.getPossibleMoves();
		Assert.assertEquals(locations.size(), 0);
		
		// all moves
		board.removeChessPiece(king, board.getLocation("d1"));
		board.removeChessPiece(bishop, board.getLocation("f1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("f2"));
		List<Location> locations2 = queen.getPossibleMoves();
		// total is up + right sideways + left sideways + left + right
		Assert.assertEquals(locations2.size(), 7 + 2 + 5 + 2 + 5);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("e2"));
		List<Location> locations3 = queen.getPossibleMoves();
		Assert.assertEquals(locations3.size(), 14);
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
		board.addChessPiece(bishop, board.getLocation("f1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("f2"));
		List<Location> locations = queen.getDefenseMoves();
		Assert.assertEquals(locations.size(), 5);
		
		// all moves
		board.removeChessPiece(king, board.getLocation("d1"));
		board.removeChessPiece(bishop, board.getLocation("f1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("f2"));
		List<Location> locations2 = queen.getDefenseMoves();
		Assert.assertEquals(locations2.size(), 0);
		
		// restricted number of moves
		board.addChessPiece(pawn, board.getLocation("e2"));
		List<Location> locations3 = queen.getDefenseMoves();
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
		board.addChessPiece(bishop, board.getLocation("f1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("f2"));
		Assert.assertEquals(queen.canReach(board.getLocation("d1")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("f1")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("d2")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("e2")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("f2")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("e9")), false);
		
		// no allies in path
		board.removeChessPiece(king, board.getLocation("d1"));
		board.removeChessPiece(bishop, board.getLocation("f1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("f2"));
		Assert.assertEquals(queen.canReach(board.getLocation("d1")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("f1")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("e2")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("f2")), true);
		
		// non bordering allies in path
		board.addChessPiece(pawn, board.getLocation("g3"));
		board.addChessPiece(pawn2, board.getLocation("c3"));
		Assert.assertEquals(queen.canReach(board.getLocation("d2")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("f2")), true);
		Assert.assertEquals(queen.canReach(board.getLocation("a2")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("g3")), false);
		Assert.assertEquals(queen.canReach(board.getLocation("c3")), false);
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
		Pawn pawn4 = new Pawn(Allegiance.BLACK, 4, board);
		
		// neighbors
		board.addChessPiece(king, board.getLocation("d1"));
		board.addChessPiece(queen, board.getLocation("e1"));
		board.addChessPiece(bishop, board.getLocation("f1"));
		board.addChessPiece(pawn, board.getLocation("d2"));
		board.addChessPiece(pawn2, board.getLocation("e2"));
		board.addChessPiece(pawn3, board.getLocation("f2"));
		Assert.assertEquals(queen.canDefend(board.getLocation("d1")), true);
		Assert.assertEquals(queen.canDefend(board.getLocation("f1")), true);
		Assert.assertEquals(queen.canDefend(board.getLocation("d2")), true);
		Assert.assertEquals(queen.canDefend(board.getLocation("e2")), true);
		Assert.assertEquals(queen.canDefend(board.getLocation("f2")), true);
		Assert.assertEquals(queen.canDefend(board.getLocation("e8")), false);
		
		// no allies in path
		board.removeChessPiece(king, board.getLocation("d1"));
		board.removeChessPiece(bishop, board.getLocation("f1"));
		board.removeChessPiece(pawn, board.getLocation("d2"));
		board.removeChessPiece(pawn2, board.getLocation("e2"));
		board.removeChessPiece(pawn3, board.getLocation("f2"));
		Assert.assertEquals(queen.canDefend(board.getLocation("d1")), false);
		Assert.assertEquals(queen.canDefend(board.getLocation("f1")), false);
		Assert.assertEquals(queen.canDefend(board.getLocation("d2")), false);
		Assert.assertEquals(queen.canDefend(board.getLocation("e2")), false);
		Assert.assertEquals(queen.canDefend(board.getLocation("f2")), false);
		
		// allies in between
		board.addChessPiece(pawn, board.getLocation("g3"));
		board.addChessPiece(pawn2, board.getLocation("f2"));
		board.addChessPiece(pawn3, board.getLocation("c3"));
		board.addChessPiece(pawn4, board.getLocation("d2"));
		Assert.assertEquals(queen.canDefend(board.getLocation("d1")), false); // no one
		Assert.assertEquals(queen.canDefend(board.getLocation("f2")), true); // someone
		Assert.assertEquals(queen.canDefend(board.getLocation("g3")), false); // ally in way
		Assert.assertEquals(queen.canDefend(board.getLocation("c3")), false); // enemy!
		Assert.assertEquals(queen.canDefend(board.getLocation("d2")), false); // enemy in way
	}

	@Test
	public void testMove() {
		ChessBoard board = new ChessBoard();
		Queen queen = new Queen(Allegiance.WHITE, 1, board);
		board.addChessPiece(queen, board.getLocation("e1"));
		Assert.assertEquals(queen.getLocation() == board.getLocation("e1"), true);
		
		queen.move(board.getLocation("e6"));
		Assert.assertEquals(queen.getLocation().getAlgebraicLocation().equals("e6"), true);
		
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e6")) == queen, true);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d4")) == queen, false);
	}
}