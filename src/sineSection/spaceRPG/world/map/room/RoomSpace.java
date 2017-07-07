package sineSection.spaceRPG.world.map.room;

import sineSection.spaceRPG.character.Player;

/**
 * It's outer space. It kills you. There should be warning lights before you
 * open a door to one of these. Also, maybe there can be non-functional lights
 * so you have to be careful when there's a cracked light.
 * 
 * @author geekman9097
 *
 */
public class RoomSpace extends Room {

	public RoomSpace(String description) {
		super(description);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onRoomEnter(Player p) {
		p.makeCharacterDie();
	}

	@Override
	public void onRoomExit(Player p) {
		// Shouldn't be possible
		
	}

}
