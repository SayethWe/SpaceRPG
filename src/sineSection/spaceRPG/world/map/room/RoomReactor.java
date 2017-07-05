package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

public class RoomReactor extends Room implements PowerPlant {
	private static final String DEFAULT_DESCRIPTION = "A big room with a reactor in the center";

	public RoomReactor() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		// TODO nuke player
		
	}
}
