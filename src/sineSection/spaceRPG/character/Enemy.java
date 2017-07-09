package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.item.Item;

public abstract class Enemy extends Creature {

	public Enemy(String name, int hpMax) {
		super(name, hpMax);
	}
	
	public abstract Item getLootDrop();

}
