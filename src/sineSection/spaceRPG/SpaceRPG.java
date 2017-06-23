package sineSection.spaceRPG;

import java.util.Random;

import sineSection.spaceRPG.UI.GameUI;
import sineSection.spaceRPG.character.Player;
import sineSection.spaceRPG.world.Generator;
import sineSection.spaceRPG.world.item.Item;
import sineSection.spaceRPG.world.item.PArmorItem;
import sineSection.spaceRPG.world.item.TestItem;
import sineSection.util.LogWriter;

public class SpaceRPG {
	public static final String TITLE = "SpaceRPG";
	
	private static GameUI gui;
	private static Generator<Item> itemGenerator;
	private static Random seedGenerator;
	
	public static void main(String[] args) {
		if (args.length > 0) {
			initRandom(Integer.parseInt(args[0]));
		} else {
			initRandom();
		}
		SpaceRPG.initialize();
		new SpaceRPG().testGame();
//		new GameUI().display();
	}
	
	private static void initialize() {
		itemGenerator = new Generator<>();
		addItemTypes();
		LogWriter.createLogger(TITLE);
	}
	
	private static void addItemTypes() {
		itemGenerator.addType(TestItem.class);
		itemGenerator.addType(PArmorItem.class);
	}
	
	private void testGame() {
		gui = new GameUI();
		gui.display();
		Player testPlayer = new Player("Katyusha");
		Item testItem = itemGenerator.generate();
		writeToGui(testPlayer);
		testPlayer.addItem(testItem);
		writeToGui(testItem);
		testPlayer.useItem(testPlayer.getInventory().get(0),testPlayer);
		writeToGui(testPlayer);
	}
	
	private static void initRandom() {
		seedGenerator = new Random();
	}
	
	private static void initRandom(int gameSeed) {
		seedGenerator = new Random(gameSeed);
	}
	
	public static int getNewSeed() {
		return seedGenerator.nextInt();
	}
	
	public GameUI getGui() {
		return gui;
	}
	
	public void writeToGui(Object in) {
		gui.write(in);
	}

}
