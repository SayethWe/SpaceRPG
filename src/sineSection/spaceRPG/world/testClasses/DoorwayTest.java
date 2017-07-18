package sineSection.spaceRPG.world.testClasses;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.map.Doorway;

public class DoorwayTest extends Doorway {
	
	public DoorwayTest() {
		super(Player.POWER, 0);
	}

	@Override
	protected void punishFail(Player punishee) {}

}
