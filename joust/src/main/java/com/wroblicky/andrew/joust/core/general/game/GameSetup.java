package com.wroblicky.andrew.joust.core.general.game;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.wroblicky.andrew.joust.core.board.ChessBoard;
import com.wroblicky.andrew.joust.core.chesspiece.Bishop;
import com.wroblicky.andrew.joust.core.chesspiece.Castle;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece;
import com.wroblicky.andrew.joust.core.chesspiece.ChessPiece.Allegiance;
import com.wroblicky.andrew.joust.core.chesspiece.Horse;
import com.wroblicky.andrew.joust.core.chesspiece.King;
import com.wroblicky.andrew.joust.core.chesspiece.Pawn;
import com.wroblicky.andrew.joust.core.chesspiece.Queen;
import com.wroblicky.andrew.joust.core.general.Game;
import com.wroblicky.andrew.joust.core.general.Location;
import com.wroblicky.andrew.joust.core.general.Util;

/**
 * Manages the state of a chess game
 * 
 * @author Andrew Wroblicky
 *
 */
public class GameSetup {
	
	public static Game setupDefaultGame() {
		// Load initial default initial configuration into game
		HashMap<Location, ChessPiece> map = new HashMap<Location, ChessPiece>();
		ArrayList<ChessPiece> actives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> blackActives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> whiteActives = new ArrayList<ChessPiece>();
		
		Location[][] board = initializeChessBoardLocations();
		ChessBoard chessBoard = new ChessBoard(board);
		
		
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
		Castle wc1 = new Castle(Allegiance.WHITE, 1, chessBoard);
		Castle wc2 = new Castle(Allegiance.WHITE, 2, chessBoard);
		Horse wh1 = new Horse(Allegiance.WHITE, 1, chessBoard);
		Horse wh2 = new Horse(Allegiance.WHITE, 2, chessBoard);
		Bishop wb1 = new Bishop(Allegiance.WHITE, 1, chessBoard);
		Bishop wb2 = new Bishop(Allegiance.WHITE, 2, chessBoard);
		Queen wq1 = new Queen(Allegiance.WHITE, 1, chessBoard);
		King wk1 = new King(Allegiance.WHITE, 2, chessBoard);
		Castle bc1 = new Castle(Allegiance.BLACK, 1, chessBoard);
		Castle bc2 = new Castle(Allegiance.BLACK, 2, chessBoard);
		Horse bh1 = new Horse(Allegiance.BLACK, 1, chessBoard);
		Horse bh2 = new Horse(Allegiance.BLACK, 2, chessBoard);
		Bishop bb1 = new Bishop(Allegiance.BLACK, 1, chessBoard);
		Bishop bb2 = new Bishop(Allegiance.BLACK, 2, chessBoard);
		Queen bq1 = new Queen(Allegiance.BLACK, 1, chessBoard);
		King bk1 = new King(Allegiance.BLACK, 1, chessBoard);
		
		// add pieces to actives
		actives.add(wp1);
		actives.add(wp2);
		actives.add(wp3);
		actives.add(wp4);
		actives.add(wp5);
		actives.add(wp6);
		actives.add(wp7);
		actives.add(wp8);
		actives.add(bp1);
		actives.add(bp2);
		actives.add(bp3);
		actives.add(bp4);
		actives.add(bp5);
		actives.add(bp6);
		actives.add(bp7);
		actives.add(bp8);
		actives.add(wc1);
		actives.add(wc2);
		actives.add(bc1);
		actives.add(bc2);
		actives.add(wh1);
		actives.add(wh2);
		actives.add(bh1);
		actives.add(bh2);
		actives.add(wb1);
		actives.add(wb2);
		actives.add(bb1);
		actives.add(bb2);
		actives.add(bq1);
		actives.add(wq1);
		actives.add(bk1);
		actives.add(wk1);
		
		whiteActives.add(wp1);
		whiteActives.add(wp2);
		whiteActives.add(wp3);
		whiteActives.add(wp4);
		whiteActives.add(wp5);
		whiteActives.add(wp6);
		whiteActives.add(wp7);
		whiteActives.add(wp8);
		blackActives.add(bp1);
		blackActives.add(bp2);
		blackActives.add(bp3);
		blackActives.add(bp4);
		blackActives.add(bp5);
		blackActives.add(bp6);
		blackActives.add(bp7);
		blackActives.add(bp8);
		whiteActives.add(wc1);
		whiteActives.add(wc2);
		blackActives.add(bc1);
		blackActives.add(bc2);
		whiteActives.add(wh1);
		whiteActives.add(wh2);
		blackActives.add(bh1);
		blackActives.add(bh2);
		whiteActives.add(wb1);
		whiteActives.add(wb2);
		blackActives.add(bb1);
		blackActives.add(bb2);
		blackActives.add(bq1);
		whiteActives.add(wq1);
		blackActives.add(bk1);
		whiteActives.add(wk1);
		
		// add pieces to hash map
		/*map.put(a2, wp1);
		map.put(b2, wp2);
		map.put(c2, wp3);
		map.put(d2, wp4);
		map.put(e2, wp5);
		map.put(f2, wp6);
		map.put(g2, wp7);
		map.put(h2, wp8);
		map.put(a7, bp1);
		map.put(b7, bp2);
		map.put(c7, bp3);
		map.put(d7, bp4);
		map.put(e7, bp5);
		map.put(f7, bp6);
		map.put(g7, bp7);
		map.put(h7, bp8);
		map.put(a1, wc1);
		map.put(b1, wh1);
		map.put(c1, wb1);
		map.put(d1, wq1);
		map.put(e1, wk1);
		map.put(f1, wb2);
		map.put(g1, wh2);
		map.put(h1, wc2);
		map.put(a8, bc1);
		map.put(b8, bh1);
		map.put(c8, bb1);
		map.put(d8, bq1);
		map.put(e8, bk1);
		map.put(f8, bb2);
		map.put(g8, bh2);
		map.put(h8, bc2); */
		
		
		ArrayList<ChessPiece> allPawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allRooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKnights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allBishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allQueens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKings = new ArrayList<ChessPiece>();
		
		Game game = new Game(0, map, actives, blackActives, whiteActives, board, false, false);
		HashMap<String, ArrayList<ChessPiece>> lookup = new HashMap<String, ArrayList<ChessPiece>>();
		ArrayList<ChessPiece> pawns = new ArrayList<ChessPiece>();
		pawns.add(wp1);
		pawns.add(wp2);
		pawns.add(wp3);
		pawns.add(wp4);
		pawns.add(wp5);
		pawns.add(wp6);
		pawns.add(wp7);
		pawns.add(wp8);
		lookup.put("P", pawns);
		ArrayList<ChessPiece> pawns2 = new ArrayList<ChessPiece>();
		pawns2.add(bp1);
		pawns2.add(bp2);
		pawns2.add(bp3);
		pawns2.add(bp4);
		pawns2.add(bp5);
		pawns2.add(bp6);
		pawns2.add(bp7);
		pawns2.add(bp8);
		lookup.put("p", pawns2);
		ArrayList<ChessPiece> rooks = new ArrayList<ChessPiece>();
		rooks.add(wc1);
		rooks.add(wc2);
		lookup.put("R", rooks);
		ArrayList<ChessPiece> rooks2 = new ArrayList<ChessPiece>();
		rooks2.add(bc1);
		rooks2.add(bc2);
		lookup.put("r", rooks2);
		ArrayList<ChessPiece> knights = new ArrayList<ChessPiece>();
		knights.add(wh1);
		knights.add(wh2);
		lookup.put("N", knights);
		ArrayList<ChessPiece> knights2 = new ArrayList<ChessPiece>();
		knights2.add(bh1);
		knights2.add(bh2);
		lookup.put("n", knights2);
		ArrayList<ChessPiece> bishops = new ArrayList<ChessPiece>();
		bishops.add(wb1);
		bishops.add(wb2);
		lookup.put("B", bishops);
		ArrayList<ChessPiece> bishops2 = new ArrayList<ChessPiece>();
		bishops2.add(bb1);
		bishops2.add(bb2);
		lookup.put("b", bishops2);
		ArrayList<ChessPiece> queens = new ArrayList<ChessPiece>();
		queens.add(wq1);
		lookup.put("Q", queens);
		ArrayList<ChessPiece> queens2 = new ArrayList<ChessPiece>();
		queens2.add(bq1);
		lookup.put("q", queens2);
		ArrayList<ChessPiece> kings = new ArrayList<ChessPiece>();
		kings.add(wk1);
		lookup.put("K", kings);
		ArrayList<ChessPiece> kings2 = new ArrayList<ChessPiece>();
		kings2.add(bk1);
		
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
		game.setStringToCP(lookup);
		return game;
	}

