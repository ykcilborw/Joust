package com.wroblicky.andrew.joust.core.general;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;

public class ChessPieceSubsetManagerImpl implements ChessPieceSubsetManager {
	
	private Set<ChessPiece> activePieces;
	private Set<ChessPiece> allPieces;
	private Map<ChessPieceType, Set<ChessPiece>> chessPieceTypeMap;
	private Map<ChessPieceAllegianceType, Set<ChessPiece>> chessPieceAllegianceTypeMap;
	private Set<ChessPiece> deceasedPieces;
	
	public ChessPieceSubsetManagerImpl(Set<ChessPiece> allPieces) {
		this.allPieces = allPieces;
		this.deceasedPieces = new HashSet<ChessPiece>();
		initialize();
	}

	public Set<ChessPiece> getChessPieces(Qualifiable qualification) {
		if (qualification == Scope.ALL) {
			return allPieces;
		} else if (qualification == Scope.NOTHING) {
			return new HashSet<ChessPiece>();
		} else if (qualification == Scope.ACTIVE) {
			return activePieces;
		} else if (qualification == Scope.DECEASED) {
			return deceasedPieces;
		} else if (qualification.getClass().getName().equals("ChessPieceType")) {
			return chessPieceTypeMap.get(qualification.getQualification().toString());
		} else if (qualification.getClass().getName().equals("ChessPieceAllegianceType")) {
			return chessPieceAllegianceTypeMap.get(qualification.getQualification().toString());
		} else {  // try to just return all of them
			return allPieces;
		}
	}

	public void removeChessPiece(ChessPiece chessPiece) {
		deceasedPieces.add(chessPiece);
		activePieces.remove(chessPiece);
		Set<ChessPiece> typeSet = chessPieceTypeMap.get(chessPiece.getMyType());
		typeSet.remove(chessPiece);
		// not sure needed
		chessPieceTypeMap.put(chessPiece.getMyType(), typeSet);
		
		Set<ChessPiece> allegianceSet = chessPieceAllegianceTypeMap.get(chessPiece.getMySymbol());
		allegianceSet.remove(chessPiece);
		// not sure needed
		chessPieceAllegianceTypeMap.put(chessPiece.getMySymbol(), allegianceSet);
	}
	
	private void initialize() {
		activePieces = new HashSet<ChessPiece>();
		initializeChessPieceTypeMap();
		initializeChessPieceAllegianceTypeMap();
		for (ChessPiece chessPiece : allPieces) {
			if (chessPiece.isAlive()) {
				activePieces.add(chessPiece);
				Set<ChessPiece> typeSet =  chessPieceTypeMap.get(chessPiece.getMyType());
				typeSet.add(chessPiece);
				chessPieceTypeMap.put(chessPiece.getMyType(), typeSet);
				Set<ChessPiece> allegianceSet =  chessPieceAllegianceTypeMap.get(chessPiece.getMySymbol());
				allegianceSet.add(chessPiece);
				chessPieceAllegianceTypeMap.put(chessPiece.getMySymbol(), allegianceSet);
			}
		}
	}
	
	private void initializeChessPieceTypeMap() {
		chessPieceTypeMap.put(ChessPieceType.PAWN, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.ROOK, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.KNIGHT, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.BISHOP, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.QUEEN, new HashSet<ChessPiece>());
		chessPieceTypeMap.put(ChessPieceType.KING, new HashSet<ChessPiece>());
	}
	
	private void initializeChessPieceAllegianceTypeMap() {
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