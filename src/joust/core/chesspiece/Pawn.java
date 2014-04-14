package joust.core.chesspiece;

import joust.core.general.*;
import java.util.*;


public class Pawn extends ChessPiece {
	
	private boolean hasMovedAtAll;
	private boolean movedTwice;
	private int round;
	
	public Pawn() {
	}
	
	public Pawn(Location l, String c, int id) {
		this.myLocation = l;
		this.myColor = c;
		this.hasMovedAtAll = false;
		this.movedTwice = false;
		this.isAlive = true;
		this.chessID = id;
		this.round = 0;
	}
	
	public boolean getmovedYet() {
		return hasMovedAtAll;
	}
	
	public boolean getMovedTwice() {
		return movedTwice;
	}
	
	public int getRound() {
		return round;
	}
	
	public String getMyType() {
		return "Pawn";
	}
	
	public String getMySymbol() {
		if (myColor.equals("b")) {
			return "p";
		} else {
			return "P";
		}
	}
	
	public String getFile() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(0, 1);
	}
	
	public String getRank() {
		String al = myLocation.getmyAlgebraicLocation();
		return al.substring(1, 2);
	}
	
	public String getRelativeRank() {
		String al = myLocation.getmyAlgebraicLocation().substring(1, 2);
		if (myColor.equals("b")) {
			int x = Integer.parseInt(al);
			int toReturn = 0;
			if (x == 1) {
				toReturn = 8;
			} else if (x == 2) {
				toReturn = 7;
			} else if (x == 3) {
				toReturn = 6;
			} else if (x == 4) {
				toReturn = 5;
			} else if (x == 5) {
				toReturn = 4;
			} else if (x == 6) {
				toReturn = 3;
			} else if (x == 7) {
				toReturn = 2;
			} else if (x == 8) {
				toReturn = 1;
			}
			return new Integer(toReturn).toString();
		} else {
			return al;
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
		//System.out.println("pawn possMoves myloc: " + myLocation);
		Location l = null;
		Location moveUpLeft = null;
		Location moveUpRight = null;
		int myRound = g.getmyRound();
		
		if(myColor.equals("w")) {
			l = myLocation.move(1, 0, 0, 0);
			moveUpLeft = myLocation.move(1, 1, 0, 0);
			moveUpRight = myLocation.move(1, 0, 0, 1);
			//System.out.println("pawn possMoves white: " + l + ", " + moveUpLeft + ", " + moveUpRight);
		} else {
			l = myLocation.move(0, 0, 1, 0);
			moveUpLeft = myLocation.move(0, 1, 1, 0);
			moveUpRight = myLocation.move(0, 0, 1, 1);
			//System.out.println("pawn possMoves black: " + l + ", " + moveUpLeft + ", " + moveUpRight);
		}
		
		if (l != null) {
			
			int x = l.getmyX();
			int y = l.getmyY();
			
			if (checkAvailability(g, l).equals("unoccupied") && x < 9 && x > 0 && y < 9 && y > 0){
				possibles.add(l);
				// Can only move two forward if the immediate space in front is also free
				if (hasMovedAtAll == false) {
					if (myColor.equals("w")) {
						Location l2 = myLocation.move(2, 0, 0, 0);
						int x2 = l2.getmyX();
						int y2 = l2.getmyY();
						if (checkAvailability(g, l2).equals("unoccupied") && x2 < 9 && x2 > 0 && y2 < 9 && y2 > 0){
							possibles.add(l2);
						}
					} else {
						Location l2 = myLocation.move(0, 0, 2, 0);
						int x2 = l2.getmyX();
						int y2 = l2.getmyY();
						if (checkAvailability(g, l2).equals("unoccupied") && x2 < 9 && x2 > 0 && y2 < 9 && y2 > 0){
							possibles.add(l2);
						}
					}
				}
			}
		}
		if (moveUpLeft != null) { 
			int ulX = moveUpLeft.getmyX();
			int ulY = moveUpLeft.getmyY();
			
			if (checkAvailability(g, moveUpLeft).equals("enemy") && ulX < 9 && ulX > 0 && ulY < 9 && ulY > 0) {
				possibles.add(moveUpLeft);
			}
			
			if (checkAvailability(g, moveUpLeft).equals("unoccupied") && hasMovedAtAll && ulX < 9 && ulX > 0 && ulY < 9 && ulY > 0) {
				Location moveLeft = myLocation.move(0, 1, 0, 0);
				int mlX = moveLeft.getmyX();
				int mlY = moveLeft.getmyY();
				
				if (checkAvailability(g, moveLeft).equals("enemy") && mlX < 9 && mlX > 0 && mlY < 9 && mlY > 0) {
					int newX = mlX - 1;
					int newY = mlY - 1;
					if (newX < 8 && newX > -1 && newY > -1 && newY < 8) {
						Location nLoc = g.getBoard()[newX][newY];
						ChessPiece c = g.getmyPositions().get(nLoc);
						Pawn p = c.getMyType().equals("Pawn") ? (Pawn)c : null;
						if (p != null) {
							if(p.getMovedTwice() && (p.getRound() == (myRound - 1))) {
								possibles.add(moveUpLeft);
							}
						}
					}
				}
			}
		}
		if (moveUpRight != null) {
			int urX = moveUpRight.getmyX();
			int urY = moveUpRight.getmyY();
			
			if (checkAvailability(g, moveUpRight).equals("enemy") && urX < 9 && urX > 0 && urY < 9 && urY > 0) {
				possibles.add(moveUpRight);
			}
			if (checkAvailability(g, moveUpRight).equals("unoccupied") && hasMovedAtAll && urX < 9 && urX > 0 && urY < 9 && urY > 0) {
				//boolean enemyToRight = false;
				Location moveRight = myLocation.move(0, 0, 0, 1);
				int mrX = moveRight.getmyX();
				int mrY = moveRight.getmyY();
				
				if (checkAvailability(g, moveRight).equals("enemy") && mrX < 9 && mrX > 0 && mrY < 9 && mrY > 0) {
					int newX = mrX - 1;
					int newY = mrY - 1;
					if (newX < 8 && newX > -1 && newY > -1 && newY < 8) {
						Location nLoc = g.getBoard()[newX][newY];
						ChessPiece c = g.getmyPositions().get(nLoc);
						Pawn p = c.getMyType().equals("Pawn") ? (Pawn)c : null;
						if (p != null) {
							if(p.getMovedTwice() && (p.getRound() == (myRound - 1))) {
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
		Location l = myLocation.move(1, 0, 0, 0);
		int x = l.getmyX();
		int y = l.getmyY();
		if (checkAvailability(g, l).equals("friend") && x < 9 && x > 0 && y < 9 && y > 0){
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
	
	
	public void move(Game g, Location l) {
		HashMap<Location, ChessPiece> positions = g.getmyPositions();
		Location onBoard = Location.getBoardLocation(g, myLocation);
		positions.remove(onBoard);
		//positions.put(onBoard, null);
		myLocation = l;
		Location onBoard2 = Location.getBoardLocation(g, l);
		positions.remove(onBoard2);
		positions.put(onBoard2, this);
	}
	
	public boolean equals(ChessPiece b) {
		boolean toReturn = false;
		if (chessID == b.getID() && b.getMyType().equals(this.getMyType())) {
			toReturn = true;
		}
		return toReturn;
	}

}
