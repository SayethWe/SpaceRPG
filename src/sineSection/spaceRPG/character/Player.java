package sineSection.spaceRPG.character;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import java.util.Map.Entry;
import java.util.Set;

import sineSection.spaceRPG.world.items.Item;

/**
 * @Author William Black
 * A class that stores the stats and inventory of a player character
 */
public class Player extends Character{
	private static final int INTELLECT_MAX_POSSIBLE = 10;
	private static final int POWER_MAX_POSSIBLE = 10;
	private static final int HEALTH_MAX = 20;
	private static final int INTELLECT_MIN = 1;
	private static final int POWER_MIN = 1;
	private static final int HEALTH_MIN = 0;
	
	public final static String INTELLECT = "Intelligence";
	public final static String POWER = "Strength";
	
	private String name; //Name of the character
	private Map<String, Item> inventory; //What the character is currently holding
	private Map<String, Stat> stats;
	private Stat health;
	//Intelligence of the character without item effects
	//Strength of character without item effects
	//Intelligence of character with item effects
	//Strength of character with item effects

	/**
	 * @Author William Black
	 * Initializes 'inventory' and 'stats', adds all the 'stats' into the appropriate HashMap
	 */
	public Player(String name){
		this.name = name;
		inventory = new HashMap<>();
		stats = new HashMap<>();
		stats.put("Intelligence", new Stat(INTELLECT_MIN, (int) (Math.random() * INTELLECT_MAX_POSSIBLE + 1)));
		stats.put("Strength", new Stat(POWER_MIN, (int) (Math.random() * POWER_MAX_POSSIBLE + 1)));
		health = new Stat(HEALTH_MIN, HEALTH_MAX);
	}

	/**
	 * @Author William Black
	 * @Return True if Item is in the players inventory, false if it is not found
	 */
	public boolean hasItem(Item item){
		return inventory.containsValue(item);
	}

	/**
	 * @Author William Black
	 * @Return True if Item was added successfully, False if the Item cannot be added to inventory
	 */
	public boolean addItem(Item item){ //Returns true if item was successfully added to inventory, returns false if the item is unable to be added
		if(inventory.size() < 20){
			inventory.put(item.getName(), item);
			
			if(item.hasAuraEffect() == true){item.addEffect(this);}
			
			return true;
		} else{
			return false;
		}

	}

	/**
	 * @Author William Black
	 * @Return The current health of the player
	 */
	public int getHealth(){
		return health.currentVal();
	}

	/**
	 * @Author William Black
	 * @Return The name of the Player
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @Author William Black
	 * @return A String array containing the inventory of the player
	 */
	public Set<String> getInventory(){
		return inventory.keySet();
	}

	/**
	 * Change a status of this character by some amount. SHOULD NOT BE USED FOR HEALTH
	 * @Author William Black
	 * @param status
	 * @param number
	 * @return if the update was successful or not
	 */
	public boolean addToStatus(String status, int number){
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
			health.increment(-health.currentVal());
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
			health.increment(health.maxVal()-health.currentVal());
		} else {
			health.increment(amt);
		}
		return fullHeal;
	}
	
	/**
	 * @Author William Black
	 * Makes character die
	 */
	public void makeCharacterDie(){
		//TODO make character die
	}
	
	/**
	 * @Author William Black
	 * @param itemName
	 * @return string stating if the Item was used successfully
	 * Checks to see if item is in inventory, if the item is not permanent then delete it from the inventory
	 */
	public boolean useItem(String itemName){
		if(inventory.containsKey(itemName)){
			inventory.get(itemName).addEffect(this);
				if(!inventory.get(itemName).isPermanent()){
					inventory.remove(itemName);
				}
			return true;
		} else{
			return false;
		}
	}

	/**
	 * @Return a string representation of the player
	 */
	public String toString(){
		StringBuilder string = new StringBuilder();
		string.append("Character:");
		string.append("\n");
		string.append(name);
		string.append("\n");

		stats.forEach((title, value) -> string.append("\n" + title + ": " + value));
		
		return string.toString();
	}

}