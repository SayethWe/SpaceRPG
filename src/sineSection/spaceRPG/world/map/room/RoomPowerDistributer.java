package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

public class RoomPowerDistributer extends Room implements PowerPlant {
	static final String DEFAULT_DESCRIPTION = "A room with a tangle of wires running throughout";

	public RoomPowerDistributer() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRoomExit(Player p) {
		// TODO Auto-generated method stub
		
	}

}
