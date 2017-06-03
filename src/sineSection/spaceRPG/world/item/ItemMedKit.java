package sineSection.spaceRPG.world.item;

import sineSection.spaceRPG.character.Creature;

public class ItemMedKit extends Item {
	final static private String ITEM_NAME = "MedKit";
	final private static String DESCRIPTION = "A small first-aid kit to heal superficial wounds";
	final private static int USES = 1;
	final private static int HEAL_AMT = 10;

	public ItemMedKit() {
		super(ITEM_NAME, DESCRIPTION,USES);
	}

	@Override
	public boolean use(Creature target) {
		boolean result = getUseable();
		if(result) {
			target.heal(HEAL_AMT);
			damage();
		}
		return result;
	}

}
