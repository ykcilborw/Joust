package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Game;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the pawn chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Pawn extends ChessPiece {
	
	private boolean hasMovedAtAll;
	private boolean movedTwice;
	private int round;
	
	public Pawn() {
		this.allegiance = Allegiance.BLACK;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = 1;
		this.round = 0;
		this.chessBoard = new ChessBoard();
	}
	
	public Pawn(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.alive = true;
		this.chessID = id;
		this.round = 0;
		this.chessBoard = chessBoard;
	}
	
	public boolean isMovedAtAll() {
		return hasMovedAtAll;
	}
	
	public boolean isMovedTwice() {
		return movedTwice;
	}
	
	public int getRound() {
		return round;
	}
	
	public String getMyType() {
		return "Pawn";
	}
	
	public String getMySymbol() {
		if (isBlack()) {
			return "p";
		} else {
			return "P";
		}
	}
	
	public void setMovedYet(boolean hasMovedAtAll) {
		this.hasMovedAtAll = hasMovedAtAll;
	}
	
	public void setMovedTwice(boolean movedTwice) {
		this.movedTwice = movedTwice;
	}
	
	public void setRound(int r) {
		round = r;
	}
	
	// TODO
	// add checks to see if can take piece diagonally and if it can move 2 spaces up
	/*
	 * Super RARE CASE (Example Scenario):
	 * Player 1: moves P5 (pawn 5) up by 2 
	 * Player 2: moves p1 (or any other pawn not to the right or left lane of p5) down by 1 or 2
	 * Player 1: moves P5 up by 1
	 * Player 2: moves p4 or p6 down by 2
	 * Player 1: can legally take that piece by moving diagonally
	 */

	public ArrayList<Location> getPossibleMoves(Game g) {
		ArrayList<Location> possibles = new ArrayList<Location>();
		//System.out.println("pawn possMoves myloc: " + getLocation());
		Location l = null;
		Location moveUpLeft = null;
		Location moveUpRight = null;
		int myRound = g.getmyRound();
		
		if(!isBlack()) {
			l = getLocation().move(1, 0, 0, 0);
			moveUpLeft = getLocation().move(1, 1, 0, 0);
			moveUpRight = getLocation().move(1, 0, 0, 1);
			//System.out.println("pawn possMoves white: " + l + ", " + moveUpLeft + ", " + moveUpRight);
		} else {
			l = getLocation().move(0, 0, 1, 0);
			moveUpLeft = getLocation().move(0, 1, 1, 0);
			moveUpRight = getLocation().move(0, 0, 1, 1);
			//System.out.println("pawn possMoves black: " + l + ", " + moveUpLeft + ", " + moveUpRight);
		}
		
		if (l != null) {
			
			int x = l.getXCoordinate();
			int y = l.getYCoordinate();
			
			if (checkAvailability(l).equals("unoccupied") && x < 9 && x > 0 && y < 9 && y > 0){
				possibles.add(l);
				// Can only move two forward if the immediate space in front is also free
				if (hasMovedAtAll == false) {
					if (!isBlack()) {
						Location l2 = getLocation().move(2, 0, 0, 0);
						int x2 = l2.getXCoordinate();
						int y2 = l2.getYCoordinate();
						if (checkAvailability(l2).equals("unoccupied") && x2 < 9 && x2 > 0 && y2 < 9 && y2 > 0){
							possibles.add(l2);
						}
					} else {
						Location l2 = getLocation().move(0, 0, 2, 0);
						int x2 = l2.getXCoordinate();
						int y2 = l2.getYCoordinate();
						if (checkAvailability(l2).equals("unoccupied") && x2 < 9 && x2 > 0 && y2 < 9 && y2 > 0){
							possibles.add(l2);
						}
					}
				}
			}
		}
		if (moveUpLeft != null) { 
			int ulX = moveUpLeft.getXCoordinate();
			int ulY = moveUpLeft.getYCoordinate();
			
			if (checkAvailability(moveUpLeft).equals("enemy") && ulX < 9 && ulX > 0 && ulY < 9 && ulY > 0) {
				possibles.add(moveUpLeft);
			}
			
			if (checkAvailability(moveUpLeft).equals("unoccupied") && hasMovedAtAll && ulX < 9 && ulX > 0 && ulY < 9 && ulY > 0) {
				Location moveLeft = getLocation().move(0, 1, 0, 0);
				int mlX = moveLeft.getXCoordinate();
				int mlY = moveLeft.getYCoordinate();
				
				if (checkAvailability(moveLeft).equals("enemy") && mlX < 9 && mlX > 0 && mlY < 9 && mlY > 0) {
					int newX = mlX - 1;
					int newY = mlY - 1;
					if (newX < 8 && newX > -1 && newY > -1 && newY < 8) {
						Location nLoc = chessBoard.getLocation(newX, newY);
						ChessPiece c = g.getMyPositions().get(nLoc);
						Pawn p = c.getMyType().equals("Pawn") ? (Pawn)c : null;
						if (p != null) {
							if(p.isMovedTwice() && (p.getRound() == (myRound - 1))) {
								possibles.add(moveUpLeft);
							}
						}
					}
				}
			}
		}
		if (moveUpRight != null) {
			int urX = moveUpRight.getXCoordinate();
			int urY = moveUpRight.getYCoordinate();
			
			if (checkAvailability(moveUpRight).equals("enemy") && urX < 9 && urX > 0 && urY < 9 && urY > 0) {
				possibles.add(moveUpRight);
			}
			if (checkAvailability(moveUpRight).equals("unoccupied") && hasMovedAtAll && urX < 9 && urX > 0 && urY < 9 && urY > 0) {
				//boolean enemyToRight = false;
				Location moveRight = getLocation().move(0, 0, 0, 1);
				int mrX = moveRight.getXCoordinate();
				int mrY = moveRight.getYCoordinate();
				
				if (checkAvailability(moveRight).equals("enemy") && mrX < 9 && mrX > 0 && mrY < 9 && mrY > 0) {
					int newX = mrX - 1;
					int newY = mrY - 1;
					if (newX < 8 && newX > -1 && newY > -1 && newY < 8) {
						Location nLoc = chessBoard.getLocation(newX, newY);
						ChessPiece c = g.getMyPositions().get(nLoc);
						Pawn p = c.getMyType().equals("Pawn") ? (Pawn)c : null;
						if (p != null) {
							if(p.isMovedTwice() && (p.getRound() == (myRound - 1))) {
								possibles.add(moveUpRight);
							}
						}
					}
				}
			}
		}
			
		return possibles;
	}
	
	public ArrayList<Location> getDefenseMoves(Game g) {
		ArrayList<Location> possibles = new ArrayList<Location>();
		Location l = getLocation().move(1, 0, 0, 0);
		int x = l.getXCoordinate();
		int y = l.getYCoordinate();
		if (checkAvailability(l).equals("friend") && x < 9 && x > 0 && y < 9 && y > 0){
			possibles.add(l);
		}
		return possibles;
	}
	
	//TODO
	// add support for becoming a queen 
	
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			Location temp = possibles.get(i);
			//System.out.println("pawn canReach move: " + temp);
			if (temp.equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	public boolean canDefend(Game g, Location l) {
		ArrayList<Location> possibles = this.getDefenseMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
}