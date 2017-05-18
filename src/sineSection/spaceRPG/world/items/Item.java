package sineSection.spaceRPG.world.items;

import sineSection.spaceRPG.player.Player;

/*
 * @Author William Black
 * Abstract class for Item
 */
public abstract class Item {
	
	public abstract String getName();
	public abstract boolean isImmediateEffect();
	public  abstract void addEffect(Player player);
	public abstract boolean isPermanent();
}
