package sineSection.spaceRPG.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.item.Inventory;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.WorldPos;
import sineSection.spaceRPG.world.map.room.Room;

/**
 * @Author William Black A class that stores info about a player
 *         character
 */
public class Player extends Creature {
	private static final int BASE_MAX_HEALTH = 20;
	private static final int INVENTORY_SIZE = 20;

	public final static String INTELLECT = "Intelligence";
	public final static String POWER = "Strength";
	public final static String RESISTANCE = "Defense";

	private static final int RESISTANCE_MAX_POSSIBLE = 20;
	private static final int INTELLECT_MAX_POSSIBLE = 10;
	private static final int POWER_MAX_POSSIBLE = 10;

	private static final int RESISTANCE_MIN = 0;
	private static final int INTELLECT_MIN = 1;
	private static final int POWER_MIN = 1;

	private WorldPos location;
	private Direction lastDirectionTraveled;
	private boolean hasHud = false;
	
	// private Map<String, ComfortStat> comfortStats;
	// TODO add in 'comfort' like warmth/hunger/thirst, and environment checks
	// for damage

	/**
	 * Construct a new Player object with specific starting Positions and a name.
	 * 
	 * @Author William Black
	 */
	public Player(String name, WorldPos startingPos) {
		super(name, BASE_MAX_HEALTH, INVENTORY_SIZE, true, startingPos);
		location = startingPos;
		addStat(RESISTANCE, new Stat(RESISTANCE_MIN, (int) (Math.random() * RESISTANCE_MAX_POSSIBLE + 1)));
		addStat(INTELLECT, new Stat(INTELLECT_MIN, (int) (Math.random() * INTELLECT_MAX_POSSIBLE + 1)));
		addStat(POWER, new Stat(POWER_MIN, (int) (Math.random() * POWER_MAX_POSSIBLE + 1)));
	}

	public boolean damage(int amt) {
		return super.damage(amt - getStatVal(RESISTANCE));
	}
	

	/**
	 * @Author William Black
	 * @Return True if Item was added successfully, False if the Item cannot be
	 *         added to inventory
	 */
	public boolean addItem(Item item) {
		boolean result = inventory.getSize() < INVENTORY_SIZE;
		if (result) {
			inventory.addItem(item);
		}
		return result;
	}

	public boolean removeItem(String itemName) {
		Item item = inventory.getItemsWithName(itemName).get(0);
		if (item != null) {
			if (item.hasAuraEffect()) {
				item.getAuras().forEach((aura) -> aura.unaffect(this));
			}
			inventory.removeItem(item);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeItem(Item item) {
		if (inventory.hasItem(item)) {
			if (item.hasAuraEffect()) {
				item.getAuras().forEach((aura) -> aura.unaffect(this));
			}
			inventory.removeItem(item);
			return true;
		} else {
			return false;
		}
	}

	public void setHasHud(boolean hasHud) {
		if (hasHud != this.hasHud) {
			this.hasHud = hasHud;
			SpaceRPG.getMaster().getGui().updateHud();
		}
	}

	public boolean hasHud() {
		return hasHud;
	}

	public WorldPos getPos() {
		return location;
	}
	
	public Inventory getInventory() {
		return inventory;
	}

	public void move(Direction dir) {
		SpaceRPG.getMaster().writeToGui(getName() + " moved " + dir.getCall());
		lastDirectionTraveled = dir;
		Room from = SpaceRPG.getMaster().getWorld().getRoomAt(getPos());
		from.onRoomExit(this);
		from.removeCreature(this);
		
		setPos(new WorldPos(getPos().getNode(), dir.affectPos(getPos().getRoom())));
		
		Room to = SpaceRPG.getMaster().getWorld().getRoomAt(getPos());
		to.onRoomEnter(this);
		to.addCreature(this);
		to.setSeen();
		
		SpaceRPG.getMaster().writeToGui(getPos().getRoom());
	}

	public Direction getLastDir() {
		return lastDirectionTraveled;
	}

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = super.getScriptVars();
		ret.put("getPos()", getPos());
		return ret;
	}

	public HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions() {
		HashMap<String, BiFunction<?, ?, ?>> ret = super.getScriptBiFunctions();
		return ret;
	}
}