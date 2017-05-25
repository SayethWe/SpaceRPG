package sineSection.spaceRPG.world.items;

import java.util.LinkedList;
import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;
import sineSection.spaceRPG.world.items.effects.Effect;

public class PArmorItem extends Item {
	final static private String ITEM_NAME = "Power Armor";


	public PArmorItem(){
		super(ITEM_NAME);
		addAura(new Aura(Player.POWER, 10));
		addAura(new Aura(Player.INTELLECT, 3));
		addAura(new Aura(Player.RESISTANCE, 5));
		//TODO add nightvision
	}
	
	public boolean use() {
		boolean result = super.use();
		System.out.println("!!WARNING!!: Suit does not contain Bubblegum");
		return result;
	}
}
