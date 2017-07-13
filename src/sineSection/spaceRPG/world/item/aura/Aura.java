package sineSection.spaceRPG.world.item.aura;

import sineSection.spaceRPG.character.Creature;

/**
 * a native effect in place as long as an item is held.
 * 
 * @author geekman9097, Richard Abbott
 *
 */
public abstract class Aura {

	public Aura() {}
	
	public abstract void affect(Creature creature);
	public abstract void unaffect(Creature creature);
	
	public abstract String toString();
}
