package com.wroblicky.andrew.joust.pgn;

import java.util.List;

import com.wroblicky.andrew.joust.core.general.Turn;

/**
 * Represents the information contained in a PGNGame
 * 
 * @author Andrew Wroblicky
 *
 */
public class PGNGame {
	
	private static final String UNKNOWN = "Unknown";
	
	private String event = UNKNOWN;
	private String site = UNKNOWN;
	private String date = UNKNOWN;
	private String round = UNKNOWN;
	private String black = UNKNOWN;
	private String white = UNKNOWN;
	private String result = UNKNOWN;
	private List<Turn> turns;
	
	
	public String getEvent() {
		return event;
	}
	
	public void setEvent(String event) {
		this.event = event;
	}
	
	public String getSite() {
		return site;
	}
	
	public void setSite(String site) {
		this.site = site;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getRound() {
		return round;
	}
	
	public void setRound(String round) {
		this.round = round;
	}
	
	public String getBlack() {
		return black;
	}
	
	public void setBlack(String black) {
		this.black = black;
	}
	
	public String getWhite() {
		return white;
	}
	
	public void setWhite(String white) {
		this.white = white;
	}
	
	public String getResult() {
		return result;
	}
	
	public void setResult(String result) {
		this.result = result;
	}
	
	public List<Turn> getTurns() {
		return turns;
	}

	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}
}