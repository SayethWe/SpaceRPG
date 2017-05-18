package sineSection.spaceRPG.player;

import sineSection.spaceRPG.world.items.Item;

/*
 * @Author William Black
 * Abstract class for character
 */
public abstract class Character {
	
	abstract boolean hasItem(Item item); //Returns true if item is in the inventory
	abstract boolean addItem(Item item); //Returns true if item was successfully added to inventory, returns false if the item is unable to be added
	abstract int getHealth();
}