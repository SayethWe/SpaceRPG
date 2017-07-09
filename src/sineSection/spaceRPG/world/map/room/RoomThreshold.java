package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

public final class RoomThreshold extends Room {
	private static final String DEFAULT_DESCRIPTION = "The Threshhold to another section of the ship." + "\nDEBUG_ERROR: THIS MESSAGE SHOULD NOT BE SEEN IN-GAME " + "\nSee RoomThreshold";

	public RoomThreshold() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		// TODO Mave to next Node
	}

	@Override
	public void onRoomExit(Player p) {

	}

}
