package com.wroblicky.andrew.joust.core.general;

import java.util.Set;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;

public class ChessPieceLookup {
	
	private ChessPieceSubsetManager chessPieceSubsetManager;
	
	public ChessPieceLookup(ChessPieceSubsetManager chessPieceSubsetManager) {
		this.chessPieceSubsetManager = chessPieceSubsetManager;
	}
	
	public Set<ChessPiece> get(String s) {
		return chessPieceSubsetManager.getChessPieces(ChessPieceAllegianceType.valueOf(s));
	}
}