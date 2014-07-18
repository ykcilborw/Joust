package com.wroblicky.andrew.joust.game.subset;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceAllegianceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.ChessPieceType;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Qualifiable;
import com.wroblicky.andrew.joust.game.subset.qualifiable.Scope;

/**
 * Provides a subset of the game's chess pieces given some qualification
 * 
 * @author Andrew Wroblicky
 *
 */
public final class ChessPieceSubsetManager {
	
	private Set<ChessPiece> allPieces;
	private Set<ChessPiece> actives;
	private Set<ChessPiece> blackActives;
	private Set<ChessPiece> whiteActives;
	private Map<ChessPieceType, Set<ChessPiece>> chessPieceTypeMap;
	private Map<ChessPieceAllegianceType, Set<ChessPiece>> chessPieceAllegianceTypeMap;
	private Set<ChessPiece> deceasedPieces;
	
	public ChessPieceSubsetManager(Set<ChessPiece> allPieces) {
		this.allPieces = allPieces;
		this.deceasedPieces = new HashSet<ChessPiece>();
		
		initialize();
	}

	/**
	 * Returns the chess pieces matching the given qualification
	 * 
	 * @param qualification
	 */
	public Set<ChessPiece> getChessPieces(Qualifiable qualification) {
		if (qualification == Scope.ALL) {
			return allPieces;
		} else if (qualification == Scope.NOTHING) {
			return new HashSet<ChessPiece>();
		} else if (qualification == Scope.ACTIVE) {
			return actives;
		} else if (qualification == Scope.BLACK_ACTIVE) {
			return blackActives;
		} else if (qualification == Scope.WHITE_ACTIVE) {
			return whiteActives;
		} else if (qualification == Scope.DECEASED) {
			return deceasedPieces;
		} else if (qualification.getClass().getSimpleName().equals("ChessPieceType")) {
			return chessPieceTypeMap.get(qualification.getQualification());
		} else if (qualification.getClass().getSimpleName().equals("ChessPieceAllegianceType")) {
			return chessPieceAllegianceTypeMap.get(qualification.getQualification());
		} else {  // try to just return all of them
			return allPieces;
		}
	}
	
	/**
	 * Adds the chess piece back in to the backing store
	 */
	public void addChessPiece(ChessPiece chessPiece) {
		deceasedPieces.remove(chessPiece);
		actives.add(chessPiece);
		if (chessPiece.isBlack()) {
			blackActives.add(chessPiece);
		} else {
			whiteActives.add(chessPiece);
		}
		Set<ChessPiece> typeSet = chessPieceTypeMap.get(chessPiece.getChessPieceType());
		typeSet.add(chessPiece);
		
		Set<ChessPiece> allegianceSet = chessPieceAllegianceTypeMap.get(chessPiece.getChessPieceAllegianceType());
		allegianceSet.add(chessPiece);
	}

	/**
	 * Removes the chess piece from the backing store
	 * 
	 * @param chessPiece
	 */
	public void removeChessPiece(ChessPiece chessPiece) {
		deceasedPieces.add(chessPiece);
		actives.remove(chessPiece);
		if (chessPiece.isBlack()) {
			blackActives.remove(chessPiece);
		} else {
			whiteActives.remove(chessPiece);
		}
		Set<ChessPiece> typeSet = chessPieceTypeMap.get(chessPiece.getChessPieceType());
		typeSet.remove(chessPiece);
		// not sure needed
		chessPieceTypeMap.put(chessPiece.getChessPieceType(), typeSet);
		
		Set<ChessPiece> allegianceSet = chessPieceAllegianceTypeMap.get(chessPiece.getChessPieceAllegianceType());
		allegianceSet.remove(chessPiece);
		// not sure needed
		chessPieceAllegianceTypeMap.put(chessPiece.getChessPieceAllegianceType(), allegianceSet);
	}
	
	private void initialize() {
		actives = new HashSet<ChessPiece>();
		blackActives = new HashSet<ChessPiece>();
		whiteActives = new HashSet<ChessPiece>();
		initializeChessPieceTypeMap();
		initializeChessPieceAllegianceTypeMap();
		for (ChessPiece chessPiece : allPieces) {
			if (chessPiece.isAlive()) {
				actives.add(chessPiece);
				if (chessPiece.isBlack()) {
					blackActives.add(chessPiece);
				} else {
					whiteActives.add(chessPiece);
				}
				Set<ChessPiece> typeSet =  chessPieceTypeMap.get(chessPiece.getChessPieceType());
				typeSet.add(chessPiece);
				chessPieceTypeMap.put(chessPiece.getChessPieceType(), typeSet);
				Set<ChessPiece> allegianceSet =  chessPieceAllegianceTypeMap.get(chessPiece.getChessPieceAllegianceType());
				allegianceSet.add(chessPiece);
				chessPieceAllegianceTypeMap.put(chessPiece.getChessPieceAllegianceType(), allegianceSet);
			}
		}
	}
	
	private void initializeChessPieceTypeMap() {
		this.chessPieceTypeMap = new HashMap<ChessPieceType, Set<ChessPiece>>();
		chessPieceTypeMap.put(ChessPieceType.PAWN, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.ROOK, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.KNIGHT, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.BISHOP, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.QUEEN, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.KING, new HashSet<ChessPiece>());
	}
	
	private void initializeChessPieceAllegianceTypeMap() {
		this.chessPieceAllegianceTypeMap = new HashMap<ChessPieceAllegianceType, Set<ChessPiece>>();
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_PAWN, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_ROOK, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_KNIGHT, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_BISHOP, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_QUEEN, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.BLACK_KING, new HashSet<ChessPiece>());
		
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_PAWN, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_ROOK, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_KNIGHT, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_BISHOP, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_QUEEN, new HashSet<ChessPiece>());
		chessPieceAllegianceTypeMap.put(ChessPieceAllegianceType.WHITE_KING, new HashSet<ChessPiece>());
	}
}