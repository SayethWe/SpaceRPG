package sineSection.spaceRPG.world.map;

import java.util.HashMap;
import java.util.Map;

import sineSection.spaceRPG.world.Pos;
import sineSection.spaceRPG.world.map.nodes.Node;

/**
 * The ship map, and character holder. Technically the overworld.
 * stores a list of all the rooms. Makes writing saves easier, 
 * and can be used to connect existing discovered rooms with newly generated areas
 * @author geekman9097
 *
 */
public class Ship {
	private Map<Pos,Node> map;
	
	public Ship() {
		map = new HashMap<>();
	}
	
	public Map<Pos, Node> getMap() {
		return map;
	}
	
	/**
	 * Get the element at a specific position
	 * @param pos
	 * @return
	 */
	public Node get(Pos pos) {
		return map.get(pos);
	}
	
	public void generateMap(){
		
	}
}
