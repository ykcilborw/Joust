package com.wroblicky.andrew.joust;

import java.util.Set;

import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.subset.ChessPieceSubsetManager;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;

@Deprecated
// Temporary class to aid in transition to subset manager
public class ChessPieceLookup {
	
	private ChessPieceSubsetManager chessPieceSubsetManager;
	
	public ChessPieceLookup(ChessPieceSubsetManager chessPieceSubsetManager) {
		this.chessPieceSubsetManager = chessPieceSubsetManager;
	}
	
	public Set<ChessPiece> get(String s) {
		return chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.valueOf(s));
	}
}