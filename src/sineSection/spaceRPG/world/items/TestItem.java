package sineSection.spaceRPG.world.items;

import java.util.LinkedList;
import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;
import sineSection.spaceRPG.world.items.effects.Effect;

public class TestItem extends Item {
	final static private String ITEM_NAME = "Fred the Test Item";
	final static private boolean PERMANENT = true;


	public TestItem(){
		super(ITEM_NAME, PERMANENT);
		addAura(new Aura(Player.POWER, 1));
		addAura(new Aura(Player.INTELLECT, 2));
		//TODO add nightvision
	}
	
	public List<Effect> use() {
		System.out.println("My Love Is Eternal");
		return new LinkedList<>(getAuras());
	}

//	public void addEffect(sineSection.spaceRPG.character.Character ch){
//		Iterator<Aura> itr = auras.iterator();
//
//		while(itr.hasNext()){
//			Aura nextEntry = itr.next();
//			String status = nextEntry.getStat();
//			int add = nextEntry.getAmount();
//			ch.addToStatus(status, add);
//		}
//	}
}
