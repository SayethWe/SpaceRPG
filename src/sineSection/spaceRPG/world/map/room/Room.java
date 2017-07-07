package sineSection.spaceRPG.world.map.room;

import java.util.HashSet;
import java.util.Set;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Direction;

public abstract class Room {
	private Set<Direction> exits;
	private final String description;
	private boolean hasPower;
	private Generator<Item> itemGenerator;
	private boolean isPressurized;

	protected Room(String description) {
		this.description = description;
		itemGenerator = new Generator<>();
		exits = new HashSet<>();
	}

	public void addItemType(Class<? extends Item> type) {
		itemGenerator.addType(type);
	}

	public Boolean hasPower() {
		return hasPower;
	}

	public void hasPower(Boolean arg) {
		hasPower = arg;
	}

	public Boolean hasOxygen() {
		return isPressurized;
	}

	public void hasOxygen(Boolean arg) {
		isPressurized = arg;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * run when a room is entered. Does room specific actions.
	 * 
	 * @param p
	 */
	public abstract void onRoomEnter(Player p);

	public String getExitString() {
		StringBuilder result = new StringBuilder("Exits: ");
		exits.forEach((str) -> result.append(str + "; "));
		return result.toString();
	}

//	@Override
//	public String toString() {
//		StringBuilder result = new StringBuilder("Room");
//		result.append(this.hashCode());
//		return result.toString();
//	}
	
	public void addExit(Direction exit) {
		exits.add(exit);
	}

	public Set<Direction> getExits() {
		return exits;
	}
}
