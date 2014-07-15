package com.wroblicky.andrew.joust.game.board;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.move.Move;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;

public class ChessBoardTest {

	@Test
	public void testGetLocation() {
		// algebraic
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a5");
		Assert.assertEquals(location.getAlgebraicLocation().equals("a5"), true);
		Assert.assertEquals(location.getXCoordinate() == 0, true);
		Assert.assertEquals(location.getYCoordinate() == 4, true);
		
		// x, y version
		Location location2 = board.getLocation(0, 4);
		Assert.assertEquals(location2.getAlgebraicLocation().equals("a5"), true);
		Assert.assertEquals(location2.getXCoordinate() == 0, true);
		Assert.assertEquals(location2.getYCoordinate() == 4, true);
	}
	
	@Test
	public void testGetLocationWithParameters() {
		ChessBoard board = new ChessBoard();
		Location initial = board.getLocation("a1");
		Assert.assertEquals(board.getLocation(initial, 1, 2).getAlgebraicLocation(), "b3");
	}
	
	@Test
	public void testNorthNeighbor() {
		// test adjusted x and y
		ChessBoard board = new ChessBoard();
		Location e1 = board.getLocation("e1");
		Location e8 = board.getLocation("e8");
		Assert.assertEquals(board.getNorthNeighbor(e1).getAlgebraicLocation(), "e2");
		Assert.assertEquals(board.getNorthNeighbor(e8), null);
	}
	
	@Test
	public void testNorthWestNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e1 = board.getLocation("e1");
		Location e8 = board.getLocation("e8");
		Assert.assertEquals(board.getNorthWestNeighbor(e1).getAlgebraicLocation(), "d2");
		Assert.assertEquals(board.getNorthWestNeighbor(e8), null);
	}
	
	@Test
	public void testWestNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e1 = board.getLocation("e1");
		Location a1 = board.getLocation("a1");
		Assert.assertEquals(board.getWestNeighbor(e1).getAlgebraicLocation(), "d1");
		Assert.assertEquals(board.getWestNeighbor(a1), null);
	}
	
	@Test
	public void testSouthWestNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e2 = board.getLocation("e2");
		Location a1 = board.getLocation("a1");
		Assert.assertEquals(board.getSouthWestNeighbor(e2).getAlgebraicLocation(), "d1");
		Assert.assertEquals(board.getSouthWestNeighbor(a1), null);
	}
	
	@Test
	public void testSouthNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e2 = board.getLocation("e2");
		Location a1 = board.getLocation("a1");
		Assert.assertEquals(board.getSouthNeighbor(e2).getAlgebraicLocation(), "e1");
		Assert.assertEquals(board.getSouthNeighbor(a1), null);
	}
	
	@Test
	public void testSouthEastNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e2 = board.getLocation("e2");
		Location a1 = board.getLocation("a1");
		Assert.assertEquals(board.getSouthEastNeighbor(e2).getAlgebraicLocation(), "f1");
		Assert.assertEquals(board.getSouthEastNeighbor(a1), null);
	}
	
	@Test
	public void testEastNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e1 = board.getLocation("e1");
		Location h8 = board.getLocation("h8");
		Assert.assertEquals(board.getEastNeighbor(e1).getAlgebraicLocation(), "f1");
		Assert.assertEquals(board.getEastNeighbor(h8), null);
	}
	
	@Test
	public void testNorthEastNeighbor() {
		ChessBoard board = new ChessBoard();
		Location e1 = board.getLocation("e1");
		Location e8 = board.getLocation("e8");
		Assert.assertEquals(board.getNorthEastNeighbor(e1).getAlgebraicLocation(), "f2");
		Assert.assertEquals(board.getNorthEastNeighbor(e8), null);
	}
	
	@Test
	public void testOnBoard() {
		ChessBoard board = new ChessBoard();
		Assert.assertEquals(board.onBoard(2, 2), true);
		Assert.assertEquals(board.onBoard(8, 8), false);
		Assert.assertEquals(board.onBoard(0, 7), true);
		Assert.assertEquals(board.onBoard(-1, 4), false);
		Assert.assertEquals(board.onBoard(16, 9), false);
	}
	
	@Test
	public void testGetLocationByChessPiece() {
		ChessBoard board = new ChessBoard();
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, new Location("a5"));
		Assert.assertEquals(board.getLocationByChessPiece(pawn).getAlgebraicLocation(), "a5");
	}

	@Test
	public void testGetChessPieceByLocation() {
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location).getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
	}

	@Test
	public void testAddChessPiece() {
		ChessBoard board = new ChessBoard();
		Location originalLocation = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, originalLocation);
		
		// make sure chess piece was added
		Assert.assertEquals(board.getChessPieceByLocation(originalLocation).getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
	}

	@Test
	public void testMoveChessPiece() {
		ChessBoard board = new ChessBoard();
		Location originalLocation = board.getLocation("a1");
		Location newLocation = board.getLocation("a2");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, originalLocation);
		
		// make sure chess piece was added
		Assert.assertEquals(board.getChessPieceByLocation(originalLocation).getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
		
		// make sure chess piece was not already in new location
		Assert.assertEquals(board.getChessPieceByLocation(newLocation), null);
		
		board.moveChessPiece(new Move(pawn, originalLocation, newLocation));
		Assert.assertEquals(board.getChessPieceByLocation(newLocation).getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
	}

	@Test
	public void testRemoveChessPiece() {
		ChessBoard board = new ChessBoard();
		Location location = board.getLocation("a1");
		Pawn pawn = new Pawn();
		board.addChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location).getChessPieceAllegianceType(), ChessPieceAllegianceType.BLACK_PAWN);
		board.removeChessPiece(pawn, location);
		Assert.assertEquals(board.getChessPieceByLocation(location), null);
	}
}