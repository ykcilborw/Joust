package com.wroblicky.andrew.joust.game.subset.qualifiable;


public enum ChessPieceAllegianceType implements Qualifiable {
	
	BLACK_PAWN("p"),
	BLACK_ROOK("r"),
	BLACK_KNIGHT("n"),
	BLACK_BISHOP("b"),
	BLACK_QUEEN("q"),
	BLACK_KING("k"),
	WHITE_PAWN("P"),
	WHITE_ROOK("R"),
	WHITE_KNIGHT("N"),
	WHITE_BISHOP("B"),
	WHITE_QUEEN("Q"),
	WHITE_KING("K");
	
	private String chessPieceAllegianceType;
	
	ChessPieceAllegianceType(String chessPieceAllegianceType) {
		this.chessPieceAllegianceType = chessPieceAllegianceType;
	}
	
	public String getChessPieceAllegianceType() {
		return this.chessPieceAllegianceType;
	}
	
	public Qualifiable getQualification() {
		return this;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
}