package sineSection.spaceRPG.world.map;

import java.util.HashMap;
import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.node.Node;
import sineSection.spaceRPG.world.map.node.NodeResidential;

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

	public Ship() {
		map = new HashMap<>();
		nodeGenerator = new Generator<>();
		addNodeTypes();
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
	public Node get(Pos pos) {
		return map.get(pos);
	}

	public int getSize() {
		return size;
	}

	public void generateMap() {

	}

	private void addNodeTypes() {
		addNodeType(NodeResidential.class);
	}

	public void addNodeType(Class<? extends Node> type) {
		nodeGenerator.addType(type);
	}
}
