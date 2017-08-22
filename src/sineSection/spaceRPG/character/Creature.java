package sineSection.spaceRPG.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.script.Scriptable;
import sineSection.spaceRPG.script.TriConsumer;
import sineSection.spaceRPG.script.TriFunction;
import sineSection.spaceRPG.world.item.Inventory;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.aura.Aura;
import sineSection.spaceRPG.world.map.WorldPos;
import sineSection.util.LogWriter;

/**
 * Abstract class for character
 * 
 * @Author William Black
 */
public abstract class Creature implements Scriptable {
	protected static final int HEALTH_MIN = 0;

	protected final String name; // Name of the character
	// private final ComfortStat warmth;
	protected Stat health;
	protected Map<String, Stat> stats;
	protected List<Aura> auras;
	protected Inventory inventory;
	protected boolean alive;

	public Creature(String name, int hpMax, int invSize) {
		this.name = name;
		stats = new HashMap<>();
		auras = new ArrayList<Aura>();
		health = new Stat(HEALTH_MIN, hpMax);
		health.topOff();
		inventory = new Inventory(invSize);
		alive = true;
	}

	public void addStat(String name, Stat stat) {
		if (stats.containsKey(name)) {
			stats.replace(name, stat);
		} else {
			stats.put(name, stat);
		}
	}

	public int getStatVal(String stat) {
		return stats.get(stat).currentVal();
	}

	/**
	 * Change a status of this character by some amount. SHOULD NOT BE USED FOR
	 * HEALTH
	 * 
	 * @Author William Black
	 * @param stat
	 * @param number
	 * @return if the update was successful or not
	 */
	public boolean addToStat(String stat, int number) {
		return stats.get(stat).increment(number);
	}

	/**
	 * Deal damage to the player by using specifically the HEALTH stat
	 * 
	 * @author geekman9097
	 * @return <code>true</code> if the character is still alive after the
	 *         damage is applied <br>
	 *         <code>false</code> if otherwise
	 */
	public boolean damage(int amt) {
		amt = Math.max(amt, 0); // ensure that we will only deal damage
								// ex, if a low stat causes us to do -3 damage,
								// do 0 instead of healing.
		SpaceRPG.getMaster().writeToGui(name + " takes " + amt + " damage.");
		boolean alive = health.isIncrementAllowed(-amt);
		if (alive) {
			health.increment(-amt);
		} else {
			health.empty();
			die();
		}
		return alive;
	}

	/**
	 * @param amt
	 *            the amount of damage to give.
	 * @return if this <code>Creature</code> will survive the damage.
	 * 
	 * @author Richard Abbott
	 */
	public boolean willSurvive(int amt) {
		return healthAfterDamage(amt) > 0;
	}

	/**
	 * @param amt
	 *            the amount of damage to give.
	 * @return how much health is left after this <code>Creature</code> takes
	 *         damage.
	 * 
	 * @author Richard Abbott
	 */
	public int healthAfterDamage(int amt) {
		amt = Math.abs(amt); // ensure that we will only deal damage
		boolean alive = health.isIncrementAllowed(-amt);
		if (alive) {
			return health.currentVal() - amt;
		} else {
			return 0;
		}
	}

	/**
	 * Restore health points to the player
	 * 
	 * @author geekman9097
	 * @param amt
	 * @return true if the character is now fully healthy
	 */
	public boolean heal(int amt) {
		if (alive) { // Even Rick Can't heal Death
			amt = Math.max(amt, 0); // ensure that we will only heal
			SpaceRPG.getMaster().writeToGui(name + " heals " + amt + " health.");
			amt = health.incrementAllowed(amt);
			if (amt == health.maxVal()) {
				health.topOff();
			} else {
				health.increment(amt);
			}
			SpaceRPG.getMaster().writeToGui(name + " restores " + amt + " Health.");
			return amt == health.maxVal();
		} else {
			return false;
		}
	}

	public int getHealth() {
		return health.currentVal();
	}

	public int getMaxHealth() {
		return health.maxVal();
	}

	public void addToBase(String stat, int incrementNum) {
		stats.get(stat).addToMax(incrementNum);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("Creature:");
		string.append("\n").append(name).append("\n").append("Health: ").append(health).append("\n").append("Stats: ").append("\n");
		stats.forEach((title, value) -> string.append("\t" + title + ": " + value + "\n"));
		return string.toString();
	}

	public String getName() {
		return name;
	}

	public List<Stat> getAllStats() {
		List<Stat> result = new ArrayList<>();
		stats.forEach((name, stat) -> result.add(stat));
		return result;
	}

	public Map<String, Stat> getStatsAsMap() {
		return stats;
	}

	/**
	 * Makes character die
	 * 
	 * @Author William Black
	 */
	public void die() {
		alive = false;
		// TODO make character die (riparoni)
	}

