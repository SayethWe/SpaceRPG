package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.map.WorldPos;

public abstract class Enemy extends EnnPeeSee {

	public Enemy(String name, int hpMax, WorldPos pos) {
		super(name, hpMax, false, pos);
	}
	
	/**
	 * most enemies will drop a little something for you. This is how you know what it is.
	 * @return
	 */
	public abstract Item getLootDrop();
	
	/**
	 * enemies have an "ability" they can use. this does that.
	 */
	public abstract void doAbility();

}
