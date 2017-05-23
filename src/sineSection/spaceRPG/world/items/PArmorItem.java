package sineSection.spaceRPG.world.items;

import java.util.LinkedList;
import java.util.List;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.items.effects.Aura;
import sineSection.spaceRPG.world.items.effects.Effect;

public class PArmorItem extends Item {
	final static private String ITEM_NAME = "Power Armor";
	final static private boolean PERMANENT = true;


	public PArmorItem(){
		super(ITEM_NAME, PERMANENT);
		addAura(new Aura(Player.POWER, 10));
		addAura(new Aura(Player.INTELLECT, 3));
		addAura(new Aura(Player.RESISTANCE, 5));
		//TODO add nightvision
	}
	
	public List<Effect> use() {
		System.out.println("!!WARNING!!: Suit does not contain Bubblegum");
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
