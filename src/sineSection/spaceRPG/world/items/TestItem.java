package sineSection.spaceRPG.world.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sineSection.spaceRPG.player.Player;

public class TestItem extends Item {
	final private String ITEM_TYPE = "Test Item";
	final private String ITEM_NAME = "Fred the Test Item";
	final private boolean IMMEDIATE = true; //If true then effect is applied to the character immediately after acquiring, else it can be applied anytime
	final private boolean PERMANENT = false;
	
	private List<String> EFFECTS;


	public TestItem(){
		EFFECTS = new ArrayList<String>();
		EFFECTS.add("Strength 1");
		EFFECTS.add("Health 3");
		//TODO add nightvision
	}
	public String getName(){
		return ITEM_NAME;
	}

	public String getType(){
		return ITEM_TYPE;
	}

	public boolean isImmediateEffect(){
		return IMMEDIATE;
	}

	public void addEffect(Player player){
		Iterator<String> itr = EFFECTS.iterator();


		while(itr.hasNext()){
			String nextEntry;
			String entry[];
			String status = new String();
			int mult;
			
			nextEntry = itr.next();
			entry = nextEntry.split(" ");
			status = entry[0];
			mult = Integer.parseInt(entry[1]);
			
			player.addToStatus(status, mult);
		}
	}
	
	public boolean isPermanent(){
		return PERMANENT;
	}
}
