package sineSection.spaceRPG.world.rooms;

import java.util.Map;

public class Room {
	Map<String, Room> exits;
	
	public Room exit(String dir) {
		return exits.get(dir);
	}
	
	public String getExits() {
		StringBuilder result = new StringBuilder("Exits: ");
		exits.keySet().forEach((str) -> result.append(str + "; "));
		return result.toString();
	}
}
