package joust.core.general;

import joust.core.chesspiece.*;
import joust.core.ui.*;
import java.util.*;

public class Util {
	
	public static String[][] getStringBoard(ArrayList<ChessPiece> actives) {
		String[][] newBoard = new String[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				newBoard[i][j] = "-"; //denotes unoccupied
			}
		}
		for (int k = 0; k < actives.size(); k++) {
			ChessPiece current = actives.get(k);
			int currX = current.getLocation().getXCoordinate();
			int currY = current.getLocation().getYCoordinate();
			newBoard[currX - 1][currY - 1] = current.getMySymbol(); 
		}
		return newBoard;
	}
	
	public static void handleResults(ArrayList<String[][]> boards, ArrayList<Integer> rounds, ChessBoardMatcher jo, String joustProgram) {
		System.out.println("Printing RESULTS: ");
		for (int i = 0; i < boards.size(); i++) {
			int round = rounds.get(i);
			String[][] board = boards.get(i);
			System.out.println("Current Round: " + round);
				for (int j = 7; j > -1; j--) {
					for (int k = 0; k < 8; k++) {
						System.out.print(board[k][j] + " ");
					}
					System.out.println("");
				}
		}
		jo.getGame().printVisitedBoards();
		ChessGameDemo.start(boards, jo, joustProgram, jo.getGame().getCastleMoves());
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
	
	// called by Game when loading special config file
	public static Location getLocation(String id, Location[][] board) {
		int file = Util.fileToNum(id.substring(0, 1)) - 1;
		int rank = Integer.parseInt(id.substring(1, 2)) - 1;
		return board[rank][file];
	}

}
