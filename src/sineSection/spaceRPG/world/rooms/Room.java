package sineSection.spaceRPG.world.rooms;

import java.util.Map;

public abstract class Room {
	protected Map<String, Room> exits;
	private final String description;
	
	protected Room(String description) {
		this.description = description;
	}
	
	public Room exit(String dir) {
		return exits.get(dir);
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getExits() {
		StringBuilder result = new StringBuilder("Exits: ");
		exits.keySet().forEach((str) -> result.append(str + "; "));
		return result.toString();
	}
}
