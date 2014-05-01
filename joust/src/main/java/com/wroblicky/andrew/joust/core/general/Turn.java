package com.wroblicky.andrew.joust.core.general;

import java.util.List;

import com.wroblicky.andrew.joust.core.general.move.Move;

public class Turn {
	
	private List<Move> moves;
	private String result;
	
	
	public List<Move> getMoves() {
		return moves;
	}
	
	public void setMoves(List<Move> moves) {
		this.moves = moves;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
}