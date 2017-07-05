package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

public class RoomStore extends Room {
	private static final String DEFAULT_DESCRIPTION = "A do-it all shop with surprisingly wide selection, but extremely limited stock.";

	public RoomStore() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		// TODO Have Clerk Greet You
		
	}

}
