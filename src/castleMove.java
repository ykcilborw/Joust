
public class castleMove {
	private int myRound;
	private String myColor;
	private String kingorQueen;
	
	
	public castleMove(int round, String color, String kingorQueen) {
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
