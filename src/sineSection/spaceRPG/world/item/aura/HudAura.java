package sineSection.spaceRPG.world.item.aura;

import sineSection.spaceRPG.character.Creature;
import sineSection.spaceRPG.character.Player;

public class HudAura extends AbilityAura {

	public HudAura() {
		super("Allows user to view their stats.");
	}

	public void affect(Creature creature) {
		if(creature instanceof Player) {
			((Player) creature).setHasHud(true);
		}
	}
	
	public void unaffect(Creature creature) {
		if(creature instanceof Player) {
			((Player) creature).setHasHud(false);
		}
	}

}
