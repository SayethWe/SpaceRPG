package sineSection.spaceRPG.character;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.Pos;

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
	private Pos location;
	// TODO add in 'comfort' like warmth/hunger/thirst, and environment checks for damage

	/**
	 * Initializes 'inventory' and 'stats', adds all the 'stats' into the
	 * appropriate HashMap
	 * 
	 * @Author William Black
	 */
	public Player(String name) {
		super(name, BASE_MAX_HEALTH);
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
	 * @Return True if Item is in the players inventory, false if it is not
	 *         found
	 */
	public boolean hasItem(Item item) {
		return inventory.containsValue(item);
	}

	/**
	 * @Author William Black
	 * @Return True if Item was added successfully, False if the Item cannot be
	 *         added to inventory
	 */
	public boolean addItem(Item item) {
		boolean result = inventory.size() < INVENTORY_SIZE;
		if (result) {
			inventory.put(item.getName(), item);

			if (item.hasAuraEffect() == true) {
				item.getAuras().forEach((aura) -> addToStat(aura.getStat(), aura.getAmount()));
			}
		}
		return result;
	}

	public void removeItem(String itemName) {
		Item item = inventory.get(itemName);
		if (item.hasAuraEffect()) {
			item.getAuras().forEach((aura) -> addToStat(aura.getStat(), -aura.getAmount()));
		}
		inventory.remove(itemName);
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
	 * @return <true> if the Item was used successfully
	 */
	public boolean useItem(String itemName, Creature target) {
		Item item = inventory.get(itemName);
		boolean result = item != null;
		if (result) {
			if (inventory.get(itemName).use(target)) {
				removeItem(itemName);
			}
		}
		return result;
	}

	public Set<? extends Object> getAllItems() {
		Set<Item> result = new HashSet<>();
		inventory.forEach((name, item) -> result.add(item));
		return result;
	}
	
	public Pos getPos() {
		return location;
	}
}