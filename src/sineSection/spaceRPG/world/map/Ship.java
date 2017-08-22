package sineSection.spaceRPG.world.map;

import java.util.HashMap;
import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.node.Node;
import sineSection.spaceRPG.world.map.room.Room;
import sineSection.spaceRPG.world.testClasses.NodeTest;

/**
 * The ship map, and character holder. Technically the overworld. stores a list
 * of all the rooms. Makes writing saves easier, and can be used to connect
 * existing discovered rooms with newly generated areas
 * 
 * @author geekman9097
 *
 */
public class Ship {
	private Generator<Node> nodeGenerator;
	private Map<Pos, Node> map;
	private int size;
	private static final Map<String, Direction> dirs = initDirs();;

	public Ship() {
		map = new HashMap<>();
		nodeGenerator = new Generator<>();
		addNodeTypes();
		generateMap();
	}

	private static Map<String, Direction> initDirs() {
		HashMap<String, Direction> result = new HashMap<>();
		for (Direction dir : Direction.values()) {
			result.put(dir.getCall().toLowerCase(), dir);
		}
		return result;
	}

	public static Direction getDir(String call) {
		return dirs.get(call.toLowerCase());
	}

	public Map<Pos, Node> getMap() {
		return map;
	}

	/**
	 * Get the element at a specific position
	 * 
	 * @param pos
	 * @return
	 */
	public Node getNode(Pos pos) {
		return map.get(pos);
	}

	public int getSize() {
		return size;
	}

	public void generateMap() {
		map.put(new Pos(0,0), nodeGenerator.generate());
	}

	private void addNodeTypes() {
		addNodeType(NodeTest.class);
	}

	public void addNodeType(Class<? extends Node> type) {
		nodeGenerator.addType(type);
	}

	public Room getRoomAt(WorldPos location) {
		return getNode(location.getNode()).getRoom(location.getRoom());
	}
}
