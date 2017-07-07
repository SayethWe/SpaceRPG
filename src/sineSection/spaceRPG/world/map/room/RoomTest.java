package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

public class RoomTest extends Room {

	private static final String DEFAULT_DESCRIPTION = "It's boring. It's blank.";

	public RoomTest() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		p.heal(1);
	}

}
