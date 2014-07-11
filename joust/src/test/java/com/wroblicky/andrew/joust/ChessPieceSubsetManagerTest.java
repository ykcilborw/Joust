package com.wroblicky.andrew.joust;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.chesspiece.Bishop;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.chesspiece.King;
import com.wroblicky.andrew.joust.game.chesspiece.Knight;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.Queen;
import com.wroblicky.andrew.joust.game.chesspiece.Rook;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.subset.ChessPieceSubsetManager;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Scope;

public class ChessPieceSubsetManagerTest {
	
	private ChessPieceSubsetManager chessPieceSubsetManager;
	private King whiteKing;
	
	@Before
	public void init() {
		// create relevant chess pieces
		Set<ChessPiece> actives = new HashSet<ChessPiece>();
		ChessBoard chessBoard = new ChessBoard();
		
		Pawn wp1 = new Pawn(Allegiance.WHITE, 1, chessBoard);
		Pawn wp2 = new Pawn(Allegiance.WHITE, 2, chessBoard);
		Pawn wp3 = new Pawn(Allegiance.WHITE, 3, chessBoard);
		Pawn wp4 = new Pawn(Allegiance.WHITE, 4, chessBoard);
		Pawn wp5 = new Pawn(Allegiance.WHITE, 5, chessBoard);
		Pawn wp6 = new Pawn(Allegiance.WHITE, 6, chessBoard);
		Pawn wp7 = new Pawn(Allegiance.WHITE, 7, chessBoard);
		Pawn wp8 = new Pawn(Allegiance.WHITE, 8, chessBoard);
		Pawn bp1 = new Pawn(Allegiance.BLACK, 1, chessBoard);
		Pawn bp2 = new Pawn(Allegiance.BLACK, 2, chessBoard);
		Pawn bp3 = new Pawn(Allegiance.BLACK, 3, chessBoard);
		Pawn bp4 = new Pawn(Allegiance.BLACK, 4, chessBoard);
		Pawn bp5 = new Pawn(Allegiance.BLACK, 5, chessBoard);
		Pawn bp6 = new Pawn(Allegiance.BLACK, 6, chessBoard);
		Pawn bp7 = new Pawn(Allegiance.BLACK, 7, chessBoard);
		Pawn bp8 = new Pawn(Allegiance.BLACK, 8, chessBoard);
		Rook wc1 = new Rook(Allegiance.WHITE, 1, chessBoard);
		Rook wc2 = new Rook(Allegiance.WHITE, 2, chessBoard);
		Knight wh1 = new Knight(Allegiance.WHITE, 1, chessBoard);
		Knight wh2 = new Knight(Allegiance.WHITE, 2, chessBoard);
		Bishop wb1 = new Bishop(Allegiance.WHITE, 1, chessBoard);
		Bishop wb2 = new Bishop(Allegiance.WHITE, 2, chessBoard);
		Queen wq1 = new Queen(Allegiance.WHITE, 1, chessBoard);
		King wk1 = new King(Allegiance.WHITE, 1, chessBoard);
		Rook bc1 = new Rook(Allegiance.BLACK, 1, chessBoard);
		Rook bc2 = new Rook(Allegiance.BLACK, 2, chessBoard);
		Knight bh1 = new Knight(Allegiance.BLACK, 1, chessBoard);
		Knight bh2 = new Knight(Allegiance.BLACK, 2, chessBoard);
		Bishop bb1 = new Bishop(Allegiance.BLACK, 1, chessBoard);
		Bishop bb2 = new Bishop(Allegiance.BLACK, 2, chessBoard);
		Queen bq1 = new Queen(Allegiance.BLACK, 1, chessBoard);
		King bk1 = new King(Allegiance.BLACK, 1, chessBoard);
		// white pawns
		actives.add(wp1);
		actives.add(wp2);
		actives.add(wp3);
		actives.add(wp4);
		actives.add(wp5);
		actives.add(wp6);
		actives.add(wp7);
		actives.add(wp8);
		
		// black pawns
		actives.add(bp1);
		actives.add(bp2);
		actives.add(bp3);
		actives.add(bp4);
		actives.add(bp5);
		actives.add(bp6);
		actives.add(bp7);
		actives.add(bp8);
		
		// white castles
		actives.add(wc1);
		actives.add(wc2);
		
		// black castles
		actives.add(bc1);
		actives.add(bc2);
		
		// white knights
		actives.add(wh1);
		actives.add(wh2);
		
		// black knights
		actives.add(bh1);
		actives.add(bh2);
		
		// white bishops
		actives.add(wb1);
		actives.add(wb2);
		
		// black bishops
		actives.add(bb1);
		actives.add(bb2);
		
		// queens
		actives.add(bq1);
		actives.add(wq1);
		
		// kings
		actives.add(bk1);
		actives.add(wk1);
		chessPieceSubsetManager = new ChessPieceSubsetManager(actives);
		whiteKing = wk1;
	}

