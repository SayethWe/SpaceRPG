package sineSection.spaceRPG.world.map.node;

import java.util.Map;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;

public abstract class Node {
	private int size;
	private Map<Pos, Room> map;
	private Generator<Room> roomGenerator;

	public Node(int size) {
		this.size = size;
		roomGenerator = new Generator<>();
		generate();
		addRoomTypes();
	}

	public void addRoomType(Class<? extends Room> type) {
		roomGenerator.addType(type);
	}

	private void generate() {
		for (int i = 0; i < size; i++) {
			map.put(new Pos(i, i), roomGenerator.generate());
		}
	}

	public Room getRoom(Pos pos) {
		return map.get(pos);
	}

	protected abstract void addRoomTypes();

}
