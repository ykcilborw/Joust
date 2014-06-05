package com.wroblicky.andrew.joust.core.general;

import java.util.Set;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.game.Game;
import com.wroblicky.andrew.joust.core.qualifiable.ChessPieceAllegianceType;

public class ChessPieceLookup {
	
	private Game game;
	
	public ChessPieceLookup(Game game) {
		this.game = game;
	}
	
	public Set<ChessPiece> get(String s) {
		return game.getChessPieces(ChessPieceAllegianceType.valueOf(s));
	}
}