package com.wroblicky.andrew.joust.game.board;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;

public final class Location {
	private int myX;
	private int myY;
	private String myAlgebraicLocation;
	private final String myFile;
	private final String myRank;
	private ChessPiece myChessPiece;

	public Location(String s) {
		myAlgebraicLocation = s;
		myFile = s.substring(0, 1);
		myRank = s.substring(1, 2);
		myY = Integer.parseInt(myRank) - 1;
		if (myFile.equals("a")) {
			myX = 0;
		} else if (myFile.equals("b")) {
			myX = 1;
		} else if (myFile.equals("c")) {
			myX = 2;
		} else if (myFile.equals("d")) {
			myX = 3;
		} else if (myFile.equals("e")) {
			myX = 4;
		} else if (myFile.equals("f")) {
			myX = 5;
		} else if (myFile.equals("g")) {
			myX = 6;
		} else if (myFile.equals("h")) {
			myX = 7;
		}
	}

	public int getXCoordinate() {
		return myX;
	}

	public void setXCoordinate(int x) {
		myX = x;
	}

	public int getYCoordinate() {
		return myY;
	}

	public void setYCoordinate(int y) {
		myY = y;
	}

	public String getAlgebraicLocation() {
		return myAlgebraicLocation;
	}

	public void setMyAlgebraicLocation(String al) {
		myAlgebraicLocation = al;
	}

	public String getFile() {
		return myFile;
	}

	public String getRank() {
		return myRank;
	}

	public int getComponentNumber() {
		return Util.reverseNum(myY) * 8 + myX;
	}

	public ChessPiece getChessPiece() {
		return myChessPiece;
	}

	public void setChessPiece(ChessPiece chessPiece) {
		this.myChessPiece = chessPiece;
	}

	public boolean equals(Location l) {
		if (l != null
				&& (l.getXCoordinate() == myX && l.getYCoordinate() == myY)) {
			return true;
		}
		return false;
	}

	public static String convert(int x, int y) {
		String stringX = null;
		if (x == 1) {
			stringX = "a";
		} else if (x == 2) {
			stringX = "b";
		} else if (x == 3) {
			stringX = "c";
		} else if (x == 4) {
			stringX = "d";
		} else if (x == 5) {
			stringX = "e";
		} else if (x == 6) {
			stringX = "f";
		} else if (x == 7) {
			stringX = "g";
		} else if (x == 8) {
			stringX = "h";
		}
		return stringX + y;
	}

	@Override
	public String toString() {
		return myAlgebraicLocation;
	}
}