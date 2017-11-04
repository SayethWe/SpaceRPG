package greenShift.world.generator;

import java.util.Random;

public class MapGenerator {

	private long seed;
	private final static int nodeSize = 5; //how large of a grid to generate at a time
	
	/**
	 * creates a doorway map to ensure continuous possible travel across rooms, then tells rooms as they're generated which ones to connect to.
	 * 
	 * Needs to be infinitely scalable in two dimensions, able to add segments in four directions.
	 * <br> is recommended to generate occasional loops.
	 * 
	 * @author geekman9097
	 *
	 */
	private class PathGenerator {
		private final Random pathRandom;

		
		private PathGenerator(long seed) {
			pathRandom = new Random(seed);
		}
	}
	
	/**
	 * Generates a biome map based on noise values for multiple aspects.
	 * @author geekman9097
	 *
	 */
	private class BiomeGenerator {

		/*
		 * Generate a room based on the following aspects:
		 * How nice the part of the ship it's in is. (price)
		 * How hot the part of the ship its in is. (heat)
		 * Possibly more, eventually.
		 */
		private OpenSimplexNoise heatNoise;
		private OpenSimplexNoise priceNoise;

		private final String[][][] RoomCalls = //an array of all the room types and their generation capabilities.
			{
					{{},{"Computer Room","Refrigerator"},{},{},{"Boiler","Reactor"}},//Machine Rooms
					{{},{},{"Quarters","Bunks"},{"Kitchen"},{}},//Crew Rooms
					{{},{},{},{},{}},//Economy class
					{{},{},{},{},{}},//Business class
					{{},{},{},{},{}},//First Class
			};
	}
}
