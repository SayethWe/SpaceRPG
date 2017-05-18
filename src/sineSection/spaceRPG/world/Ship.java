package sineSection.spaceRPG.world;

import java.io.Serializable;
import java.util.Map;

import sineSection.spaceRPG.world.rooms.Room;

/**
 * The ship map, and character holder. Technically the overworld.
 * stores a list of all the rooms. Makes writing saves easier, 
 * and can be used to connect existing discovered rooms with newly generated areas
 * @author geekman9097
 *
 */
public class Ship implements Serializable {
	private static final long serialVersionUID = 198508905811730455L;
	private Map<Pos,Room> map;
	
	public Ship() {
		map = new HashMap<>();
	}
}
