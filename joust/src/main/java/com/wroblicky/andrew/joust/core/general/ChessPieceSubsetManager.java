package com.wroblicky.andrew.joust.core.general;

import java.util.Set;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

/**
 * Provides a subset of the game's chess pieces given some qualification
 * 
 * @author Andrew Wroblicky
 *
 */
public interface ChessPieceSubsetManager {
	
	/**
	 * Returns the chess pieces matching the given qualification
	 * 
	 * @param qualification
	 */
	public Set<ChessPiece> getChessPieces(Qualifiable qualification);
	
	/**
	 * Returns the chess pieces matching the entire list of qualifications
	 * 
	 * @param qualification
	 */
	public Set<ChessPiece> getChessPieces(Qualifiable... qualifications);
	
	/**
	 * Removes the chess piece from the backing store
	 * 
	 * @param chessPiece
	 */
	public void removeChessPiece(ChessPiece chessPiece);
}