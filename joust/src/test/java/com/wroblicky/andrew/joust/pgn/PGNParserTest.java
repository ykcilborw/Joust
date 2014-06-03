package com.wroblicky.andrew.joust.pgn;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class PGNParserTest {

	@Test
	public void testGetPGNGame() {
		PGNGame game = PGNParser.getPGNGame("src/test/resources/canAttack.pgn");
		ArrayList<String> moves = (ArrayList<String>) game.getMoves();
		
		// validate moves
		assertEquals(moves.get(0), "Ra2");
		assertEquals(moves.get(1), "Qxa5");
		assertEquals(moves.get(2), "Ra3");
		assertEquals(moves.get(3), "1/2-1/2");
		
		// make sure not extra moves
		assertEquals(moves.size(), 4);
		
		// validate pgn metadata
		assertEquals(game.getEvent(), "Fake Event");
		assertEquals(game.getSite(), "Belgrade, Serbia Yugoslavia|JUG");
		assertEquals(game.getDate(), "1992.11.04");
		assertEquals(game.getRound(), "29");
		assertEquals(game.getWhite(), "Bond, James");
		assertEquals(game.getBlack(), "Stark, Ed");
		assertEquals(game.getResult(), "1/2-1/2");
		
		PGNGame game2 = PGNParser.getPGNGame("src/test/resources/pgnpractice1.pgn");
		ArrayList<String> moves2 = (ArrayList<String>) game2.getMoves();
		
		// validate moves
		assertEquals(moves2.get(0), "Nf3");
		assertEquals(moves2.get(1), "Nf6");
		assertEquals(moves2.get(2), "c4");
		assertEquals(moves2.get(3), "g6");
		assertEquals(moves2.get(4), "Nc3");
		assertEquals(moves2.get(5), "Bg7");
		assertEquals(moves2.get(52), "Kh2");
		assertEquals(moves2.get(82), "0-1");
		
		// make sure not extra moves
		assertEquals(moves2.size(), 83);
		
		// validate pgn metadata
		assertEquals(game2.getEvent(), "chp");
		assertEquals(game2.getSite(), "USA");
		assertEquals(game2.getDate(), "1956.??.??");
		assertEquals(game2.getRound(), "?");
		assertEquals(game2.getWhite(), "Byrne, D.");
		assertEquals(game2.getBlack(), "Fischer, R.");
		assertEquals(game2.getResult(), "0-1");
	}
}