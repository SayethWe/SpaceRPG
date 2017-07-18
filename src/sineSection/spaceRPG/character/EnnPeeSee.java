package sineSection.spaceRPG.character;

import sineSection.spaceRPG.world.map.WorldPos;

/**
 * Non Player Character
 * 
 * @author geekman9097
 *
 */
public abstract class EnnPeeSee extends Creature{

	public EnnPeeSee(String name, int hpMax, WorldPos pos) {
		super(name, hpMax, pos);
	}

	public abstract void interact();

}
