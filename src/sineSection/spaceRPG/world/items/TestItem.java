package sineSection.spaceRPG.world.items;

import java.util.LinkedList;
import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;
import sineSection.spaceRPG.world.items.effects.Effect;

public class TestItem extends Item {
	final static private String ITEM_NAME = "Fred the Test Item";


	public TestItem(){
		super(ITEM_NAME);
		addAura(new Aura(Player.POWER, 1));
		addAura(new Aura(Player.INTELLECT, 2));
		//TODO add nightvision
	}
	
	public boolean use() {
		boolean result = super.use();
		System.out.println("My Love Is Eternal");
		return result;
	}
}
