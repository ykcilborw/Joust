package joust.core.board;

import joust.core.chesspiece.ChessPiece;
import joust.core.general.Location;
import joust.core.general.move.Move;

public class ChessBoard {
	
	private Location[][] chessBoard;
	
	public ChessBoard(Location[][] chessBoard) {
		this.chessBoard = chessBoard;
	}
	
	public void addChessPiece(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(chessPiece);
	}
	
	public void moveChessPiece(Move move) {
		removeChessPiece(move.getChessPiece(), move.getStart());
		addChessPiece(move.getChessPiece(), move.getDestination());
	}
	
	public Location getLocationByChessPiece(ChessPiece chessPiece) {
		return chessPiece.getLocation();
	}
	
	public ChessPiece getChessPieceByLocation(Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		return chessBoard[x][y].getChessPiece();
	}
	
	public void removeChessPiece(ChessPiece chessPiece, Location location) {
		int x = location.getXCoordinate();
		int y = location.getYCoordinate();
		chessBoard[x][y].setChessPiece(null);
	}
	
	@Deprecated
	public Location[][] getBoard() {
		return chessBoard;
	}

	public void printBoard() {
		for (int j = 7; j > -1; j--) {
			for (int i = 0; i < 8; i++) {
				System.out.print(chessBoard[i][j].getChessPiece().getMySymbol() + " ");
			}
			System.out.println("");
		}
	}
}
