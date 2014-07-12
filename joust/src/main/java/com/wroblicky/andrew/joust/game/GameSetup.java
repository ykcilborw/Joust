package com.wroblicky.andrew.joust.game;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.wroblicky.andrew.joust.Util;
import com.wroblicky.andrew.joust.game.board.ChessBoard;
import com.wroblicky.andrew.joust.game.board.Location;
import com.wroblicky.andrew.joust.game.chesspiece.Bishop;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.game.chesspiece.King;
import com.wroblicky.andrew.joust.game.chesspiece.Knight;
import com.wroblicky.andrew.joust.game.chesspiece.Pawn;
import com.wroblicky.andrew.joust.game.chesspiece.Queen;
import com.wroblicky.andrew.joust.game.chesspiece.Rook;
import com.wroblicky.andrew.joust.game.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.game.move.Turn;
import com.wroblicky.andrew.joust.game.subset.ChessPieceSubsetManager;

/**
 * Manages the state of a chess gameManager
 * 
 * @author Andrew Wroblicky
 *
 */
public final class GameSetup {
	
	public static GameManager setupDefaultGame() {
		// Load initial default initial configuration into gameManager
		Set<ChessPiece> actives = new HashSet<ChessPiece>();
		ChessBoard chessBoard = new ChessBoard();

		// create relevant chess pieces
		Pawn wp1 = new Pawn(Allegiance.WHITE, 1, chessBoard);
		Pawn wp2 = new Pawn(Allegiance.WHITE, 2, chessBoard);
		Pawn wp3 = new Pawn(Allegiance.WHITE, 3, chessBoard);
		Pawn wp4 = new Pawn(Allegiance.WHITE, 4, chessBoard);
		Pawn wp5 = new Pawn(Allegiance.WHITE, 5, chessBoard);
		Pawn wp6 = new Pawn(Allegiance.WHITE, 6, chessBoard);
		Pawn wp7 = new Pawn(Allegiance.WHITE, 7, chessBoard);
		Pawn wp8 = new Pawn(Allegiance.WHITE, 8, chessBoard);
		Pawn bp1 = new Pawn(Allegiance.BLACK, 1, chessBoard);
		Pawn bp2 = new Pawn(Allegiance.BLACK, 2, chessBoard);
		Pawn bp3 = new Pawn(Allegiance.BLACK, 3, chessBoard);
		Pawn bp4 = new Pawn(Allegiance.BLACK, 4, chessBoard);
		Pawn bp5 = new Pawn(Allegiance.BLACK, 5, chessBoard);
		Pawn bp6 = new Pawn(Allegiance.BLACK, 6, chessBoard);
		Pawn bp7 = new Pawn(Allegiance.BLACK, 7, chessBoard);
		Pawn bp8 = new Pawn(Allegiance.BLACK, 8, chessBoard);
		Rook wc1 = new Rook(Allegiance.WHITE, 1, chessBoard);
		Rook wc2 = new Rook(Allegiance.WHITE, 2, chessBoard);
		Knight wh1 = new Knight(Allegiance.WHITE, 1, chessBoard);
		Knight wh2 = new Knight(Allegiance.WHITE, 2, chessBoard);
		Bishop wb1 = new Bishop(Allegiance.WHITE, 1, chessBoard);
		Bishop wb2 = new Bishop(Allegiance.WHITE, 2, chessBoard);
		Queen wq1 = new Queen(Allegiance.WHITE, 1, chessBoard);
		King wk1 = new King(Allegiance.WHITE, 1, chessBoard);
		Rook bc1 = new Rook(Allegiance.BLACK, 1, chessBoard);
		Rook bc2 = new Rook(Allegiance.BLACK, 2, chessBoard);
		Knight bh1 = new Knight(Allegiance.BLACK, 1, chessBoard);
		Knight bh2 = new Knight(Allegiance.BLACK, 2, chessBoard);
		Bishop bb1 = new Bishop(Allegiance.BLACK, 1, chessBoard);
		Bishop bb2 = new Bishop(Allegiance.BLACK, 2, chessBoard);
		Queen bq1 = new Queen(Allegiance.BLACK, 1, chessBoard);
		King bk1 = new King(Allegiance.BLACK, 1, chessBoard);
		
		// add pieces to actives
		
		// white pawns
		actives.add(wp1);
		actives.add(wp2);
		actives.add(wp3);
		actives.add(wp4);
		actives.add(wp5);
		actives.add(wp6);
		actives.add(wp7);
		actives.add(wp8);
		
		// black pawns
		actives.add(bp1);
		actives.add(bp2);
		actives.add(bp3);
		actives.add(bp4);
		actives.add(bp5);
		actives.add(bp6);
		actives.add(bp7);
		actives.add(bp8);
		
		// white castles
		actives.add(wc1);
		actives.add(wc2);
		
		// black castles
		actives.add(bc1);
		actives.add(bc2);
		
		// white knights
		actives.add(wh1);
		actives.add(wh2);
		
		// black knights
		actives.add(bh1);
		actives.add(bh2);
		
		// white bishops
		actives.add(wb1);
		actives.add(wb2);
		
		// black bishops
		actives.add(bb1);
		actives.add(bb2);
		
		// queens
		actives.add(bq1);
		actives.add(wq1);
		
		// kings
		actives.add(bk1);
		actives.add(wk1);
		
		// white pawns
		chessBoard.addChessPiece(wp1, chessBoard.getLocation("a2"));
		chessBoard.addChessPiece(wp2, chessBoard.getLocation("b2"));
		chessBoard.addChessPiece(wp3, chessBoard.getLocation("c2"));
		chessBoard.addChessPiece(wp4, chessBoard.getLocation("d2"));
		chessBoard.addChessPiece(wp5, chessBoard.getLocation("e2"));
		chessBoard.addChessPiece(wp6, chessBoard.getLocation("f2"));
		chessBoard.addChessPiece(wp7, chessBoard.getLocation("g2"));
		chessBoard.addChessPiece(wp8, chessBoard.getLocation("h2"));
		
		// white royals
		chessBoard.addChessPiece(wc1, chessBoard.getLocation("a1"));
		chessBoard.addChessPiece(wh1, chessBoard.getLocation("b1"));
		chessBoard.addChessPiece(wb1, chessBoard.getLocation("c1"));
		chessBoard.addChessPiece(wq1, chessBoard.getLocation("d1"));
		chessBoard.addChessPiece(wk1, chessBoard.getLocation("e1"));
		chessBoard.addChessPiece(wb2, chessBoard.getLocation("f1"));
		chessBoard.addChessPiece(wh2, chessBoard.getLocation("g1"));
		chessBoard.addChessPiece(wc2, chessBoard.getLocation("h1"));
		
		// black pawns
		chessBoard.addChessPiece(bp1, chessBoard.getLocation("a7"));
		chessBoard.addChessPiece(bp2, chessBoard.getLocation("b7"));
		chessBoard.addChessPiece(bp3, chessBoard.getLocation("c7"));
		chessBoard.addChessPiece(bp4, chessBoard.getLocation("d7"));
		chessBoard.addChessPiece(bp5, chessBoard.getLocation("e7"));
		chessBoard.addChessPiece(bp6, chessBoard.getLocation("f7"));
		chessBoard.addChessPiece(bp7, chessBoard.getLocation("g7"));
		chessBoard.addChessPiece(bp8, chessBoard.getLocation("h7"));
		
		// black royals
		chessBoard.addChessPiece(bc1, chessBoard.getLocation("a8"));
		chessBoard.addChessPiece(bh1, chessBoard.getLocation("b8"));
		chessBoard.addChessPiece(bb1, chessBoard.getLocation("c8"));
		chessBoard.addChessPiece(bq1, chessBoard.getLocation("d8"));
		chessBoard.addChessPiece(bk1, chessBoard.getLocation("e8"));
		chessBoard.addChessPiece(bb2, chessBoard.getLocation("f8"));
		chessBoard.addChessPiece(bh2, chessBoard.getLocation("g8"));
		chessBoard.addChessPiece(bc2, chessBoard.getLocation("h8"));
	
		Game game = new Game(chessBoard, new ArrayList<Turn>(), new ChessPieceSubsetManager(actives));
		GameManager gameManager = new GameManager(game);
		
		return gameManager;
	}

