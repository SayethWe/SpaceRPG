package sineSection.spaceRPG.world;

import java.util.ArrayList;
import java.util.List;

import sineSection.spaceRPG.world.rooms.*;

public class Generator<T> {
	private List<Class<? extends T>> validTypes = new ArrayList<>();
	
	public void addType(Class<? extends T> type) {
		validTypes.add(type);
	}
	
	private  Class<? extends T> generateType() {
		Class<? extends T> result;
		int index = (int) (Math.random() * validTypes.size());
		result = validTypes.get(index);
		return result;
	}
	
	private T generate() {
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
