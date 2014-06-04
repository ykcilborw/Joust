package com.wroblicky.andrew.joust.core.game;

import static org.junit.Assert.*;

import org.junit.Test;

import com.wroblicky.andrew.joust.core.general.GameManagerImpl;
import com.wroblicky.andrew.joust.core.general.game.GameSetup;

public class GameSetupTest {

	@Test
	public void testSetupDefaultGame() {
		GameManagerImpl gameManager = GameSetup.setupDefaultGame();
	}

	@Test
	public void testSetupSpecialLayout() {
		fail("Not yet implemented");
	}

}