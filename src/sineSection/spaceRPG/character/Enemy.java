package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.item.Item;

public abstract class Enemy extends Creature {

	public Enemy(String name, int hpMax) {
		super(name, hpMax, 0);
	}
	
	public abstract Item getLootDrop();
	
	/**
	 * enemies have an "ability" they can use. this does that.
	 */
	public abstract void doAbility();

}
