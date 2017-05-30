package sineSection.spaceRPG.world.map.room;

import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;

public abstract class Room {
	private Map<String, Room> exits;
	private final String description;
	private boolean hasPower;
	private Generator<Item> itemGenerator;
	
	protected Room(String description) {
		this.description = description;
		itemGenerator = new Generator<>();
	}
	
	public void addItemType(Class<? extends Item> type) {
		itemGenerator.addType(type);
	}
	
	public Boolean hasPower() {
		return hasPower;
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
