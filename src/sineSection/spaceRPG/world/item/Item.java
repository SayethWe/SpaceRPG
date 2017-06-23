package sineSection.spaceRPG.world.item;

import java.util.ArrayList;
import java.util.List;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.world.item.effect.*;

/**
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	private final static int NO_DURABILITY = 0;
	
	private final String name; 
	private final String description;
	private final boolean permanent;
	private int usesLeft = 0;
	private List<Aura> auras;
	private boolean isUseable = true;
	
	/**
	 * create this as a permanent item
	 * @param name
	 */
	protected Item(String name, String description) {
		this.name = name;
		this.description = description;
		permanent = true;
		auras = new ArrayList<>();
	}
	
	/**
	 * create this as a non-permanent item with a number of uses
	 * @param name
	 * @param uses
	 */
	protected Item(String name, String description, int uses) {
		this.name = name;
		this.description = description;
		usesLeft = uses;
		permanent = false;
	}
	
	public void addAura(Aura aura) {
		auras.add(aura);
	}
	
	public List<Aura> getAuras() {
		return auras;
	}
	
	public boolean hasAuraEffect() {
		return (auras.size() > 0);
	}

	public String getName(){
		return name;
	}
	
	public int getUsesLeft() {
		return usesLeft;
	}
	
	/**
	 * @return <code>true</code> if the item is out of uses
	 */
	protected void damage() {
		if(!permanent) {
			if(usesLeft > NO_DURABILITY) {
				usesLeft--;
				isUseable = false;
			} else {
				isUseable = true; // no uses left
			}
		} else {
			isUseable = false; //it's permanent
		}
	}
	
	public boolean getUseable() {
		return isUseable;
	}
	
	/**
	 * use this item to get the active effect.
	 * @return if the item was used on a valid target successfully
	 */
	public abstract boolean use(Creature target);
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Item:");
		result.append("\n")
			  .append(name)
			  .append("\n")
			  .append(description)
			  .append("\n")
			  .append("Passives:")
			  .append("\n");
		auras.forEach((aura) -> result.append(aura.toString() + "\n"));
		return result.toString();
	}
}