	public static GameManager setupSpecialLayout(String initialConfig) {
		GameManager gameManager = null;
		HashMap<Location, ChessPiece> map = new HashMap<Location, ChessPiece>();
		HashSet<ChessPiece> actives = new HashSet<ChessPiece>();
		HashSet<ChessPiece> blackActives = new HashSet<ChessPiece>();
		HashSet<ChessPiece> whiteActives = new HashSet<ChessPiece>();
		HashMap<String, Set<ChessPiece>> lookup = new HashMap<String, Set<ChessPiece>>();
		HashSet<ChessPiece> pawns = new HashSet<ChessPiece>();
		HashSet<ChessPiece> pawns2 = new HashSet<ChessPiece>();
		HashSet<ChessPiece> rooks = new HashSet<ChessPiece>();
		HashSet<ChessPiece> rooks2 = new HashSet<ChessPiece>();
		HashSet<ChessPiece> knights = new HashSet<ChessPiece>();
		HashSet<ChessPiece> knights2 = new HashSet<ChessPiece>();
		HashSet<ChessPiece> bishops = new HashSet<ChessPiece>();
		HashSet<ChessPiece> bishops2 = new HashSet<ChessPiece>();
		HashSet<ChessPiece> queens = new HashSet<ChessPiece>();
		HashSet<ChessPiece> queens2 = new HashSet<ChessPiece>();
		HashSet<ChessPiece> kings = new HashSet<ChessPiece>();
		HashSet<ChessPiece> kings2 = new HashSet<ChessPiece>();
		
		HashSet<ChessPiece> allPawns = new HashSet<ChessPiece>();
		HashSet<ChessPiece> allRooks = new HashSet<ChessPiece>();
		HashSet<ChessPiece> allKnights = new HashSet<ChessPiece>();
		HashSet<ChessPiece> allBishops = new HashSet<ChessPiece>();
		HashSet<ChessPiece> allQueens = new HashSet<ChessPiece>();
		HashSet<ChessPiece> allKings = new HashSet<ChessPiece>();
		
		Location[][] board = new Location[8][8];
		ChessBoard chessBoard = new ChessBoard(board);
		Location a1 = new Location("a1");
		board[0][0] = a1;
		Location b1 = new Location("b1");
		board[0][1] = b1;
		Location c1 = new Location("c1");
		board[0][2] = c1;
		Location d1 = new Location("d1");
		board[0][3] = d1;
		Location e1 = new Location("e1");
		board[0][4] = e1;
		Location f1 = new Location("f1");
		board[0][5] = f1;
		Location g1 = new Location("g1");
		board[0][6] = g1;
		Location h1 = new Location("h1");
		board[0][7] = h1;
		
		Location a2 = new Location("a2");
		board[1][0] = a2;
		Location b2 = new Location("b2");
		board[1][1] = b2;
		Location c2 = new Location("c2");
		board[1][2] = c2;
		Location d2 = new Location("d2");
		board[1][3] = d2;
		Location e2 = new Location("e2");
		board[1][4] = e2;
		Location f2 = new Location("f2");
		board[1][5] = f2;
		Location g2 = new Location("g2");
		board[1][6] = g2;
		Location h2 = new Location("h2");
		board[1][7] = h2;
		
		Location a3 = new Location("a3");
		board[2][0] = a3;
		Location b3 = new Location("b3");
		board[2][1] = b3;
		Location c3 = new Location("c3");
		board[2][2] = c3;
		Location d3 = new Location("d3");
		board[2][3] = d3;
		Location e3 = new Location("e3");
		board[2][4] = e3;
		Location f3 = new Location("f3");
		board[2][5] = f3;
		Location g3 = new Location("g3");
		board[2][6] = g3;
		Location h3 = new Location("h3");
		board[2][7] = h3;
		
		Location a4 = new Location("a4");
		board[3][0] = a4;
		Location b4 = new Location("b4");
		board[3][1] = b4;
		Location c4 = new Location("c4");
		board[3][2] = c4;
		Location d4 = new Location("d4");
		board[3][3] = d4;
		Location e4 = new Location("e4");
		board[3][4] = e4;
		Location f4 = new Location("f4");
		board[3][5] = f4;
		Location g4 = new Location("g4");
		board[3][6] = g4;
		Location h4 = new Location("h4");
		board[3][7] = h4;
		
		Location a5 = new Location("a5");
		board[4][0] = a5;
		Location b5 = new Location("b5");
		board[4][1] = b5;
		Location c5 = new Location("c5");
		board[4][2] = c5;
		Location d5 = new Location("d5");
		board[4][3] = d5;
		Location e5 = new Location("e5");
		board[4][4] = e5;
		Location f5 = new Location("f5");
		board[4][5] = f5;
		Location g5 = new Location("g5");
		board[4][6] = g5;
		Location h5 = new Location("h5");
		board[4][7] = h5;
		
		Location a6 = new Location("a6");
		board[5][0] = a6;
		Location b6 = new Location("b6");
		board[5][1] = b6;
		Location c6 = new Location("c6");
		board[5][2] = c6;
		Location d6 = new Location("d6");
		board[5][3] = d6;
		Location e6 = new Location("e6");
		board[5][4] = e6;
		Location f6 = new Location("f6");
		board[5][5] = f6;
		Location g6 = new Location("g6");
		board[5][6] = g6;
		Location h6 = new Location("h6");
		board[5][7] = h6;
		
		Location a7 = new Location("a7");
		board[6][0] = a7;
		Location b7 = new Location("b7");
		board[6][1] = b7;
		Location c7 = new Location("c7");
		board[6][2] = c7;
		Location d7 = new Location("d7");
		board[6][3] = d7;
		Location e7 = new Location("e7");
		board[6][4] = e7;
		Location f7 = new Location("f7");
		board[6][5] = f7;
		Location g7 = new Location("g7");
		board[6][6] = g7;
		Location h7 = new Location("h7");
		board[6][7] = h7;
		
		Location a8 = new Location("a8");
		board[7][0] = a8;
		Location b8 = new Location("b8");
		board[7][1] = b8;
		Location c8 = new Location("c8");
		board[7][2] = c8;
		Location d8 = new Location("d8");
		board[7][3] = d8;
		Location e8 = new Location("e8");
		board[7][4] = e8;
		Location f8 = new Location("f8");
		board[7][5] = f8;
		Location g8 = new Location("g8");
		board[7][6] = g8;
		Location h8 = new Location("h8");
		board[7][7] = h8;
		try {
			// Open the file that is the first 
			// command line parameter
			FileInputStream fstream = new FileInputStream(initialConfig);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// Print the content on the console
				String piece = strLine.substring(0, 1);
				//System.out.println("text: " + strLine.substring(2, 4));
				Location location = Util.getLocation(strLine.substring(2, 4), board);
				//System.out.println("location2: " + location2);
				int pawnCounter = 1;
				int PawnCounter = 1;
				int rookCounter = 1;
				int RookCounter = 1;
				int knightCounter = 1;
				int KnightCounter = 1;
				int bishopCounter = 1;
				int BishopCounter = 1;
				int queenCounter = 1;
				int QueenCounter = 1;
				int kingCounter = 1;
				int KingCounter = 1;
				if (piece.equals("p")) {
					Pawn p = new Pawn(Allegiance.BLACK, pawnCounter, chessBoard);
					pawnCounter += 1;
					actives.add(p);
					blackActives.add(p);
					map.put(location, p);
					pawns2.add(p);
				} else if(piece.equals("P")) {
					Pawn p = new Pawn(Allegiance.WHITE, PawnCounter, chessBoard);
					PawnCounter += 1;
					actives.add(p);
					whiteActives.add(p);
					map.put(location, p);
					pawns.add(p);
				} else if (piece.equals("R")) {
					Rook c = new Rook(Allegiance.WHITE, RookCounter, chessBoard);
					RookCounter += 1;
					actives.add(c);
					whiteActives.add(c);
					map.put(location, c);
					rooks.add(c);
				} else if (piece.equals("r")) {
					Rook c = new Rook(Allegiance.BLACK, rookCounter, chessBoard);
					rookCounter += 1;
					actives.add(c);
					blackActives.add(c);
					map.put(location,c);
					rooks2.add(c);
				} else if (piece.equals("n")) {
					Knight h = new Knight(Allegiance.BLACK, knightCounter, chessBoard);
					knightCounter += 1;
					actives.add(h);
					blackActives.add(h);
					map.put(location, h);
					knights2.add(h);
				} else if (piece.equals("N")) {
					Knight h = new Knight(Allegiance.WHITE, KnightCounter, chessBoard);
					KnightCounter += 1;
					actives.add(h);
					whiteActives.add(h);
					map.put(location, h);
					knights.add(h);
				} else if (piece.equals("B")) {
					Bishop b = new Bishop(Allegiance.WHITE, BishopCounter, chessBoard);
					BishopCounter += 1;
					actives.add(b);
					whiteActives.add(b);
					map.put(location, b);
					bishops.add(b);
				} else if(piece.equals("b")) {
					Bishop b = new Bishop(Allegiance.BLACK, bishopCounter, chessBoard);
					bishopCounter += 1;
					actives.add(b);
					blackActives.add(b);
					map.put(location, b);
					bishops2.add(b);
				} else if (piece.equals("Q")) {
					Queen q = new Queen(Allegiance.WHITE, QueenCounter, chessBoard);
					QueenCounter += 1;
					actives.add(q);
					whiteActives.add(q);
					map.put(location, q);
					queens.add(q);
				} else if (piece.equals("q")) {
					Queen q = new Queen(Allegiance.BLACK, queenCounter, chessBoard);
					queenCounter += 1;
					actives.add(q);
					blackActives.add(q);
					map.put(location, q);
					queens2.add(q);
				} else if (piece.equals("K")) {
					King k = new King(Allegiance.WHITE, KingCounter, chessBoard);
					KingCounter += 1;
					actives.add(k);
					whiteActives.add(k);
					map.put(location, k);
					kings.add(k);
				} else if (piece.equals("k")) {
					King k = new King(Allegiance.BLACK, kingCounter, chessBoard);
					kingCounter += 1;
					actives.add(k);
					blackActives.add(k);
					map.put(location, k);
					kings2.add(k);
				}
			}
			
			Set<ChessPiece> activesSet = new HashSet<ChessPiece>();
			for (ChessPiece chessPiece : actives) {
				activesSet.add(chessPiece);
			}
			
			allPawns.addAll(pawns);
			allPawns.addAll(pawns2);
			allRooks.addAll(rooks);
			allRooks.addAll(rooks2);
			allKnights.addAll(knights);
			allKnights.addAll(knights2);
			allBishops.addAll(bishops);
			allBishops.addAll(bishops2);
			allQueens.addAll(queens);
			allQueens.addAll(queens2);
			allKings.addAll(kings);
			allKings.addAll(kings2);
			
			
			lookup.put("P", pawns);
			lookup.put("p", pawns2);
			lookup.put("R", rooks);
			lookup.put("r", rooks2);
			lookup.put("N", knights);
			lookup.put("n", knights2);
			lookup.put("B", bishops);
			lookup.put("b", bishops2);
			lookup.put("Q", queens);
			lookup.put("q", queens2);
			lookup.put("K", kings);
			lookup.put("k", kings2);
			lookup.put("g", actives);
			lookup.put("G", actives);
			lookup.put("d", blackActives);
			lookup.put("D", blackActives);
			lookup.put("l", whiteActives);
			lookup.put("L", whiteActives);
			lookup.put("1", allPawns);
			lookup.put("2", allRooks);
			lookup.put("3", allKnights);
			lookup.put("4", allBishops);
			lookup.put("5", allQueens);
			lookup.put("6", allKings);
			Game game = new Game(chessBoard, new ArrayList<Turn>(), new ChessPieceSubsetManager(activesSet));
			gameManager = new GameManager(game);
			//Close the input stream
			in.close();
		} catch (IOException e){//Catch exception if any
			System.err.println("An error occurred trying to open the initialConfig file" + e);
			System.exit(-1);
		}
		return gameManager;
	}
}