	@Test
	public void testGetAllActiveChessPieces() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(Scope.ACTIVE);
		Assert.assertEquals(actives.size(), 32);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 16);
		Assert.assertEquals(rookCount, 4);
		Assert.assertEquals(knightCount, 4);
		Assert.assertEquals(bishopCount, 4);
		Assert.assertEquals(queenCount, 2);
		Assert.assertEquals(kingCount, 2);
	}
	
	@Test
	public void testGetAllBlackActiveChessPieces() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(Scope.BLACK_ACTIVE);
		Assert.assertEquals(actives.size(), 16);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 8);
		Assert.assertEquals(rookCount, 2);
		Assert.assertEquals(knightCount, 2);
		Assert.assertEquals(bishopCount, 2);
		Assert.assertEquals(queenCount, 1);
		Assert.assertEquals(kingCount, 1);
	}
	
	@Test
	public void testGetAllWhiteActiveChessPieces() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(Scope.WHITE_ACTIVE);
		Assert.assertEquals(actives.size(), 16);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 8);
		Assert.assertEquals(rookCount, 2);
		Assert.assertEquals(knightCount, 2);
		Assert.assertEquals(bishopCount, 2);
		Assert.assertEquals(queenCount, 1);
		Assert.assertEquals(kingCount, 1);
	}
	
	@Test
	public void testGetAllDeceasedChessPieces() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(Scope.DECEASED);
		Assert.assertEquals(actives.size(), 0);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllPawns() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.PAWN);
		Assert.assertEquals(actives.size(), 16);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 16);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllRooks() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.ROOK);
		Assert.assertEquals(actives.size(), 4);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 4);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllKnights() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.KNIGHT);
		Assert.assertEquals(actives.size(), 4);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 4);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllBishops() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.BISHOP);
		Assert.assertEquals(actives.size(), 4);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 4);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllQueens() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.QUEEN);
		Assert.assertEquals(actives.size(), 2);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 2);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllKings() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceType.KING);
		Assert.assertEquals(actives.size(), 2);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 2);
	}
	
	@Test
	public void testGetAllWhitePawns() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.WHITE_PAWN);
		Assert.assertEquals(actives.size(), 8);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 8);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllBlackPawns() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_PAWN);
		Assert.assertEquals(actives.size(), 8);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 8);
		Assert.assertEquals(rookCount, 0);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllWhiteRooks() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.WHITE_ROOK);
		Assert.assertEquals(actives.size(), 2);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 2);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}
	
	@Test
	public void testGetAllBlackRooks() {
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.BLACK_ROOK);
		Assert.assertEquals(actives.size(), 2);
		
		int pawnCount = 0;
		int rookCount = 0;
		int knightCount = 0;
		int bishopCount = 0;
		int queenCount = 0;
		int kingCount = 0;
		for (ChessPiece chessPiece : actives) {
			if (chessPiece instanceof Pawn) {
				pawnCount += 1;
			} else if (chessPiece instanceof Rook) {
				rookCount += 1;
			} else if (chessPiece instanceof Knight) {
				knightCount += 1;
			} else if (chessPiece instanceof Bishop) {
				bishopCount += 1;
			} else if (chessPiece instanceof Queen) {
				queenCount += 1;
			} else if (chessPiece instanceof King) {
				kingCount += 1;
			}
		}
		
		Assert.assertEquals(pawnCount, 0);
		Assert.assertEquals(rookCount, 2);
		Assert.assertEquals(knightCount, 0);
		Assert.assertEquals(bishopCount, 0);
		Assert.assertEquals(queenCount, 0);
		Assert.assertEquals(kingCount, 0);
	}

	@Test
	public void testRemoveChessPiece() {
		chessPieceSubsetManager.removeChessPiece(whiteKing);
		Set<ChessPiece> actives = chessPieceSubsetManager.getChessPieces(Scope.ACTIVE);
		Assert.assertEquals(actives.size(), 31);
		Set<ChessPiece> deceased = chessPieceSubsetManager.getChessPieces(Scope.DECEASED);
		Assert.assertEquals(deceased.size(), 1);
		Set<ChessPiece> kings = chessPieceSubsetManager.getChessPieces(ChessPieceType.KING);
		Assert.assertEquals(kings.size(), 1);
		Set<ChessPiece> whiteKings = chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.WHITE_KING);
		Assert.assertEquals(whiteKings.size(), 0);
	}

}
