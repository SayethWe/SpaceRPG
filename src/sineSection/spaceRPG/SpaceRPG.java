package sineSection.spaceRPG;

import java.util.LinkedList;
import java.util.List;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.rooms.*;

public class SpaceRPG {
	
	private static List<Object> gameObjects = new LinkedList<>();
	private static Generator<Room> roomGenerator;

	public static void main(String[] args) {
		new GameUI();
	}
	
	private static void init() {
		roomGenerator = new Generator<>();
		roomGenerator.addType(Room.class);
		roomGenerator.addType(TransporterRoom.class);
	}

	public static void register(Object o) {
		gameObjects.add(o);
	}
	
	public static void remove (Object o) {
		gameObjects.remove(o);
	}

}
