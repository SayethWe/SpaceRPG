package sineSection.spaceRPG.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.WorldPos;
import sineSection.spaceRPG.world.map.room.Room;

/**
 * @Author William Black A class that stores the stats and inventory of a player
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

	private Map<String, Item> inventory;
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
		super(name, BASE_MAX_HEALTH, true, startingPos);
		inventory = new HashMap<>();
		addStat(RESISTANCE, new Stat(RESISTANCE_MIN, (int) (Math.random() * RESISTANCE_MAX_POSSIBLE + 1)));
		addStat(INTELLECT, new Stat(INTELLECT_MIN, (int) (Math.random() * INTELLECT_MAX_POSSIBLE + 1)));
		addStat(POWER, new Stat(POWER_MIN, (int) (Math.random() * POWER_MAX_POSSIBLE + 1)));
	}

	@Override
	public boolean damage(int amt) {
		return super.damage(amt - getStatVal(RESISTANCE));
	}

	/**
	 * @Author William Black
	 * @Return <code>True</code> if Item is in the players inventory, 
	 * 			<code>false</code> if it is not found
	 */
	public boolean hasItem(Item item) {
		return inventory.containsValue(item);
	}
	
	/**
	 * @Author Richard Abbott
	 * @Return True if Item is in the players inventory, false if it is not
	 *         found
	 */
	public boolean hasItem(String itemRefID) {
		for(String i : inventory.keySet()) {
			Item item = inventory.get(i);
			System.out.println(itemRefID + ", " + item.getRefID().substring(0, item.getRefID().lastIndexOf('-') - 1));
			if(item.getRefID().substring(0, item.getRefID().lastIndexOf('-') - 1).equalsIgnoreCase(itemRefID)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Author William Black
	 * @Return True if Item was added successfully, False if the Item cannot be
	 *         added to inventory
	 */
	public boolean addItem(Item item) {
		boolean result = inventory.size() < INVENTORY_SIZE;
		if (result) {
			List<Item> dupeItems = getItemsByRefID(item.getRefID());
			inventory.put(item.getAlias() + ((dupeItems.size() > 0) ? 0 : dupeItems.size() - 1), item);
			if (item.hasAuraEffect()) {
				item.getAuras().forEach((aura) -> aura.affect(this));
			}
		}
		return result;
	}

	public boolean removeItem(String itemRefID) {
		Item item = inventory.get(itemRefID);
		if (item != null) {
			if (item.hasAuraEffect()) {
				item.getAuras().forEach((aura) -> aura.unaffect(this));
			}
			inventory.remove(itemRefID);
			return true;
		} else {
			return false;
		}
	}

	public boolean removeItem(Item item) {
		if (inventory.containsValue(item)) {
			if (item.hasAuraEffect()) {
				item.getAuras().forEach((aura) -> aura.unaffect(this));
			}
			inventory.remove(inventory);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Author William Black
	 * @return A String set containing the inventory of the player
	 */
	public Set<String> getInventory() {
		return inventory.keySet();
	}

	/**
	 * Checks to see if item is in inventory, if the item is not permanent and
	 * out of uses, then delete it from the inventory
	 * 
	 * @Author William Black
	 * @param itemName
	 * @param target
	 * @return <true> if the Item was used successfully
	 */
	public boolean useItem(String itemRefID, ArrayList<Creature> target) {
		Item item = inventory.get(itemRefID);
		boolean result = item != null;
		if (result) {
			if (item.use(this, target)) {
				removeItem(itemRefID);
			}
		}
		return result;
	}

	/**
	 * Checks to see if item is in inventory, if the item is not permanent and
	 * out of uses, then delete it from the inventory
	 * 
	 * @Author William Black
	 * @param itemName
	 * @param target
	 * @return <true> if the Item was used successfully
	 */
	public boolean useItem(String itemRefID, Creature target) {
		Item item = inventory.get(itemRefID);
		boolean result = item != null;
		if (result) {
			if (item.use(this, target)) {
				removeItem(itemRefID);
			}
		}
		return result;
	}
	
	public Item getItemByAlias(String itemAlias) {
		for(String i : inventory.keySet()) {
			if(i.equalsIgnoreCase(itemAlias)) {
				return inventory.get(i);
			}
		}
		return null;
	}

	public List<Item> getItemsByRefID(String itemRefID) {
		List<Item> result = new ArrayList<>();
		for(String i : inventory.keySet()) {
			if(i.substring(0, i.lastIndexOf('-') - 1).equalsIgnoreCase(itemRefID)) {
				result.add(inventory.get(i));
			}
		}
		return result;
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

	public List<Item> getAllItems() {
		List<Item> result = new ArrayList<>();
		inventory.forEach((name, item) -> result.add(item));
		return result;
	}

	public void move(Direction dir) {
		SpaceRPG.getMaster().writeToGui(getName() + " Moved " + dir.getCall());
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
		SpaceRPG.getMaster().getGui().updateMap();
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
		ret.put("useItem", (BiFunction<String, Creature, Boolean>) this::useItem);
		ret.put("useItem", (BiFunction<String, ArrayList<Creature>, Boolean>) this::useItem);
		return ret;
	}
}