package sineSection.spaceRPG.world.rooms;

import java.util.Map;

public abstract class Room {
	private Map<String, Room> exits;
	private final String description;
	private boolean hasPower;
	private boolean isPressurized;
	
	protected Room(String description) {
		this.description = description;
	}
	
	public Boolean hasPower() {
		return hasPower;
	}
	
	public void hasPower(Boolean arg){
		hasPower = arg;
	}
	
	public Boolean hasOxygen(){
		return isPressurized;
	}
	
	public void hasOxygen(Boolean arg){
		isPressurized = arg;
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
