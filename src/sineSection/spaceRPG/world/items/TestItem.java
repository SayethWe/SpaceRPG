package sineSection.spaceRPG.world.items;

import java.util.ArrayList;
import java.util.Iterator;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;

public class TestItem extends Item {
	final static private String ITEM_NAME = "Fred the Test Item";
	final static private boolean PERMANENT = true;


	public TestItem(){
		super(ITEM_NAME, PERMANENT);
		auras = new ArrayList<>();
		auras.add(new Aura(Player.POWER, 1));
		auras.add(new Aura(Player.INTELLECT, -2));
		//TODO add nightvision
	}
	
	public void use() {
		System.out.println("My Love Is Eternal");
	}

	public void addEffect(sineSection.spaceRPG.character.Character ch){
		Iterator<Aura> itr = auras.iterator();

		while(itr.hasNext()){
			Aura nextEntry = itr.next();
			String status = nextEntry.getStat();
			int add = nextEntry.getAmount();
			ch.addToStatus(status, add);
		}
	}
}
