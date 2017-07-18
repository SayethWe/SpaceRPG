package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.WorldPos;

public abstract class Enemy extends EnnPeeSee {

	public Enemy(String name, int hpMax, WorldPos pos) {
		super(name, hpMax, false, pos);
	}
	
	public abstract Item getLootDrop();
	
	/**
	 * enemies have an "ability" they can use. this does that.
	 */
	public abstract void doAbility();

}
