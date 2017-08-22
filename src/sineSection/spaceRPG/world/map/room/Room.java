package sineSection.spaceRPG.world.map.room;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.Doorway;

public abstract class Room {
	private Map<String,Creature> creatures;
	private final String description;
	private boolean hasPower;
	private Generator<Item> itemGenerator;
	private boolean isPressurized;
	private Map<Direction, Doorway> doors;
	private Map<String, Item> items;
	private boolean visited = false;

	protected Room(String description) {
		this.description = description;
		itemGenerator = new Generator<>();
		creatures = new HashMap<>();
		items = new HashMap<>();
		doors = new HashMap<>();
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
	public abstract void onRoomExit(Player p);

	public String getExitString() {
		StringBuilder result = new StringBuilder("Exits: ");
		doors.keySet().forEach((str) -> result.append(str + "; "));
		return result.toString();
	}

	public String getInfoText() {
		StringBuilder result = new StringBuilder(description);
		result.append("\n")
			.append(getExitString())
			.append("\n")
			.append(getCreatureString());
		return result.toString();
	}
	
	public Doorway addDoor(Direction exit, Doorway door) {
		doors.put(exit,door);
		return door;
	}

	public Set<Direction> getExits() {
		return doors.keySet();
	}
	
	public Doorway getDoor(Direction key) {
		return doors.get(key);
	}
	
	public Creature getCreature(String name) {
		return creatures.get(name);
	}
	
	public Set<String> getCreatures() {
		return creatures.keySet();
	}
	
	public Set<Creature> getRawCreatures() {
		Set<Creature> result;
		result = new HashSet<>(creatures.values());
		return result;
	}
	
	public String getCreatureString() {
		StringBuilder result = new StringBuilder ("Creatures: ");
		getCreatures().forEach((str) -> result.append(str + "; "));
		return result.toString();
	}
	
	public void addCreature(Creature c) {
		creatures.put(c.getName(), c);
	}
	
	public void removeCreature(Creature c) {
		creatures.remove(c.getName());
	}

	public Set<Item> getRawItems() {
		Set<Item> result;
		result = new HashSet<>(items.values());
		return result;
	}
	
	public boolean hasBeenEntered() {
		return visited;
	}
	
	public void setSeen() {
		visited = true;
	}
}
