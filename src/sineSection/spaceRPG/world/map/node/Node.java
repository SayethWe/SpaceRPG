package sineSection.spaceRPG.world.map.node;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;
import sineSection.spaceRPG.world.map.room.RoomThreshold;

public abstract class Node {
	private static final int MAX_GEN_SIZE = 15; // maximum size this can
												// randomly generate itself to
												// be;
	private static final double SIZE_TUNER = 1.1; // make it so that smaller
													// nodes are favoured

	private int size;
	private Map<Pos, Room> map;
	private Generator<Room> roomGenerator;
	private Random doorRandomizer;
	
	public Node() {
		// this((int) (Math.pow(SIZE_TUNER, Math.random() *
		// (Utils.log(SIZE_TUNER, MAX_GEN_SIZE) + 1)) + 0.5));
		this(MAX_GEN_SIZE); // because There'd be now way right now to handle
							// diff sized nodes & movement between
		// TODO change the above comment to be false
	}

	public Node(int size) {
		this.size = size;
		roomGenerator = new Generator<>();
		generate();
		generateExits();
		addRoomTypes();
		doorRandomizer = new Random(SpaceRPG.getNewSeed());
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
	
	/**
	 * Generate all the doorways
	 * uses the pentadact method, described at <br>
	 * <a href="http://www.pentadact.com/2014-07-19-improving-heat-signatures-randomly-generated-ships-inside-and-out/">
	 * pentadact.com </a>
	 */
	public void generateExits() {
		final int doorChance = 6; //about one in every #VALUE, generate a door to the next row
		final int cutoffChance = 3; //if we have a way back, about one in every VALUE, don't go to the previous cell. 
		
		Set<Integer> prevDoors = new HashSet<>();
		for (int y = 0; y < size; y++) {
			Set<Integer> doorsHere = prevDoors;
			prevDoors.clear();
			doorsHere.add(Integer.valueOf(doorRandomizer.nextInt(size)));
			boolean doorRight = false;
			for (int x = 0; x < size; x++) {
				Room setting = map.get(new Pos(x,y));
				Set<Direction> exits = new HashSet<>();
				boolean doorLeft = true;
				if(doorRight) {
					doorRight = false;
					setting.addExit(Direction.STARBOARD);
				}
				if(doorsHere.contains(Integer.valueOf(x))) {
					//generate a door to the previously generated row
					doorLeft = doorRandomizer.nextInt(cutoffChance) == 0;
					setting.addExit(Direction.FORE);
				}
				if(doorRandomizer.nextInt(doorChance) == 0) {
					//generate a door to the next row
					prevDoors.add(x);
					setting.addExit(Direction.AFT);
				}
				if(doorLeft) {
					doorRight = true;
					setting.addExit(Direction.PORT);
				}
			}
		}
		
	}

	protected abstract void addRoomTypes();

}
