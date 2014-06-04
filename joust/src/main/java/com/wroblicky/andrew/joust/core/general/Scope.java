package com.wroblicky.andrew.joust.core.general;

public enum Scope implements Qualifiable {
	
	ALL("all"),
	NOTHING("nothing"),
	ACTIVE("active"),
	DECEASED("deceased");
	
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