package sineSection.spaceRPG.world.items;

import java.util.ArrayList;
import java.util.List;

import sineSection.spaceRPG.world.items.effects.*;

/**
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	private final static int NO_DURABILITY = 0;
	
	private final String name; 
	private final boolean permanent;
	private int usesLeft = 0;
	private List<Aura> auras;
	
	/**
	 * create this as a permanent item
	 * @param name
	 */
	protected Item(String name) {
		this.name = name;
		permanent = true;
		auras = new ArrayList<>();
	}
	
	/**
	 * create this as a non-permanent item with a number of uses
	 * @param name
	 * @param uses
	 */
	protected Item(String name, int uses) {
		this.name = name;
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
			if(--usesLeft > NO_DURABILITY) {
				result = false;
			} else {
				result = true;
			}
		} else {
			result = false;
		}
		
		return result;
	}
}
