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

import greenShift.Reference;
import util.SystemGrabber;

public class MapGenerator {
	private static final String BIOME_FILE = SystemGrabber.getAppdataPath() + File.separator + Reference.TITLE + "Biomes.txt";
		//TODO: Dynamically find this file.

	private long seed;
	private Random seedGenerator;
	private final BiomeGenerator biomeGenerator;
	
	public MapGenerator(long seed) {
		this.seed = seed;
		seedGenerator = new Random(seed);
		biomeGenerator = new BiomeGenerator(BIOME_FILE);
	}
	
	public long getNewSeed() {
		return seedGenerator.nextLong();
	}

	private class PathGenerator {
		
	}

	/**
	 * Generates a biome map based on noise values for multiple aspects.
	 * @author geekman9097
	 *
	 */
	private class BiomeGenerator {
		
		/**
		 * what series of characters we want our generator to split at when we define data
		 */
		private static final String GEN_SPLIT = ", ";
		/**
		 * what character(s) to use when trying to determine if a line is a comment
		 */
		private static final String COMMENT = "##";
		
		private final String biomeFile; //which file to read to load data
		private final BufferedReader dataReader; //reads the data off the file.
		private final int depth; //how many bases of generation to use.
		
		private final List<OpenSimplexNoise> noiseGenerators; //the group of noise planes to draw our data from
		private final Map<String,List<String>> biomeGroups; //a list of the calls for biomes that can be generated, for some data values
		private final Random biomeGrabber; //a random generator for selecting which biome from the possible group to use.

		BiomeGenerator(String readFile) {
			biomeFile = readFile;
			
			noiseGenerators = new ArrayList<>();
			biomeGroups = new HashMap<>();
			biomeGrabber = new Random(getNewSeed());
			
			//Possible Exception-throwing generations. Outsourced try/catch pollution
			dataReader = generateDataReader();
			depth = generateDepth();
			
			//Load data into collections
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
	
}
