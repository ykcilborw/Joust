package com.wroblicky.andrew.joust.game.subset.qualifiable;


public enum Scope implements Qualifiable {
	
	ALL("all"),
	NOTHING("nothing"),
	ACTIVE("active"),
	DECEASED("deceased"),
	BLACK_ACTIVE("black_active"),
	WHITE_ACTIVE("white_active");
	
	private String scope;
	
	Scope(String scope) {
		this.scope = scope;
	}
	
	public String getScope() {
		return this.scope;
	}
	
	public Qualifiable getQualification() {
		return this;
	}
	
	@Override
	public String toString() {
		return this.scope;
	}
}