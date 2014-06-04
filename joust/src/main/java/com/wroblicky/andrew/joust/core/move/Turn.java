package com.wroblicky.andrew.joust.core.move;

import java.util.List;


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