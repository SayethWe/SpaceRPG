package sineSection.spaceRPG.world.map.node;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.map.Direction;
import sineSection.spaceRPG.world.map.Doorway;
import sineSection.spaceRPG.world.map.Pos;
import sineSection.spaceRPG.world.map.room.Room;

public abstract class Node {
	private static final int MAX_GEN_SIZE = 5; // maximum size this can
												// randomly generate itself to
												// be;
//	private static final double SIZE_TUNER = 1.1; // make it so that smaller
													// nodes are favoured

	private int size;
	private Map<Pos, Room> map;
	private Generator<Room> roomGenerator;
	private DoorPlacer doorGenerator;
//	private Random doorRandomizer;
	
	public Node() {
		// this((int) (Math.pow(SIZE_TUNER, Math.random() *
		// (Utils.log(SIZE_TUNER, MAX_GEN_SIZE) + 1)) + 0.5));
		this(MAX_GEN_SIZE); // because There would be now way right now to handle
							// diff sized nodes & movement between
		// TODO change the above comment to be false
	}

	public Node(int size) {
		this.size = size;
		map = new HashMap<>();
		roomGenerator = new Generator<>();
		doorGenerator = new DoorPlacer();
//		doorRandomizer = new Random(SpaceRPG.getNewSeed());
		addRoomTypes();
		addDoorTypes();
		generate();
		doorGenerator.generateExits();
	}
	
	public void addRoomType(Class<? extends Room> type, int weight) {
		roomGenerator.addType(type, weight);
	}

	public void addRoomType(Class<? extends Room> type) {
		roomGenerator.addType(type);
	}
	
	public void addDoorType(Class<? extends Doorway> type, int weight) {
		doorGenerator.addType(type, weight);
	}

	public void addDoorType(Class<? extends Doorway> type) {
		doorGenerator.addType(type);
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
			//This should not happen. The Ship should catch before this.\
			throw new IllegalArgumentException();
		} else {
			result = map.get(pos);
		}
		return result;
	}

	protected abstract void addRoomTypes();
	
	protected abstract void addDoorTypes();
	
	private class DoorPlacer {
		/**a <code>val</code>/<code>DOOR_BACK_TOTAL</code>  to create a door back after we've created the first door for a row*/
		private static final int DOOR_BACK_CHANCE = 1;
		private static final int DOOR_BACK_TOTAL = 3;
		
		private static final int SIDE_CUTOFF_CHANCE = 1;
		private static final int SIDE_CUTOFF_TOTAL = 4;
		
		private Generator<Doorway> generator;
		private Random doorRandom;

		public DoorPlacer() {
			generator = new Generator<>();
			doorRandom = new Random(SpaceRPG.getNewSeed());
		}

		public void generateExits() {
			Map<Integer, Doorway> doorsFore = new HashMap<>();
			for (int y = 0; y < size; y++) {
				Map<Integer, Doorway> doorsAft = new HashMap<>(doorsFore);
				System.out.println("Row " + y + " : " + doorsAft);
				doorsFore.clear();
				int forcedDoorFore = doorRandom.nextInt(size);
				Doorway doorPort = null;
				
				for (int x = 0; x < size; x++) {
					Room doorify = getRoom(new Pos(x,y));
					Doorway doorStarboard;
					boolean doorFore = (y == size-1)||x>forcedDoorFore ? false : doorRandom.nextInt(DOOR_BACK_TOTAL) < DOOR_BACK_CHANCE;
					boolean doDoorStarboard = doorFore ? doorRandom.nextInt(SIDE_CUTOFF_TOTAL) < SIDE_CUTOFF_CHANCE : true;
					if (x == size-1) doDoorStarboard = false; //ensure we don't go of the edge of the world

					if ((x == forcedDoorFore || doorFore)) {
						//generate door fore
						doorsFore.put(x, doorify.addDoor(Direction.FORE, generator.generate()));
					}
					if (doDoorStarboard) {
						doorStarboard = doorify.addDoor(Direction.STARBOARD, generator.generate());
					} else {
						doorStarboard = null;
					}
					if (doorPort != null) { //unfortunately, must currently be before
						doorify.addDoor(Direction.PORT, doorPort);
					}
					if(doorsAft.keySet().contains(x)) {
						doorify.addDoor(Direction.AFT, doorsAft.get(x));
					}
					doorPort = doorStarboard;
				}
			}
		}

		public void addType(Class<? extends Doorway> type, int weight) {
			generator.addType(type, weight);
		}
		
		public void addType(Class<? extends Doorway> type) {
			generator.addType(type);
		}

	}

	public int getWidth() {
		return size;
	}
	
	public int getHeight() {
		return size;
	}

}
