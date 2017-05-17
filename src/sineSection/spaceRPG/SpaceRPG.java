package sineSection.spaceRPG;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.rooms.*;

public class SpaceRPG {
	
	private static Generator<Room> roomGenerator;

	public static void main(String[] args) {
		SpaceRPG.initialize();
		new GameUI();
	}
	
	private static void initialize() {
		roomGenerator = new Generator<>();
		addRoomTypes();
	}
	
	private static void addRoomTypes() {
		roomGenerator.addType(RoomCorridor.class);
		roomGenerator.addType(RoomReactor.class);
	}

}
