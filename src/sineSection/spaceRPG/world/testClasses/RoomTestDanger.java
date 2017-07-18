package sineSection.spaceRPG.world.testClasses;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.map.room.Room;

/**
 * a dangerous room. fills with smoke after you leave and damages you if you enter again
 * @author geekman9097
 *
 */
public class RoomTestDanger extends Room {
	
	private final static String DEFAULT_DESCRIPTION = "A danger noodle Room";
	private final static int DMG = 5;
	private boolean discovered = false;

	public RoomTestDanger() {
		super(DEFAULT_DESCRIPTION);
	}

	@Override
	public void onRoomEnter(Player p) {
		if(discovered) {
			SpaceRPG.getMaster().writeToGui("The cloud of smog chokes you!");
			p.damage(DMG);
		} else {
			discovered = true;
			SpaceRPG.getMaster().writeToGui("There's a fire in this room, filling it with smoke."
					+ "You put it out, but future expeditions may be hazardous.");
		}
		
	}

	@Override
	public void onRoomExit(Player p) {
		// TODO Auto-generated method stub
		
	}

}