	public static Game setupSpecialLayout(String initialConfig) {
		Game game = null;
		HashMap<Location, ChessPiece> map = new HashMap<Location, ChessPiece>();
		ArrayList<ChessPiece> actives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> blackActives = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> whiteActives = new ArrayList<ChessPiece>();
		HashMap<String, ArrayList<ChessPiece>> lookup = new HashMap<String, ArrayList<ChessPiece>>();
		ArrayList<ChessPiece> pawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> pawns2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> rooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> rooks2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> knights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> knights2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> bishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> bishops2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> queens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> queens2 = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> kings = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> kings2 = new ArrayList<ChessPiece>();
		
		ArrayList<ChessPiece> allPawns = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allRooks = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKnights = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allBishops = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allQueens = new ArrayList<ChessPiece>();
		ArrayList<ChessPiece> allKings = new ArrayList<ChessPiece>();
		
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
					Castle c = new Castle(Allegiance.WHITE, RookCounter, chessBoard);
					RookCounter += 1;
					actives.add(c);
					whiteActives.add(c);
					map.put(location, c);
					rooks.add(c);
				} else if (piece.equals("r")) {
					Castle c = new Castle(Allegiance.BLACK, rookCounter, chessBoard);
					rookCounter += 1;
					actives.add(c);
					blackActives.add(c);
					map.put(location,c);
					rooks2.add(c);
				} else if (piece.equals("n")) {
					Horse h = new Horse(Allegiance.BLACK, knightCounter, chessBoard);
					knightCounter += 1;
					actives.add(h);
					blackActives.add(h);
					map.put(location, h);
					knights2.add(h);
				} else if (piece.equals("N")) {
					Horse h = new Horse(Allegiance.WHITE, KnightCounter, chessBoard);
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
			game = new Game(0, map, actives, blackActives, whiteActives, board, false, false);
			game.setStringToCP(lookup);
			//Close the input stream
			in.close();
		} catch (IOException e){//Catch exception if any
			System.err.println("An error occurred trying to open the initialConfig file" + e);
			System.exit(-1);
		}
		return game;
	}
	
	private static Location[][] initializeChessBoardLocations() {
		//create location objects
		Location[][] board = new Location[8][8];
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
		
		return board;
	}
	
}