package com.wroblicky.andrew.joust.core.general;

public interface GameManager {
	
	/**
	 * Updates the game state after another turn is played
	 */
	public void playNextTurn();
	
	/**
	 * Brings back the game state to what it was before the last turn was played
	 */
	public void undoCurrentTurn();	
}