package sineSection.spaceRPG.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator<T> {
	private List<Class<? extends T>> validTypes = new ArrayList<>();
	private Random RNJesus;
	
	public Generator(long seed) {
		RNJesus = new Random(seed);
	}
	
	public Generator() {
		RNJesus = new Random();
	}
	
	public void addType(Class<? extends T> type) {
		validTypes.add(type);
	}
	
	private  Class<? extends T> generateType() {
		Class<? extends T> result;
		int index = RNJesus.nextInt(validTypes.size());
		result = validTypes.get(index);
		return result;
	}
	
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
