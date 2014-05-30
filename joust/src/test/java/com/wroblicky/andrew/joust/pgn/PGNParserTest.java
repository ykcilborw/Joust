package com.wroblicky.andrew.joust.pgn;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class PGNParserTest {

	@Test
	public void testGetGameMoves() {
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
		
		//List<String> moves2 = PGNParser.getPGNGame("src/test/resources/pgnpractice1.pgn").getMoves();
		// TODO
	}
}