package sineSection.spaceRPG.world.items;

import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;

/*
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	protected final String name; 
	protected final boolean permanent;
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
	public  abstract void addEffect(Player player);
	
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
}
