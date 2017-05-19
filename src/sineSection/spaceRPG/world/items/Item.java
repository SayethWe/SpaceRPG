package sineSection.spaceRPG.world.items;

import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;

/*
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	private final String name; 
	private final boolean permanent;
	protected List<Aura> auras;
	
	protected Item(String name, boolean permanent) {
		this.name = name;
		this.permanent = permanent;
	}
	
	public boolean hasAuraEffect() {
		return (auras.size() > 0);
	}

	public String getName(){
		return name;
	}
	
	@Deprecated
	/**
	 * use <code>getClass()</code> instead
	 * @return
	 */
	public Class<? extends Item> getType(){
		return this.getClass();
	}
	
	public boolean isPermanent(){
		return permanent;
	}
	
	public abstract void addEffect(sineSection.spaceRPG.character.Character character);
	/**
	 * use this item to get the active effect
	 */
	public abstract void use();
}
