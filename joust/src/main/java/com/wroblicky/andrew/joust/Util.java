package com.wroblicky.andrew.joust;

import java.util.Set;

import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;

public final class Util {

	public static String[][] getStringBoard(Set<ChessPiece> actives) {
		String[][] newBoard = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				newBoard[i][j] = "-"; // denotes unoccupied
			}
		}
		for (ChessPiece active : actives) {
			int currX = active.getLocation().getXCoordinate();
			int currY = active.getLocation().getYCoordinate();
			newBoard[currX - 1][currY - 1] = active
					.getChessPieceAllegianceType().toString();
		}
		return newBoard;
	}

	public static int reverseNum(int num) {
		int toReturn = num;
		if (num == 0) {
			toReturn = 7;
		} else if (num == 1) {
			toReturn = 6;
		} else if (num == 2) {
			toReturn = 5;
		} else if (num == 3) {
			toReturn = 4;
		} else if (num == 4) {
			toReturn = 3;
		} else if (num == 5) {
			toReturn = 2;
		} else if (num == 6) {
			toReturn = 1;
		} else if (num == 7) {
			toReturn = 0;
		}
		return toReturn;
	}

	public static int fileToNum(String file) {
		int toReturn = 0;
		if (file.equals("a")) {
			toReturn = 1;
		} else if (file.equals("b")) {
			toReturn = 2;
		} else if (file.equals("c")) {
			toReturn = 3;
		} else if (file.equals("d")) {
			toReturn = 4;
		} else if (file.equals("e")) {
			toReturn = 5;
		} else if (file.equals("f")) {
			toReturn = 6;
		} else if (file.equals("g")) {
			toReturn = 7;
		} else if (file.equals("h")) {
			toReturn = 8;
		}
		return toReturn;
	}

	public static int rankToNum(String rank) {
		int toReturn = 0;
		if (rank.equals("1")) {
			toReturn = 1;
		} else if (rank.equals("2")) {
			toReturn = 2;
		} else if (rank.equals("3")) {
			toReturn = 3;
		} else if (rank.equals("4")) {
			toReturn = 4;
		} else if (rank.equals("5")) {
			toReturn = 5;
		} else if (rank.equals("6")) {
			toReturn = 6;
		} else if (rank.equals("7")) {
			toReturn = 7;
		} else if (rank.equals("8")) {
			toReturn = 8;
		}
		return toReturn;
	}

	// called by Game when loading special config file
	public static Location getLocation(String id, Location[][] board) {
		int file = Util.fileToNum(id.substring(0, 1)) - 1;
		int rank = Integer.parseInt(id.substring(1, 2)) - 1;
		return board[rank][file];
	}

	// convenience method for printing
	public static void print(String s) {
		System.out.print(s);
	}

	public static void println(String s) {
		System.out.print(s + "\n");
	}

	public static void error(String s) {
		System.err.println(s);
	}
}