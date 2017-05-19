package sineSection.spaceRPG;

import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.items.*;
import sineSection.spaceRPG.world.rooms.*;

public class SpaceRPG {
	private static Generator<Room> roomGenerator;
	private static Generator<Item> itemGenerator;
	
	public static void main(String[] args) {
		SpaceRPG.initialize();
		new SpaceRPG().testGame();
//		new GameUI();
	}
	
	private static void initialize() {
		roomGenerator = new Generator<>();
		itemGenerator = new Generator<>();
		addRoomTypes();
		addItemTypes();
	}
	
	private static void addRoomTypes() {
		roomGenerator.addType(RoomCorridor.class);
		roomGenerator.addType(RoomReactor.class);
	}
	
	private static void addItemTypes() {
		itemGenerator.addType(TestItem.class);
	}
	
	private void testGame() {
		Player testPlayer = new Player("Katyusha");
		testPlayer.addItem(itemGenerator.generate());
		testPlayer.useItem("Fred the Test Item");
		System.out.println(testPlayer);
	}

}
