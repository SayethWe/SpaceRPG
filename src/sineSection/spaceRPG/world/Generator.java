package sineSection.spaceRPG.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sineSection.spaceRPG.SpaceRPG;
import sineSection.util.LogWriter;

/**
 * A generator object to create new randomly generated instances of certain
 * classes. Typed to the type of object you want to create, then handed the
 * possible types. <br>
 * Possible types are necessarily subclasses of the declared type.
 * 
 * <p>
 * Example: Type to Room, then hand RoomCorridor and RoomReactor as valid types.
 * Calling the {@code generate()} method will then deliver a new instance of
 * either a RoomCorridor or a RoomReactor
 * 
 * @author geekman9097
 *
 * @param <T>
 *            the type of object you want to generate.
 */
public class Generator<T> {
	protected List<Class<? extends T>> validTypes = new ArrayList<>();
	protected Random RNJesus;

	/**
	 * Creates a new Generator with a system-determined seed
	 * 
	 * @see java.util.Random
	 */
	public Generator() {
		RNJesus = new Random(SpaceRPG.getNewSeed());
	}

	/**
	 * adds a valid class to produce to the possible results of
	 * {@link #generate()}
	 * 
	 * <p>
	 * should only be called at initialization time. failure to do so may cause
	 * map generation issues.
	 * 
	 * @param type
	 *            the possible Class to add. Must be a subclass of the generic
	 *            this generator was typed with
	 */
	public void addType(Class<? extends T> type) {
		validTypes.add(type);
	}

	private Class<? extends T> generateType() {
		Class<? extends T> result;
		int index = RNJesus.nextInt(validTypes.size());
		result = validTypes.get(index);
		LogWriter.getLogger().info("Crafting a " + result);
		return result;
	}

	/**
	 * randomly generate a new object of the type this class was created with
	 * though a multi-stage process <br>
	 * randomly selects a Class from the valid type list, then creates a new
	 * instance of that class, and returns it
	 * 
	 * @return a new instance of a valid type this class can generate, as
	 *         defined by its typing and validType list
	 */
	public T generate() {
		T result;
		Class<? extends T> type = generateType();
		result = instantiate(type);
		return result;
	}

	private T instantiate(Class<? extends T> type) {
		T result = null;
		try {
			result = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
}
