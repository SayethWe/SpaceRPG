package sineSection.spaceRPG.world.item;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.item.effect.Aura;

public class PArmorItem extends Item {
	final static private String ITEM_NAME = "Power Armor";
	final private static String DESCRIPTION = "Powerful armor that increases your defense";


	public PArmorItem(){
		super(ITEM_NAME, DESCRIPTION);
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
