package com.wroblicky.andrew.joust.core.general;

import java.util.List;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

/**
 * Provides a subset of the game's chess pieces given some qualification
 * 
 * @author Andrew Wroblicky
 *
 */
public interface ChessPieceSubsetManager {
	
	/**
	 * Adds the chess piece to the backing store
	 * 
	 * @param chessPiece
	 */
	public void addChessPiece(ChessPiece chessPiece);
	
	/**
	 * Returns the chess pieces matching the given qualification
	 * 
	 * @param qualification
	 */
	public List<ChessPiece> getChessPieces(ChessPieceQualification qualification);
	
	/**
	 * Returns the chess pieces matching the entire list of qualifications
	 * 
	 * @param qualification
	 */
	public List<ChessPiece> getChessPieces(ChessPieceQualification... qualifications);
	
	/**
	 * Removes the chess piece from the backing store
	 * 
	 * @param chessPiece
	 */
	public void removeChessPiece(ChessPiece chessPiece);
	
	public enum ChessPieceQualification {
		
		ACTIVE("active"),
		DEAD("dead"),
		WHITE("white"),
		BLACK("black"),
		PAWN("pawn"),
		ROOK("rook"),
		KNIGHT("knight"),
		BISHOP("bishop"),
		QUEEN("queen"),
		KING("king");
		
		private String qualification;
		
		ChessPieceQualification(String qualification) {
			this.qualification = qualification;
		}
		
		public String getQualification() {
			return this.qualification;
		}
	}
}