package sineSection.spaceRPG.world.item;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.item.effect.Aura;

public class TestItem extends Item {
	final static private String ITEM_NAME = "Fred";
	final static private String DESCRIPTION = "His love, it is eternal.";

	public TestItem() {
		super(ITEM_NAME, DESCRIPTION);
		addAura(new Aura(Player.POWER, 1));
		addAura(new Aura(Player.INTELLECT, 2));
	}

	public boolean use(Creature target) {
		boolean result = getUseable();
		System.out.println("My Love Is Eternal");
		return result;
	}
}
