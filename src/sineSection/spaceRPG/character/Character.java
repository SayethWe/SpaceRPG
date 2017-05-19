package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.items.Item;

/**
 * Abstract class for character
 * @Author William Black
 */
public abstract class Character {


	protected final String name; //Name of the character
	protected Stat health;
	
	public Character(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Character:");
		string.append("\n");
		string.append(name);
		string.append("\n");
		string.append("Health: ");
		string.append(health);
		return string.toString();
	}
	
	public abstract boolean hasItem(Item item); //Returns true if item is in the inventory
	public abstract boolean addItem(Item item); //Returns true if item was successfully added to inventory, returns false if the item is unable to be added
	public abstract String getName();
}