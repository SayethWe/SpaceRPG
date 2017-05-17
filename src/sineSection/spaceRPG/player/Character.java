package sineSection.spaceRPG.player;

public abstract class Character {
	private String name;
	
	protected Character(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
