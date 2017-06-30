package sineSection.spaceRPG.character;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.effect.Aura;

/**
 * Abstract class for character
 * @Author William Black
 */
public abstract class Creature {
	private static final int HEALTH_MIN = 0;

	private final String name; //Name of the character
//	private final ComfortStat warmth;
	private Stat health;
	private Map<String, Stat> stats;
	
	
	public Creature(String name, int hpMax) {
		this.name = name;
		stats = new HashMap<>();
		health = new Stat(HEALTH_MIN, hpMax);
		health.topOff();
	}
	
	public void addStat(String name, Stat stat) {
		stats.put(name, stat);
	}
	
	public int getStatVal(String stat)  {
		return stats.get(stat).currentVal();
	}

	/**
	 * Change a status of this character by some amount. SHOULD NOT BE USED FOR HEALTH
	 * @Author William Black
	 * @param status
	 * @param number
	 * @return if the update was successful or not
	 */
	public boolean addToStat(String status, int number) {
		return stats.get(status).increment(number);
	}
	
	
	/**
	 * Deal damage to the player by using specifically the HEALTH stat
	 * @author geekman9097
	 * @return <code>true</code> if the character is still alive after the damage is applied
	 * <br> <code>false</code> if otherwise
	 */
	public boolean damage(int amt) {
		amt = Math.abs(amt); //ensure that we will only deal damage
		boolean alive = health.incrementAllowed(-amt);
		if(alive) {
			health.increment(amt);
		} else {
			health.empty();
		}
		return alive;
	}
	
	/**
	 * Restore health points to the player
	 * @author geekman9097
	 * @param amt
	 * @return true if the character is now fully healthy
	 */
	public boolean heal(int amt) {
		amt = Math.abs(amt); //ensure that we will only heal
		boolean fullHeal = ! health.incrementAllowed(amt);
		if (fullHeal) {
			health.topOff();
		} else {
			health.increment(amt);
		}
		return fullHeal;
	}
	
	public int getHealth() {
		return health.currentVal();
	}
	
	public void addToBase(String stat, int incrementNum){
		stats.get(stat).addToMax(incrementNum);
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("Creature:");
		string.append("\n")
			  .append(name)
			  .append("\n")
			  .append("Health: ")
			  .append(health)
			  .append("Stats: ")
			  .append("/n");
		stats.forEach((title, value) -> string.append(title + ": " + value + "\n"));
		return string.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public Set<? extends Object> getAllStats() {
		Set<Stat> result = new HashSet<>();
		stats.forEach((name,stat) -> result.add(stat));
		return result;
	}
	
	public void addAuras(List<Aura> auras) {
		auras.forEach((aura) -> addToStat( aura.getStat(), aura.getAmount()));
	}
	public abstract boolean hasItem(Item item); //Returns true if item is in the inventory
	public abstract boolean addItem(Item item); //Returns true if item was successfully added to inventory, returns false if the item is unable to be added
}