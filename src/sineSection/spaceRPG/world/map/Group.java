package sineSection.spaceRPG.world.map;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import sineSection.spaceRPG.world.Pos;

/**
 * The ship map, and character holder. Technically the overworld.
 * stores a list of all the rooms. Makes writing saves easier, 
 * and can be used to connect existing discovered rooms with newly generated areas
 * @author geekman9097
 *
 */
public class Group<E> implements Serializable {
	private static final long serialVersionUID = 198508905811730455L;
	private Map<Pos,E> map;
	
	public Group() {
		map = new HashMap<>();
	}
	
	public Map<Pos, E> getMap() {
		return map;
	}
	
	public void generateMap(){
		
	}
}
