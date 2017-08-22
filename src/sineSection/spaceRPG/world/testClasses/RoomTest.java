package sineSection.spaceRPG.world.testClasses;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.map.room.Room;

public class RoomTest extends Room {

	private static final String DEFAULT_DESCRIPTION = "It's boring. It's blank.";

	public RoomTest() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		p.heal(1);
	}

	@Override
	public void onRoomExit(Player p) {
		p.heal(3);
	}

}
