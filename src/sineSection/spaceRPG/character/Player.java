package sineSection.spaceRPG.character;

import java.util.HashMap;
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
	
	public final static String INTELLECT = "Intelligence";
	public final static String POWER = "Strength";

	private Map<String, Item> inventory; //What the player is currently holding
	//Intelligence of the character without item effects
	//Strength of character without item effects
	//Intelligence of character with item effects
	//Strength of character with item effects

	/**
	 * @Author William Black
	 * Initializes 'inventory' and 'stats', adds all the 'stats' into the appropriate HashMap
	 */
	public Player(String name){
		super(name, HEALTH_MAX);
		inventory = new HashMap<>();
		stats = new HashMap<>();
		stats.put("Intelligence", new Stat(INTELLECT_MIN, (int) (Math.random() * INTELLECT_MAX_POSSIBLE + 1)));
		stats.put("Strength", new Stat(POWER_MIN, (int) (Math.random() * POWER_MAX_POSSIBLE + 1)));
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
	 * @return A String array containing the inventory of the player
	 */
	public Set<String> getInventory(){
		return inventory.keySet();
	}
	
	/**
	 * @Author William Black
	 * Makes character die
	 */
	public void makeCharacterDie(){
		//TODO make character die
	}
	
	/**
	 * Checks to see if item is in inventory, if the item is not permanent then delete it from the inventory
	 * @Author William Black
	 * @param itemName
	 * @return <true> if the Item was used successfully
	 */
	public boolean useItem(String itemName){
		if(inventory.containsKey(itemName)){
			inventory.get(itemName).addEffect(this);
				if(!inventory.get(itemName).isPermanent()){
					//TODO: implement a number-of-uses system
					inventory.remove(itemName);
				}
			return true;
		} else{
			return false;
		}
	}
	
	public String toString(){
		StringBuilder string = new StringBuilder(super.toString());

		stats.forEach((title, value) -> string.append("\n" + title + ": " + value));
		
		return string.toString();
	}

}