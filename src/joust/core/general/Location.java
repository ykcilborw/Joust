package joust.core.general;

public class Location {
	private int myX;
	private int myY;
	private String myAlgebraicLocation;
	private String myFile;
	private String myRank;
	
	//Doesn't work needs to be rewritten
	public Location(int x, int y) {
		//System.out.println("x: " + x);
		//System.out.println("y: " + y);
		myY = y;
		myX = x;
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
		myAlgebraicLocation = stringX + myY;
		//System.out.println("myAlgebra: " + myAlgebraicLocation);
	}
	
	public Location(String s) {
		//System.out.println("s: " + s);
		myAlgebraicLocation = s;
		String x = s.substring(0, 1);
		myFile = x;
		String y = s.substring(1, 2);
		myRank = y;
		//System.out.println("y: " + y);
		int y2 = Integer.parseInt(y);
		if (x.equals("a")) {
			myX = 1;
		} else if (x.equals("b")) {
			myX = 2;
		} else if (x.equals("c")) {
			myX = 3;
		} else if (x.equals("d")) {
			myX = 4;
		} else if (x.equals("e")) {
			myX = 5;
		} else if (x.equals("f")) {
			myX = 6;
		} else if (x.equals("g")) {
			myX = 7;
		} else if (x.equals("h")) {
			myX = 8;
		}
		//System.out.println("location constructor x: " + x);
		//System.out.println("location constructor myX: " + myX);
		myY = y2;
		
	}
	
	public int getXCoordinate() {
		return myX;
	}
	
	public int getYCoordinate() {
		return myY;
	}
	
	public String getAlgebraicLocation() {
		return myAlgebraicLocation;
	}
	
	public String getFile() {
		return myFile;
	}
	
	public String getRank() {
		return myRank;
	}
	
	//All setters need to be fixed. Not priority at moment though
	public void setmyX(int x) {
		myX = x;
	}
	
	public void setmyY(int y) {
		myY = y;
	}
	
	public void setmyAlgebraicLocation(String al) {
		myAlgebraicLocation = al;
	}
	
	public boolean equals(Location l) {
		boolean toReturn = false;
		if (l.getXCoordinate() == myX && l.getYCoordinate() == myY) {
			toReturn = true;
		}
		return toReturn;
	}
	
	public Location move(int up, int left, int down, int right) {
		Location l = null;
		int x = myX - left + right;
		int y = myY + up - down;
		//System.out.println("x: " + x);
		//System.out.println("y: " + y);
		if((myX - left + right >= 1 || myX - left + right <= 8 ) && (myY + up - down >= 1 || myY + up - down <= 8)) {
			l = new Location(myX - left + right, myY + up - down);
		}
		return l;
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
	
	public static Location getBoardLocation(Game g, Location l) {
		Location[][] board = g.getBoard();
		int x = l.getXCoordinate();
		int y = l.getYCoordinate();
		return board[y-1][x-1];
	}
	
	public String toString() {
		return myAlgebraicLocation;
	}

}
