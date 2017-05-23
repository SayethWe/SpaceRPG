package sineSection.spaceRPG.world.items;

import java.util.ArrayList;
import java.util.List;

import sineSection.spaceRPG.world.items.effects.*;

/*
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	private final String name; 
	private final boolean permanent;
	private List<Aura> auras;
	
	protected Item(String name, boolean permanent) {
		this.name = name;
		this.permanent = permanent;
		auras = new ArrayList<>();
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
	
	/**
	 * use this item to get the active effect
	 */
	public abstract List<Effect> use();
}
