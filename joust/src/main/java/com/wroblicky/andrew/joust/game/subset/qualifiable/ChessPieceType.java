package com.wroblicky.andrew.joust.game.subset.qualifiable;


public enum ChessPieceType implements Qualifiable {
	
	PAWN("pawn"),
	ROOK("rook"),
	KNIGHT("knight"),
	BISHOP("bishop"),
	QUEEN("queen"),
	KING("king");
	
	private String chessPieceType;
	
	ChessPieceType(String chessPieceType) {
		this.chessPieceType = chessPieceType;
	}
	
	public Qualifiable getQualification() {
		return this;
	}
	
	public String getChessPieceType() {
		return this.chessPieceType;
	}
}