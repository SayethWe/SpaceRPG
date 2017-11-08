package greenShift.world.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import greenShift.Reference;
import greenShift.world.room.Room;
import util.SystemGrabber;

public class MapGenerator {

	private long seed;
	private Random seedGenerator;
	
	public MapGenerator(long seed) {
		this.seed = seed;
		seedGenerator = new Random(seed);
	}

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

		private final String biomeFile = SystemGrabber.getAppdataPath() + File.separator + Reference.TITLE + "Biomes.txt";
		//TODO: Dynamically find this file.
		
		/**
		 * what series of characters we want our generator to split at when we define data
		 */
		private static final String GEN_SPLIT = ", ";
		/**
		 * what character(s) to use when trying to determine if a line is a comment
		 */
		private static final String COMMENT = "##";
		

		private final int depth; //how many bases of generation to use.
		private final List<OpenSimplexNoise> noiseGenerators;
		private final Map<String,List<String>> biomeGroups;
		private final BufferedReader dataReader;
		private final Random biomeGrabber;

		BiomeGenerator() {
			noiseGenerators = new ArrayList<>();
			biomeGroups = new HashMap<>();
			dataReader = generateDataReader();
			depth = generateDepth();
			biomeGrabber = new Random(getNewSeed());
			for(int i = 0; i<depth; i++) { noiseGenerators.add(new OpenSimplexNoise(getNewSeed())); }
			loadBiomes();
		}
		
		private BufferedReader generateDataReader() {
			BufferedReader result;
			try {
				result = new BufferedReader(new FileReader(biomeFile));
			} catch (FileNotFoundException e) {
				result = null;
			}
			return result;
		}
		
		private int generateDepth() {
			int result;
			try {
				result = dataReader.readLine().split(GEN_SPLIT).length;
			} catch (IOException e) {
				result = 0;
			}
			return result;
		}
		
		private void loadBiomes() {
			String line = COMMENT;
				while(line != null) {
					if(!line.startsWith(COMMENT)){
						String[] data = line.split(GEN_SPLIT);
						String name = data[0];
						int[] values = new int[depth];
						for(int i = 0; i < depth; i++) {
							values[i] = Integer.parseInt(data[i+1]);
						}
						addBiome(values,name);
					}
					try {
						line = dataReader.readLine();
					} catch (IOException e) {
						//TODO: generate default values
					}
				}
		}
		
		private void addBiome(int[] values, String biomeName) {
			String key = valuesToKey(values);
			biomeGroups.putIfAbsent(key,new ArrayList<>());
			biomeGroups.get(key).add(biomeName);
		}
		
		private String valuesToKey(int[] values) {
			StringBuilder result = new StringBuilder("");
			for(int i : values) {
				result.append(i);
			}
			return result.toString();
		}

	}

	public long getNewSeed() {
		return seedGenerator.nextLong();
	}
}
