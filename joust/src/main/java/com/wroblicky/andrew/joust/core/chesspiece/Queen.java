package com.wroblicky.andrew.joust.core.chesspiece;

import java.util.ArrayList;
import java.util.List;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.general.Location;

/**
 * Represents the queen chess piece
 * 
 * @author Andrew Wroblicky
 *
 */
public class Queen extends ChessPiece {
	
	public Queen() {
		this.allegiance = Allegiance.BLACK;
		this.alive = true;
		this.chessBoard = new ChessBoard();
		this.chessID = 1;
	}
	
	public Queen(Allegiance allegiance, int id, ChessBoard chessBoard) {
		this.allegiance = allegiance;
		this.alive = true;
		this.chessBoard = chessBoard;
		this.chessID = id;
	}
	
	@Override
	public String getMyType() {
		return "Queen";
	}
	
	@Override
	public String getMySymbol() {
		if (isBlack()) {
			return "q";
		} else {
			return "Q";
		}
	}
	
	@Override
	public List<Location> getPossibleMoves(){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = getLocation().getXCoordinate();
		int y = getLocation().getYCoordinate();
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = y;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = y;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = x;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = x;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	@Override
	public List<Location> getDefenseMoves(){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = getLocation().getXCoordinate();
		int y = getLocation().getYCoordinate();
		//System.out.println("queen defense x: " + x);
		//System.out.println("queen defense y: " + y);
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			//System.out.println("queen defense available: " + checkAvailability(l));
			if ((result.equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if ((result.equals("friend")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			//System.out.println("queen defense nextX: " + nextX);
			//System.out.println("queen defense nextY: " + nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				//System.out.println("queen defense added");
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = y;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			}  else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = nextX - 1;
			nextY = y;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if ((checkAvailability(l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = x;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		nextX = x;
		nextY = y;
		stillValid = true;
		while (stillValid) {
			nextX = x;
			nextY = nextY - 1;
			Location l = new Location(nextX, nextY);
			String result = checkAvailability(l);
			if (result.equals("unoccupied") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
			} else if (result.equals("friend") && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return possibles;
	}
}