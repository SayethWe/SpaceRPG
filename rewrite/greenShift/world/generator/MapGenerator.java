package greenShift.world.generator;

import java.util.List;
import java.util.Random;
import java.util.Set;

import greenShift.world.room.Room;

public class MapGenerator {

	private long seed;
	
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

		List<Set<Room>> pathCreation; //Ellis Algorithm method
		
		Set<Room> added, frontier, unknown; //Prim's algorithm Method
		
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
		 * How nice the part of the ship it's in is. (cost)
		 * How hot the part of the ship it's in is. (heat)
		 * Possibly more, eventually.
		 */
		private OpenSimplexNoise heatNoise;
		private OpenSimplexNoise costNoise;
		
		/*
		 * String values for fetching Data from value map
		 */
		private final static String HEAT = "heatValue";
		private final static String COST = "costValue";
		
		//TODO: data structure that stores rooms and their generation values
		
		BiomeGenerator(long heatSeed, long costSeed) {
			heatNoise = new OpenSimplexNoise(heatSeed);
			costNoise = new OpenSimplexNoise(costSeed);
		}
		
	}
}
