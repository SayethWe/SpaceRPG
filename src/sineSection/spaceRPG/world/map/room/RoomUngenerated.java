package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.map.WorldPos;

public final class RoomUngenerated extends Room {
	private static final String DEFAULT_DESCRIPTION = "A blank room that somehow seems to be changing itself \nDEBUG_ERROR: THIS MESSAGE SHOULD NOT BE SEEN IN-GAME \nSee RoomUngenerated";

	public RoomUngenerated() {
		super(DEFAULT_DESCRIPTION);
	}

	/**
	 * {@inheritDoc} <br>
	 * Fills this placeholder room with an actual randomly generated one
	 */
	@Override
	public void onRoomEnter(Player p) {
		WorldPos pos = p.getPos();
		SpaceRPG.getMaster().getWorld().get(pos.getNode()).generateAt(pos.getRoom());
	}

}
