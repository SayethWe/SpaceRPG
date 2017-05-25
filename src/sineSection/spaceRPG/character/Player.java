package sineSection.spaceRPG.character;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sineSection.spaceRPG.world.items.Item;
import sineSection.spaceRPG.world.items.effects.Effect;

/**
 * @Author William Black
 * A class that stores the stats and inventory of a player character
 */
public class Player extends Character{
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
	 * @Return True if Item is in the players inventory, false if it is not found
	 */
	public boolean hasItem(Item item){
		return inventory.containsValue(item);
	}

	/**
	 * @Author William Black
	 * @Return True if Item was added successfully, False if the Item cannot be added to inventory
	 */
	public boolean addItem(Item item){ 
		boolean result = inventory.size() < INVENTORY_SIZE;
		if(result){
			inventory.put(item.getName(), item);
			
			if(item.hasAuraEffect() == true){
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
	 * @return A String array containing the inventory of the player
	 */
	public List<String> getInventory(){
		return new ArrayList<>(inventory.keySet());
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
		Item item = inventory.get(itemName);
		boolean result = item != null;
		if(result) {
			if(inventory.get(itemName).use()){
				removeItem(itemName);
			}
		}
		return result;
	}
	
	
	public void applyEffect(Effect use) {
		// TODO Auto-generated method stub
	}
}