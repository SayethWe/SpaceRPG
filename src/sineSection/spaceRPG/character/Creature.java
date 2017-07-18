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
	private static final int HEALTH_MIN = 0;

	private final String name; // Name of the character
	// private final ComfortStat warmth;
	private Stat health;
	private Map<String, Stat> stats;
	private boolean alive;
	private WorldPos pos;

	public Creature(String name, int hpMax, WorldPos pos) {
		this.name = name;
		this.pos = pos;
		stats = new HashMap<>();
		health = new Stat(HEALTH_MIN, hpMax);
		health.topOff();
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

	public void addAuras(List<Aura> auras) {
		auras.forEach((aura) -> aura.affect(this));
	}

	public abstract boolean hasItem(Item item); // Returns true if item is in
												// the inventory

	public abstract boolean addItem(Item item); // Returns true if item was
												// successfully added to
												// inventory, returns false if
												// the item is unable to be
												// added

	public abstract boolean removeItem(String itemName);

	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("name", name);
		ret.put("health", health);
		ret.put("stats", stats);
		ret.put("alive", alive);
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
		ret.put("hasItem", (Function<Item, Boolean>) this::hasItem);
		ret.put("addItem", (Function<Item, Boolean>) this::addItem);
		ret.put("removeItem", (Function<String, Boolean>) this::removeItem);
		return ret;
	}

	public HashMap<String, BiFunction<?, ?, ?>> getScriptBiFunctions() {
		HashMap<String, BiFunction<?, ?, ?>> ret = new HashMap<>();
		ret.put("addToStat", (BiFunction<String, Integer, Boolean>) this::addToStat);
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

	public WorldPos getPos() {
		return pos;
	}
	
	protected void setPos(WorldPos moveTo) {
		pos = moveTo;
	}
}