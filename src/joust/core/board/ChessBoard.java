package joust.core.board;

import joust.core.chesspiece.Bishop;
import joust.core.chesspiece.ChessPiece;
import joust.core.chesspiece.ChessPiece.Allegiance;
import joust.core.general.Location;
import joust.core.general.move.Move;

public class ChessBoard {
	
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		// TODO
	}
	
	
	public void moveChessPiece(Move move) {
		// TODO
	}
	
	public Location getLocationByChessPiece(ChessPiece chessPiece) {
		return chessPiece.getLocation();
	}
	
	public ChessPiece getChessPieceByLocation(Location l) {
		return new Bishop(Allegiance.WHITE, 20, this);
		// TODO
	}
	
	public void removeChessPiece(ChessPiece chessPiece) {
		// TODO
	}
	
	public Location[][] getBoard() {
		return new Location[8][8];
		// TODO
	}

	public void printBoard() {
		// TODO
	}
}
