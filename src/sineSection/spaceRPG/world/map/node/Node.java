package sineSection.spaceRPG.world.map.node;

import java.util.HashMap;
import java.util.Map;

import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;
import sineSection.spaceRPG.world.map.room.RoomThreshold;

public abstract class Node {
	private static final int MAX_GEN_SIZE = 5; // maximum size this can
												// randomly generate itself to
												// be;
//	private static final double SIZE_TUNER = 1.1; // make it so that smaller
													// nodes are favoured

	private int size;
	private Map<Pos, Room> map;
	private Generator<Room> roomGenerator;
//	private Random doorRandomizer;
	
	public Node() {
		// this((int) (Math.pow(SIZE_TUNER, Math.random() *
		// (Utils.log(SIZE_TUNER, MAX_GEN_SIZE) + 1)) + 0.5));
		this(MAX_GEN_SIZE); // because There'd be now way right now to handle
							// diff sized nodes & movement between
		// TODO change the above comment to be false
	}

	public Node(int size) {
		this.size = size;
		map = new HashMap<>();
		roomGenerator = new Generator<>();
//		doorRandomizer = new Random(SpaceRPG.getNewSeed());
		addRoomTypes();
		generate();
		generateExits();
	}
	
	public void addRoomType(Class<? extends Room> type, int weight) {
		roomGenerator.addType(type, weight);
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
		if (x > size || x < 0 || y > size || y < 0) {
			result = new RoomThreshold();
		} else {
			result = map.get(pos);
		}
		return result;
	}
	
	public void generateExits() {
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				Room setting = map.get(new Pos(x,y));
				if(y != 0) {
					setting.addExit(Direction.AFT);
				}
				if (y != size-1) {
					setting.addExit(Direction.FORE);
				}
				if (x != 0) {
					setting.addExit(Direction.PORT);
				}
				if (x != size-1) {
					setting.addExit(Direction.STARBOARD);
				}
			}
		}
	}
	
//	/**
//	 * Generate all the doorways
//	 * uses the pentadact method, described at
//	 * <a href="http://www.pentadact.com/2014-07-19-improving-heat-signatures-randomly-generated-ships-inside-and-out/">
//	 * pentadact.com </a>
//	 */
//	public void generateExits() {
//		final int doorChance = 6; //about one in every #VALUE, generate a door to the next row
//		final int cutoffChance = 3; //if we have a way back, about one in every VALUE, don't go to the previous cell. 
//		
//		Set<Integer> prevDoors = new HashSet<>();
//		for (int y = 0; y < size; y++) {
//			Set<Integer> doorsHere = new HashSet<>(prevDoors);
//			prevDoors.clear();
//			int guaranteedDoor = doorRandomizer.nextInt(size);
//			boolean[][] rightDoors = new boolean[size][size]; //create a boolean array. All should be false
//			for (int x = 0; x < size; x++) {
//				Room setting = map.get(new Pos(x,y));
//				boolean doorLeft = false;
//				if(((doorRandomizer.nextInt(doorChance) == 0) || x == guaranteedDoor) && y != size-1) {
//					//generate a door to the next row
//					prevDoors.add(x);
//					doorLeft = !(doorRandomizer.nextInt(cutoffChance) != 0 && prevDoors.size() > 0);
//					setting.addExit(Direction.FORE);
//				}
//				if(doorsHere.contains(x)) {
//					//generate a door to the previously generated row
//					setting.addExit(Direction.AFT);
//				}
//				if(doorLeft && x != 0) {
//					setting.addExit(Direction.PORT);
//					rightDoors[x-1][y] = true;
//				}
//			}
//			for (int x = 0; x < size; x ++) { //go back through the row to add all the rightward doors
//				Room setting = map.get(new Pos(x,y));
//				if(rightDoors[x][y]) {
//					setting.addExit(Direction.STARBOARD);
//				}
//			}
//		}
//		
//	}

	protected abstract void addRoomTypes();

}
