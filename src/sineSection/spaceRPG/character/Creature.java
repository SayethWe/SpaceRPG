package sineSection.spaceRPG.character;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import sineSection.spaceRPG.script.Scriptable;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.effect.Aura;

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
	boolean alive;

	public Creature(String name, int hpMax) {
		this.name = name;
		stats = new HashMap<>();
		health = new Stat(HEALTH_MIN, hpMax);
		health.topOff();
		alive = true;
	}

	public void addStat(String name, Stat stat) {
		if(stats.containsKey(name)) {
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
		amt = Math.abs(amt); // ensure that we will only deal damage
		boolean alive = health.incrementAllowed(-amt);
		if (alive) {
			health.increment(-amt);
		} else {
			health.empty();
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
		boolean alive = health.incrementAllowed(-amt);
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
		if (alive) { //Even Rick Can't heal Death
			amt = Math.abs(amt); // ensure that we will only heal
			boolean fullHeal = !health.incrementAllowed(amt);
			if (fullHeal) {
				health.topOff();
			} else {
				health.increment(amt);
			}
			return fullHeal;
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
		stats.forEach((title, value) -> string.append(title + ": " + value + "\n"));
		return string.toString();
	}

	public String getName() {
		return name;
	}

	public Set<? extends Object> getAllStats() {
		Set<Stat> result = new HashSet<>();
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
	public void makeCharacterDie() {
		alive = false;
		// TODO make character die (riparoni)
	}

	public void addAuras(Set<Aura> auras) {
		auras.forEach((aura) -> addToStat(aura.getStat(), aura.getAmount()));
	}

	public abstract boolean hasItem(Item item); // Returns true if item is in
												// the inventory

	public abstract boolean addItem(Item item); // Returns true if item was
												// successfully added to
												// inventory, returns false if
												// the item is unable to be
												// added
	
	public HashMap<String, Object> getScriptVars() {
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("name", name);
		ret.put("health", health);
		ret.put("stats", stats);
		ret.put("alive", alive);
		return ret;
	}

	public ArrayList<Method> getScriptFunctions() {
		ArrayList<Method> ret = new ArrayList<>();
		try {
			ret.add(getClass().getMethod("damage", new Class[] { int.class }));
			ret.add(getClass().getMethod("hasItem", new Class[] { String.class }));
			ret.add(getClass().getMethod("addItem", new Class[] { Item.class }));
			ret.add(getClass().getMethod("removeItem", new Class[] { String.class }));
			ret.add(getClass().getMethod("getInventory", new Class[] {}));
			ret.add(getClass().getMethod("useItem", new Class[] { String.class, Creature[].class }));
			ret.add(getClass().getMethod("useItem", new Class[] { String.class, Creature.class }));
			ret.add(getClass().getMethod("getAllItems", new Class[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}