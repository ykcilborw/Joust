package com.wroblicky.andrew.joust.core.general;

@Deprecated // need to incorporate inside a Turn object
public class CastleMove {
	
	private int myRound;
	private String myColor;
	private String kingorQueen;
	
	public CastleMove(int round, String color, String kingorQueen) {
		myRound = round;
		myColor = color;
		this.kingorQueen = kingorQueen;
	}
	
	public int getRound() {
		return myRound;
	}
	
	public String getColor() {
		return myColor;
	}
	
	public String getKingOrQueen() {
		return kingorQueen;
	}
}