package sineSection.spaceRPG.world.rooms;

import java.util.Map;

public abstract class Room {
	private Map<String, Room> exits;
	private final String description;
	private boolean hasPower;
	
	protected Room(String description) {
		this.description = description;
	}
	
	public Boolean hasPower() {
		return hasPower();
	}
	
	public Room exit(String dir) {
		return exits.get(dir);
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getExitString() {
		StringBuilder result = new StringBuilder("Exits: ");
		exits.keySet().forEach((str) -> result.append(str + "; "));
		return result.toString();
	}
	
	@Override
	public String toString() {
	StringBuilder result = new StringBuilder("Room");
	result.append(this.hashCode());
	return result.toString();	
	}
}
