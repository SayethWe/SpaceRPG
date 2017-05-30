package sineSection.spaceRPG.world.item;

import java.util.ArrayList;
import java.util.List;

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
	 * use this item to get the active effect. Decrements the durability if this is a non-permanent item
	 * @return <code>true</code> if the item is out of uses
	 */
	public boolean use() {
		boolean result;
		
		if(!permanent) {
			if(usesLeft > NO_DURABILITY) {
				usesLeft--;
				result = false;
			} else {
				result = true;
			}
		} else {
			result = false;
		}
		
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("Item:");
		result.append("\n");
		result.append(name);
		result.append("\n");
		result.append(description);
		result.append("\n");
		result.append("Passives:");
		result.append("\n");
		auras.forEach((aura) -> result.append(aura.toString() + "\n"));
		return result.toString();
	}
}
