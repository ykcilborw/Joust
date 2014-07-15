package com.wroblicky.andrew.joust.game;

import org.junit.Assert;
import org.junit.Test;

import com.wroblicky.andrew.joust.game.GameManager;
import com.wroblicky.andrew.joust.game.GameSetup;
import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;

public class GameSetupTest {

	@Test
	public void testSetupDefaultGame() {
		GameManager gameManager = GameSetup.setupDefaultGame();
		
		// metadata
		Assert.assertEquals(gameManager.getActivePieces().size() == 32, true);
		Assert.assertEquals(gameManager.getCheckOn() == false, true);
		Assert.assertEquals(gameManager.getCheckMateOn() == false, true);
		Assert.assertEquals(gameManager.getRound() == 0, true);
		Assert.assertEquals(gameManager.getBlackCastle() == true, true);
		Assert.assertEquals(gameManager.getWhiteCastle() == true, true);
		Assert.assertEquals(gameManager.getBoard() != null, true);
		
		ChessBoard board = gameManager.getBoard();
		
		// white pawns
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("a2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("b2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("c2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("f2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("g2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("h2")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_PAWN);
		
		// black pawns
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("a7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("b7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("c7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("f7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("g7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("h7")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_PAWN);
		
		// white royalty
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("a1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_ROOK);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("b1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_KNIGHT);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("c1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_BISHOP);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_QUEEN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_KING);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("f1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_BISHOP);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("g1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_KNIGHT);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("h1")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.WHITE_ROOK);
		
		// black royalty
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("a8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_ROOK);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("b8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_KNIGHT);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("c8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_BISHOP);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("d8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_QUEEN);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("e8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_KING);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("f8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_BISHOP);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("g8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_KNIGHT);
		Assert.assertEquals(board.getChessPieceByLocation(board.getLocation("h8")).getChessPieceAllegianceType(),
				ChessPieceAllegianceType.BLACK_ROOK);
	}

	@Test
	public void testSetupSpecialLayout() {
		//TODO
	}

}