package sineSection.spaceRPG.player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import sineSection.spaceRPG.world.items.Item;

/**
 * @Author William Black
 * A class that stores the stats and inventory of a player character
 */
public class Player extends Character{
	private String name; //Name of the character
	private HashMap<String, Item> inventory; //What the character is currently holding
	private HashMap<String, Integer> stats;
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
		inventory = new HashMap<String, Item>();
		stats = new HashMap<String, Integer>();
		stats.put("Base Intelligence", (int) (Math.random() * 10 + 1));
		stats.put("Base Strength", (int) (Math.random() * 10 + 1));
		stats.put("Max Health", 20);
		stats.put("Health", 20);
		stats.put("Intelligence", stats.get("Base Intelligence"));
		stats.put("Strength", stats.get("Base Strength"));
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
			
			if(item.isImmediateEffect() == true){item.addEffect(this);}
			
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
		return stats.get("Health");
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
	public String[] getInventory(){
		Iterator<Entry<String, Item>> itr = inventory.entrySet().iterator();
		String[] slots = new String[inventory.size()];
		int i = 0;
		while(itr.hasNext()){
			slots[i] = itr.next().getValue().getName();
			i++;
		}
		return slots;
	}

	/**
	 * @Author William Black
	 * @param status
	 * @param number
	 * @return string stating if the update was successful or not
	 */
	public String addToStatus(String status, int number){

		if(status.equals("Health") && (stats.get("Health") + number) > stats.get("Max Health")){
			return "Health Maxed out";
		}else if((stats.get(status) + number) <= 0){
			makeCharacterDie();
			stats.put(status, 0);
			return "Too little " + status + " you have died";
		}

		stats.put(status, stats.get(status) + number);
		return "Status updated";
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
	public String useItem(String itemName){
		if(inventory.containsKey(itemName)){
			inventory.get(itemName).addEffect(this);
				if(!inventory.get(itemName).isPermanent()){
					inventory.remove(itemName);
				}
			return "Item used";
		} else{
			return "Item is not in your inventory";
		}
	}

	/**
	 * @Return a string representation of the player
	 */
	public String toString(){
		StringBuilder string = new StringBuilder();
		Iterator<Entry<String, Integer>> Itr = stats.entrySet().iterator();
		string.append("Character:");
		string.append("\n");
		string.append(name);
		string.append("\n");

		while(Itr.hasNext()){
			string.append("\n");
			string.append(Itr.next());
		}
		return string.toString();
	}

}