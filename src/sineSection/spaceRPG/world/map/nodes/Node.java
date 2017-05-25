package sineSection.spaceRPG.world.map.nodes;

import java.util.List;
import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.Pos;
import sineSection.spaceRPG.world.rooms.Room;

public abstract class Node {
	private int size;
	private Map<Pos,Room> map;
	private Generator<Room> roomGenerator;
	
	public Node(int size) {
		this.size = size;
		roomGenerator = new Generator<>();
	}
	
	public void addRoomType(Class<? extends Room> type) {
		roomGenerator.addType(type);
	}
	
	private void generate() {
		for (int i = 0; i < size; i++) {
			map.put(new Pos(i,i), roomGenerator.generate());
		}
	}
	
	public Room getRoom(Pos pos) {
		return map.get(pos);
	}

}
