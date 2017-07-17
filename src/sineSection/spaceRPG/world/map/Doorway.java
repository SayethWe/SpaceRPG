package sineSection.spaceRPG.world.map;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.item.Item;

public abstract class Doorway {

	private boolean blocked;
	private final String necessarySkill;
	private final int necessarySkillLevel;
	private final Item necessaryItem;
	
	public Doorway(String necessarySkill, int necessarySkillLevel, Item necessaryItem, boolean blocked) {
		this.necessarySkill = necessarySkill;
		this.necessarySkillLevel = necessarySkillLevel;
		this.necessaryItem = necessaryItem;
		this.blocked = blocked;
	}
	
	public Doorway(String necessarySkill, int necessarySkillLevel, Item necesaryItem) {
		this(necessarySkill, necessarySkillLevel, necesaryItem, true);
	}
	
	public Doorway(Item necessaryItem) {
		this(null, 0 , necessaryItem);
	}
	
	public Doorway(String necessarySkill, int necessarySkillLevel) {
		this(necessarySkill, necessarySkillLevel, null);
	}
	
	public Doorway() {
		this(null, 0, null, false);
	}
	
	public void attemptPass(Item useItem, Player passPlayer) {
		int currentSkillLevel = passPlayer.getStatVal(necessarySkill);
		if(useItem.equals(necessaryItem) && currentSkillLevel == necessarySkillLevel) {
			blocked = false;
		} else punishFail(passPlayer);
	}
	
	public boolean getBlocked() {
		return blocked;
	}
	
	public abstract void punishFail(Player punishee);

}
