package sineSection.spaceRPG.world.map.node;

import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;
import sineSection.spaceRPG.world.map.room.RoomThreshold;

public abstract class Node {
	private static final int MAX_GEN_SIZE = 15; //maximum size this can randomly generate itself to be;
	private static final double SIZE_TUNER = 1.1; //make it so that smaller nodes are favoured
	
	private int size;
	private Map<Pos, Room> map;
	private Generator<Room> roomGenerator;
	
	public Node() {
//		this((int) (Math.pow(SIZE_TUNER, Math.random() * (Utils.log(SIZE_TUNER, MAX_GEN_SIZE) + 1)) + 0.5));
		this(MAX_GEN_SIZE); //because There'd be now way right now to handle diff sized nodes & movement between
		//TODO change the above comment to be false
	}

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
			for (int j = 0; j < size; j++) {
				generateAt(new Pos(i, j));
			}
		}
	}
	
	public void generateAt(Pos room) {
		map.put(room, roomGenerator.generate());
	}

	public Room getRoom(Pos pos) {
		Room result;
		int x = pos.getX();
		int y = pos.getY();
		if (x > size || x < 0 || y > size || y > 0) {
			result = new RoomThreshold();
		} else {
			result = map.get(pos);
		}
		return result;
	}

	protected abstract void addRoomTypes();

}
