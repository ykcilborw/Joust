import java.util.ArrayList;
import java.util.HashMap;


public class Queen extends ChessPiece {
	private Location myLocation;
	private String myColor;
	private boolean isAlive;
	private int myID;
	
	public Queen(Location l, String c, int id) {
		myLocation = l;
		myColor = c;
		isAlive = true;
		myID = id;
	}
	
	public Location getLocation() {
		return myLocation;
	}
	
	public String getColor() {
		return myColor;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public int getID() {
		return myID;
	}
	
	public String getmyType() {
		return "Queen";
	}
	
	public String getmySymbol() {
		if (myColor.equals("b")) {
			return "q";
		} else {
			return "Q";
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
	
	public String getRelRank() {
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
	
	public ArrayList<Location> getPossibleMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = myLocation.getmyX();
		int y = myLocation.getmyY();
		int nextX = x;
		int nextY = y;
		boolean stillValid = true;
		while (stillValid) {
			nextX = nextX + 1;
			nextY = nextY + 1;
			Location l = new Location(nextX, nextY);
			
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
			} else if ((checkAvailability(g, l).equals("enemy")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
				possibles.add(l);
				stillValid = false;
			} else {
				stillValid = false;
			}
		}
		return possibles;
	}
	
	public ArrayList<Location> getDefenseMoves(Game g){
		ArrayList<Location> possibles = new ArrayList<Location>();
		int x = myLocation.getmyX();
		int y = myLocation.getmyY();
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
			String result = checkAvailability(g, l);
			//System.out.println("queen defense available: " + checkAvailability(g, l));
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
			String result = checkAvailability(g, l);
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
			String result = checkAvailability(g, l);
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
			String result = checkAvailability(g, l);
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
			String result = checkAvailability(g, l);
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
			String result = checkAvailability(g, l);
			if ((checkAvailability(g, l).equals("unoccupied")) && nextX < 9 && nextX > 0 && nextY > 0 && nextY < 9) {
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
			String result = checkAvailability(g, l);
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
			String result = checkAvailability(g, l);
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
	
	public boolean canReach(Game g, Location l) {
		ArrayList<Location> possibles = this.getPossibleMoves(g);
		boolean toReturn = false;
		for (int i = 0; i < possibles.size(); i++) {
			//System.out.println("location: " + possibles.get(i).getmyX() + ", " + possibles.get(i).getmyY());
			if (possibles.get(i).equals(l)) {
				toReturn = true;
			}
		}
		return toReturn;
	}
	
	public boolean canDefend(Game g, Location l) {
		ArrayList<Location> possibles = this.getDefenseMoves(g);
		//System.out.println("can defend size queen: " + possibles.size());
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
		if (myID == b.getID() && b.getmyType().equals(this.getmyType())) {
			toReturn = true;
		}
		return toReturn;
	}


}