	public boolean isAlive() {
		return alive;
	}
	
	public Inventory getInventory() {
		return inventory;
	}
	
	public void addAura(Aura aura) {
		aura.affect(this);
		auras.add(aura);
	}

	public void addAuras(List<Aura> auras) {
		auras.forEach((aura) -> addAura(aura));
	}
	
	public void removeAura(Aura aura) {
		aura.unaffect(this);
		auras.remove(aura);
	}
	
	public void removeAuras(List<Aura> auras) {
		auras.forEach((aura) -> removeAura(aura));
	}

	public abstract WorldPos getPos(); // TODO Placeholder Method
	
	public boolean useItem(String itemName, List<Creature> targets) {
		List<Item> items = inventory.getItems(itemName);
		if(items.size() > 0) {
			if(items.size() == 1) {
				return items.get(0).use(this, targets);
			} else {
				// TODO: Multiple item selection
			}
		} else {
			SpaceRPG.getMaster().getGui().write(name + " does not have the item: " + itemName);
		}
		return false;
	}
	
	public boolean useItem(String itemName, Creature target) {
		List<Creature> targets = new ArrayList<Creature>();
		targets.add(target);
		return useItem(itemName, targets);
	}
	
	public boolean addItem(Item item) {
		if(inventory.addItem(item)) {
			addAuras(item.getAuras());
			return true;
		}
		return false;
	}
	
	public boolean removeItem(String itemName) {
		List<Item> items = inventory.getItems(itemName);
		if(items.size() > 0) {
			if(items.size() == 1) {
				removeAuras(items.get(0).getAuras());
				return inventory.removeItem(items.get(0));
			} else {
				// TODO: Multiple item selection
			}
		} else {
			SpaceRPG.getMaster().getGui().write(name + " does not have the item: " + itemName);
		}
		return false;
	}

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("name", name);
		ret.put("health", health);
		ret.put("stats", stats);
		ret.put("alive", alive);
		ret.put("inventory", inventory);
		return ret;
	}

	public HashMap<String, Supplier<?>> getScriptSuppliers() {
		HashMap<String, Supplier<?>> ret = new HashMap<>();
		ret.put("getHealth", (Supplier<Integer>) this::getHealth);
		ret.put("getMaxHealth", (Supplier<Integer>) this::getMaxHealth);
		ret.put("toString", (Supplier<String>) this::toString);
		ret.put("getName", (Supplier<String>) this::getName);
		ret.put("getAllStats", (Supplier<List<Stat>>) this::getAllStats);
		ret.put("getStatsAsMap", (Supplier<Map<String, Stat>>) this::getStatsAsMap);
		return ret;
	}

	public HashMap<String, Consumer<?>> getScriptConsumers() {
		HashMap<String, Consumer<?>> ret = new HashMap<>();
		ret.put("log", (Consumer<String>) LogWriter::print);
		ret.put("logErr", (Consumer<String>) LogWriter::printErr);
		return ret;
	}

	public HashMap<String, BiConsumer<?, ?>> getScriptBiConsumers() {
		HashMap<String, BiConsumer<?, ?>> ret = new HashMap<>();
		ret.put("addStat", (BiConsumer<String, Stat>) this::addStat);
		ret.put("addToBase", (BiConsumer<String, Integer>) this::addToBase);
		return ret;
	}

	public HashMap<String, TriConsumer<?, ?, ?>> getScriptTriConsumers() {
		return null;
	}

	public HashMap<String, Function<?, ?>> getScriptFunctions() {
		HashMap<String, Function<?, ?>> ret = new HashMap<>();
		ret.put("getStatVal", (Function<String, Integer>) this::getStatVal);
		ret.put("damage", (Function<Integer, Boolean>) this::damage);
		ret.put("willSurvive", (Function<Integer, Boolean>) this::willSurvive);
		ret.put("healthAfterDamage", (Function<Integer, Integer>) this::healthAfterDamage);
		ret.put("heal", (Function<Integer, Boolean>) this::heal);
		return ret;
	}

	public HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions() {
		HashMap<String, BiFunction<?, ?, ?>> ret = new HashMap<>();
		ret.put("addToStat", (BiFunction<String, Integer, Boolean>) this::addToStat);
		ret.put("useItem", (BiFunction<String, ArrayList<Creature>, Boolean>) this::useItem);
		ret.put("useItem", (BiFunction<String, Creature, Boolean>) this::useItem);
		return ret;
	}

	public HashMap<String, TriFunction<?, ?, ?, ?>> getScriptTriFunctions() {
		return null;
	}

	public HashMap<String, Runnable> getScriptRunnables() {
		HashMap<String, Runnable> ret = new HashMap<>();
		ret.put("die", this::die);
		return ret;
	}
}